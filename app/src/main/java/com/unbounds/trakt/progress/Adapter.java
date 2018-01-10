package com.unbounds.trakt.progress;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unbounds.trakt.ApiWrapper;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Episode;
import com.unbounds.trakt.api.model.Show;
import com.unbounds.trakt.api.model.request.WatchedItems;
import com.unbounds.trakt.api.model.response.AddHistory;
import com.unbounds.trakt.api.model.response.WatchedProgress;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by maclir on 2015-11-17.
 */
class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final List<WatchedProgressWrapper> mData = new ArrayList<>();
    private final Context mContext;

    Adapter(final Context context) {
        mContext = context;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_item, parent, false);
        return new ViewHolder(view, new ViewHolder.OnClicked() {
            @Override
            public void onCheckClicked(final int position) {
                final WatchedProgressWrapper watchedProgressWrapper = mData.get(position);
                watchedProgressWrapper.setSelected(true);
                final Episode episode = watchedProgressWrapper.getWatchedProgress().getNextEpisode();
                ApiWrapper.postWatchedItems(new WatchedItems.Builder().addEpisode(episode).create()).subscribe(new Action1<AddHistory>() {
                    @Override
                    public void call(final AddHistory addHistory) {
                        //TODO check if added episode exists in not_added
                        ApiWrapper.getWatchedProgress(watchedProgressWrapper.getShow().getIds().getTrakt()).subscribe(new Action1<WatchedProgress>() {
                            @Override
                            public void call(final WatchedProgress watchedProgress) {
                                watchedProgressWrapper.setSelected(false);
                                if (watchedProgress.isCompleted()) {
                                    mData.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    mData.set(position, watchedProgressWrapper.setWatchedProgress(watchedProgress));
                                    notifyItemChanged(position);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final WatchedProgressWrapper watchedProgressWrapper = mData.get(position);
        final WatchedProgress watchedProgress = watchedProgressWrapper.getWatchedProgress();
        final Show show = watchedProgressWrapper.getShow();
        final boolean selected = watchedProgressWrapper.isSelected();

        holder.mLoaded = watchedProgress != null && !watchedProgress.isCompleted();
        holder.mShowTitle.setText(show.getTitle());
        Picasso.with(mContext).load(show.getImages().getPoster().getThumb()).into(holder.mShowPoster);

        if (holder.mLoaded) {
            holder.mEpisodeTitle.setText(String.format("S%02dE%02d: %s",
                    watchedProgress.getNextEpisode().getSeason(),
                    watchedProgress.getNextEpisode().getNumber(),
                    watchedProgress.getNextEpisode().getTitle()));
            final int percentage = (int) ((double) watchedProgress.getCompleted() * 100 / watchedProgress.getAired());
            holder.mProgressText.setText(String.format("%d/%d (%d%%)", watchedProgress.getCompleted(), watchedProgress.getAired(), percentage));
            holder.mProgressBar.setProgress(percentage);
            holder.mCheck.setEnabled(true);
            holder.mCheck.setSelected(selected);
        } else {
            holder.mEpisodeTitle.setText(mContext.getText(R.string.progress_loading));
            holder.mProgressText.setText("");
            holder.mProgressBar.setProgress(0);
            holder.mCheck.setEnabled(false);
            holder.mCheck.setSelected(selected);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setWatchedProgressWrappers(final List<WatchedProgressWrapper> watchedProgressWrappers) {
        mData.clear();
        mData.addAll(watchedProgressWrappers);
        notifyDataSetChanged();
    }

    public void add(final WatchedProgressWrapper watchedProgressWrapper) {
        final int position = mData.size();
        mData.add(watchedProgressWrapper);
        notifyItemInserted(position);
    }

    public void notifyWatchedProgressWrapperChanged(final WatchedProgressWrapper watchedProgressWrapper) {
        final int position = mData.indexOf(watchedProgressWrapper);
        if (position != -1) {
            notifyItemChanged(position);
        }
    }

    public void remove(final WatchedProgressWrapper watchedProgressWrapper) {
        final int position = mData.indexOf(watchedProgressWrapper);
        if (position != -1) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public interface OnClicked {
            void onCheckClicked(final int position);
        }

        private final TextView mShowTitle;
        private final TextView mEpisodeTitle;
        private final ImageView mShowPoster;
        private final TextView mProgressText;
        private final ProgressBar mProgressBar;
        private final View mCheck;
        private boolean mLoaded;

        public ViewHolder(final View view, final OnClicked listener) {
            super(view);
            mShowPoster = (ImageView) view.findViewById(R.id.progress_item_show_poster);
            mShowTitle = (TextView) view.findViewById(R.id.progress_item_show_title);
            mEpisodeTitle = (TextView) view.findViewById(R.id.progress_item_episode_title);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progress_item_progress_bar);
            mProgressText = (TextView) view.findViewById(R.id.progress_item_progress_text);
            mCheck = view.findViewById(R.id.progress_item_check);
            mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (mCheck.isSelected() || !mLoaded) return;
                    mCheck.setSelected(true);
                    listener.onCheckClicked(getAdapterPosition());
                }
            });
        }
    }
}
