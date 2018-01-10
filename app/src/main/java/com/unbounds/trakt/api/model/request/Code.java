package com.unbounds.trakt.api.model.request;

import com.unbounds.trakt.BuildConfig;

/**
 * Created by maclir on 2015-11-08.
 */
public class Code {
    private final String code;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public Code(final String code, final String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri;
        clientId = BuildConfig.CLIENT_ID;
        clientSecret = BuildConfig.CLIENT_SECRET;
        grantType = "authorization_code";
    }
}
