package com.unbounds.trakt.progress;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unbounds.trakt.ApiWrapper;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.response.WatchedProgress;
import com.unbounds.trakt.api.model.response.WatchedShow;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class ProgressFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_progress, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.progress_recycle_view);
        recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final Adapter adapter = new Adapter(getActivity());
        recyclerView.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.progress_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.moonlight_blue,
                R.color.boogie_green,
                R.color.space_gray,
                R.color.cool_gray
        );
        final Action1<WatchedShow[]> loadShowsAction = new Action1<WatchedShow[]>() {
            @Override
            public void call(final WatchedShow[] watchedShows) {
                adapter.clear();
                final List<Observable<WatchedProgressWrapper>> observables = new ArrayList<>(watchedShows.length);
                final List<WatchedProgressWrapper> watchedProgressWrappers = new ArrayList<>();
                for (final WatchedShow watchedShow : watchedShows) {
                    final WatchedProgressWrapper watchedProgressWrapper = new WatchedProgressWrapper(watchedShow.getShow());
                    watchedProgressWrappers.add(watchedProgressWrapper);
                    observables.add(ApiWrapper.getWatchedProgress(watchedShow.getShow().getIds().getTrakt()).map(new Func1<WatchedProgress, WatchedProgressWrapper>() {
                        @Override
                        public WatchedProgressWrapper call(final WatchedProgress watchedProgress) {
                            return watchedProgressWrapper.setWatchedProgress(watchedProgress);
                        }
                    }));
                }
                adapter.setWatchedProgressWrappers(watchedProgressWrappers);

                Observable.merge(observables).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).subscribe(new Action1<WatchedProgressWrapper>() {
                    @Override
                    public void call(final WatchedProgressWrapper watchedProgressWrapper) {
                        if (!watchedProgressWrapper.getWatchedProgress().isCompleted()) {
                            adapter.notifyWatchedProgressWrapperChanged(watchedProgressWrapper);
                        } else {
                            adapter.remove(watchedProgressWrapper);
                        }
                    }
                });
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiWrapper.getWatchedShows().subscribe(loadShowsAction);
            }
        });
        ApiWrapper.getWatchedShows().subscribe(loadShowsAction);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        return view;
    }
}
