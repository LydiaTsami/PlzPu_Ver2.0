package com.unbounds.trakt;

import com.unbounds.trakt.api.HttpRequest;
import com.unbounds.trakt.api.RxRequest;
import com.unbounds.trakt.api.model.request.Code;
import com.unbounds.trakt.api.model.request.WatchedItems;
import com.unbounds.trakt.api.model.response.AddHistory;
import com.unbounds.trakt.api.model.response.Token;
import com.unbounds.trakt.api.model.response.WatchedProgress;
import com.unbounds.trakt.api.model.response.WatchedShow;

import rx.Observable;

/**
 * Created by maclir on 11/7/15.
 */
public class ApiWrapper {
    public static Observable<Token> getToken(final Code code) {
        return new RxRequest() {
            @Override
            protected HttpRequest request() {
                return new HttpRequest("%s/oauth/token", BuildConfig.BASE_URL).post(code);
            }
        }.asObservable(Token.class);
    }

    public static Observable<WatchedShow[]> getWatchedShows() {
        return new RxRequest() {
            @Override
            protected HttpRequest request() {
                return new HttpRequest("/sync/watched/shows?extended=images").get();
            }
        }.asObservable(WatchedShow[].class);
    }

    public static Observable<WatchedProgress> getWatchedProgress(final long showId) {
        return new RxRequest() {
            @Override
            protected HttpRequest request() {
                return new HttpRequest("/shows/%d/progress/watched", showId).get();
            }
        }.asObservable(WatchedProgress.class);
    }

    public static Observable<AddHistory> postWatchedItems(final WatchedItems watchedItems) {
        return new RxRequest() {
            @Override
            protected HttpRequest request() {
                return new HttpRequest("/sync/history").post(watchedItems);
            }
        }.asObservable(AddHistory.class);
    }
}
