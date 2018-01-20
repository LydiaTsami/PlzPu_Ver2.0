package android.uom.trakt.Search;

import android.uom.trakt.Show;

/**
 * Created by lydts on 1/12/2018.
 */

public class ShowWrapper {

    private final Long mWatchers;
    private final Show mShow;

    ShowWrapper(final Long watchers, final Show show) {
        mWatchers = watchers;
        mShow = show;
    }

    public ShowWrapper(final Show show) {
        mWatchers = null;
        mShow = show;
        new LoadShowImagesFromUrlTask(show).execute();
    }

    public long getWatchers() {
        return mWatchers;
    }

    public Show getShow() {
        return mShow;
    }

    public boolean hasWatchers() {
        return mWatchers == null ? false : true;
    }
}