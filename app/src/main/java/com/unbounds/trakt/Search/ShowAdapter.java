package com.unbounds.trakt.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Show;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lydts on 1/12/2018.
 */

class ShowAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<ShowWrapper> mWrappedShows = new ArrayList<>();

    ShowAdapter(final Context context) {
        mContext = context;
    }

    public int getCount() {
        return mWrappedShows.size();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.show_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mShowPoster = (ImageView) convertView.findViewById(R.id.gridview_item_show_poster);
            viewHolder.mShowTitle = (TextView) convertView.findViewById(R.id.gridview_item_show_title);
            viewHolder.mShowYear = (TextView) convertView.findViewById(R.id.gridview_item_show_year);
            viewHolder.mShowWatchers = (TextView) convertView.findViewById(R.id.gridview_item_show_watchers_amount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Show show = mWrappedShows.get(position).getShow();
        Picasso.with(mContext).load(show.getImages().getPoster().getThumb()).into(viewHolder.mShowPoster);
        viewHolder.mShowTitle.setText(show.getTitle());
        viewHolder.mShowYear.setText(String.valueOf(show.getYear()));
        if (mWrappedShows.get(position).hasWatchers()) {
            viewHolder.mShowWatchers.setVisibility(View.VISIBLE);
            viewHolder.mShowWatchers.setText(String.valueOf(mWrappedShows.get(position).getWatchers()));
        }
        return convertView;
    }

    @Override
    public Object getItem(final int position) {
        return null;
    }

    public long getItemId(final int position) {
        return 0;
    }

    public void setData(final ShowWrapper[] shows) {
        mWrappedShows.addAll(Arrays.asList(shows));
        notifyDataSetChanged();
    }

    static class ViewHolder {

        private TextView mShowTitle;
        private ImageView mShowPoster;
        private TextView mShowWatchers;
        private TextView mShowYear;

    }
}