package com.unbounds.trakt.api.model.response;

/**
 * Created by maclir on 2015-11-08.
 */
public class Token {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }
}
