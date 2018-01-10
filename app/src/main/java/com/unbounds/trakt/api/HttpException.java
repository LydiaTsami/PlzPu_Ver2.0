package com.unbounds.trakt.api;

import java.io.IOException;

/**
 * Created by maclir on 2015-11-08.
 */
public class HttpException extends RuntimeException {

    public static final int NO_INTERNET_CONNECTION_CODE = 0;

    public static class HttpIOException extends HttpException {
        HttpIOException(final IOException exception, final String method, final String url) {
            super(exception.getMessage(), exception, method, url);
        }
    }

    private final int mCode;
    private final String mBody;
    private final String mMethod;
    private final String mUrl;

    HttpException(final int code, final String body, final String method, final String url) {
        mCode = code;
        mBody = body;
        mMethod = method;
        mUrl = url;
    }

    /**
     * Called from HttpIOException
     */
    private HttpException(final String message, final Throwable cause, final String method, final String url) {
        super(message, cause);
        mCode = NO_INTERNET_CONNECTION_CODE;
        mBody = "";
        mMethod = method;
        mUrl = url;
    }

    /**
     * Returns the response status code.
     * This value can also be 0 if there are I/O errors.
     */
    public int getCode() {
        return mCode;
    }

    /**
     * This value may be an empty string.
     */
    public String getBody() {
        return mBody;
    }

    /**
     * Return the request method
     */
    public String getMethod() {
        return mMethod;
    }

    /**
     * Return the request url
     */
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "HttpException{" +
                "mCode=" + mCode +
                ", mBody='" + mBody + '\'' +
                ", mMethod='" + mMethod + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

}
