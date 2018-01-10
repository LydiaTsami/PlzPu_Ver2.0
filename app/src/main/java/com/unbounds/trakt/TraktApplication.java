package com.unbounds.trakt;

import android.app.Application;
import android.content.Context;

import com.squareup.okhttp.MediaType;
import com.unbounds.trakt.api.HttpRequest;
import com.unbounds.trakt.login.LoginManager;

/**
 * Created by maclir on 2015-11-16.
 */
public class TraktApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        HttpRequest.HEADERS.put("Content-type", MediaType.parse("application/json").toString());
        HttpRequest.HEADERS.put("trakt-api-key", BuildConfig.CLIENT_ID);
        HttpRequest.HEADERS.put("trakt-api-version", "2");

        // Initialize managers
        LoginManager.getInstance();
    }
}
