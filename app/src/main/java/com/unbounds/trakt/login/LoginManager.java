package com.unbounds.trakt.login;

import android.content.SharedPreferences;

import com.unbounds.trakt.TraktApplication;
import com.unbounds.trakt.api.HttpRequest;
import com.unbounds.trakt.api.model.response.Token;
import com.unbounds.trakt.json.JsonSerializer;

/**
 * Created by maclir on 2015-12-06.
 */
public class LoginManager {
    private static final String PREFERENCES_NAME = "Login";
    private static final String KEY_TOKEN = "KEY_TOKEN";

    private static LoginManager ourInstance;

    private Token mToken;

    public static LoginManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new LoginManager();
        }
        return ourInstance;
    }

    private LoginManager() {
        final SharedPreferences sharedPreferences = TraktApplication.CONTEXT.getSharedPreferences(PREFERENCES_NAME, 0);
        setToken(JsonSerializer.fromJson(sharedPreferences.getString(KEY_TOKEN, null), Token.class));
    }

    public boolean isLoggedIn() {
        return mToken != null;
    }

    public void setToken(final Token token) {
        mToken = token;
        final SharedPreferences sharedPreferences = TraktApplication.CONTEXT.getSharedPreferences(PREFERENCES_NAME, 0);

        if (token == null) {
            HttpRequest.HEADERS.remove("Authorization");
            sharedPreferences.edit().remove(KEY_TOKEN).apply();
        } else {
            HttpRequest.HEADERS.put("Authorization", String.format("Bearer %s", mToken.getAccessToken()));
            sharedPreferences.edit().putString(KEY_TOKEN, JsonSerializer.toJson(token)).apply();
        }
    }
}
