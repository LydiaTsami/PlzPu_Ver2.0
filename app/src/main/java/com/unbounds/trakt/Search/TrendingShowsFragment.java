package com.unbounds.trakt.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.unbounds.trakt.BuildConfig;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Show;

import java.util.ArrayList;

/**
 * Created by lydts on 1/12/2018.
 */

public class TrendingShowsFragment extends Fragment implements IShowsLoadedListener,AdapterView.OnItemClickListener {
    private static int ARGUMENT_TYPE;
    private TrendingShowAdapter adapter;
    public static ArrayList<Show> mShowList;
    private GridView gridview;

    int page_count = 1;
    int max_pages = 100;  //initially

    public TrendingShowsFragment(){
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mShowList = new ArrayList<>();
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        gridview = (GridView) view.findViewById(R.id.search_gridview);
        gridview.setOnScrollListener(onScrollListener());
        gridview.setOnItemClickListener(this);

        String url = BuildConfig.BASE_API_URL + "/shows/trending?page=" +page_count + "&extended=full";
        getDataFromUrl(url);
        adapter = new TrendingShowAdapter(getActivity(), mShowList);
        gridview.setAdapter(adapter);

        return view;

    }

    @Override
    public void onShowsLoaded(ArrayList<Show> shows, int scroll) {
        mShowList.addAll(shows);
        adapter.notifyDataSetChanged();
    }

    private void getDataFromUrl(String url) {
        new LoadShowsFromUrlTask(getActivity(), url, this).execute();
    }

    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = gridview.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (gridview.getLastVisiblePosition() >= count - threshold && page_count < max_pages) {
                        page_count++;
                        // Execute LoadMoreDataTask AsyncTask
                        String url = BuildConfig.BASE_API_URL + "/shows/trending?page=" +page_count+ "&extended=full";
                        getDataFromUrl(url);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Show show = mShowList.get(position);

        Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
        intent.putExtra("title",show.getTitle());
        intent.putExtra("year",show.getYear());
        intent.putExtra("overview",show.getOverview());
        intent.putExtra("imdb",show.getImdb());
        intent.putExtra("tmdb",show.getTmdb());
        intent.putExtra("trailer",show.getTrailer());
        intent.putExtra("rating",show.getRating());
        intent.putExtra("runtime",show.getRuntime());

        startActivity(intent);
    }
}