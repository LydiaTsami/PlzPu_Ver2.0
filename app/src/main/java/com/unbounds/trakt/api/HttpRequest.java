package com.unbounds.trakt.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.unbounds.trakt.BuildConfig;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by maclir on 2015-11-08.
 */
public class HttpRequest {

    public static Map<String, String> HEADERS = new ConcurrentHashMap<>();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    private static final OkHttpClient OK_HTTP = new OkHttpClient();
    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private final Request.Builder mBuilder = new Request.Builder();

    public HttpRequest(final String url) {
        initialize(url);
    }

    public HttpRequest(final String url, final Object... args) {
        initialize(String.format(Locale.ENGLISH, url, args));
    }

    private void initialize(final String url) {
        mBuilder.url(url.startsWith("http") ? url :
                BuildConfig.BASE_API_URL.replaceAll("/$", "") + "/" + url.replaceAll("^/", ""));
        for (final Map.Entry<String, String> header : HEADERS.entrySet()) {
            header(header.getKey(), header.getValue());
        }
    }

    public HttpRequest header(final String name, final String value) {
        mBuilder.header(name, value);
        return this;
    }

    public HttpRequest post() {
        mBuilder.post(null);
        return this;
    }

    public HttpRequest post(final Object model) {
        mBuilder.post(RequestBody.create(MEDIA_TYPE_JSON, GSON.toJson(model).getBytes()));
        return this;
    }

    public HttpRequest get() {
        mBuilder.get();
        return this;
    }

    public HttpRequest put(final Object model) {
        mBuilder.put(RequestBody.create(MEDIA_TYPE_JSON, GSON.toJson(model).getBytes()));
        return this;
    }

    public HttpRequest put() {
        mBuilder.put(null);
        return this;
    }

    public HttpRequest delete() {
        mBuilder.delete();
        return this;
    }

    /**
     * This method will throw HttpException unless the response code is 200, 201, 202, or 204.
     */
    public HttpResponse execute() throws HttpException {
        final Request request = mBuilder.build();
        try {
            final Call mCall = OK_HTTP.newCall(request);
            final Response response = mCall.execute();

            // Headers
            final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            for (final String key : response.headers().names()) {
                headers.put(key, response.headers().get(key));
            }

            // Body
            final String body = parseResponse(request, response);

            return new HttpResponse() {
                /**
                 * Return a map of header key-value-pairs. Duplicates are not allowed.
                 */
                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }

                @Override
                public String getBody() {
                    return body;
                }

                @Override
                public String getHeader(final String name) {
                    return headers.get(name);
                }

                @Override
                public <T> T getModel(final Class<T> ofClass) {
                    try {
                        return GSON.fromJson(body, ofClass);
                    } catch (final JsonSyntaxException exception) {
                        throw new HttpRequestException(body, ofClass);
                    }
                }

            };

        } catch (final IOException exception) {
            throw new HttpException.HttpIOException(exception, request.method(), request.urlString());
        }
    }

    private String parseResponse(final Request request, final Response response) throws HttpException {
        String body = "";
        try {
            body = response.body().string();
        } catch (final IOException ignored) {
        } finally {
            final int statusCode = response.code();
            if (statusCode != 200 && statusCode != 201 && statusCode != 202 && statusCode != 204) {
                throw new HttpException(statusCode, body, request.method(), request.urlString());
            }
        }

        return body;
    }

    private class HttpRequestException extends RuntimeException {
        HttpRequestException(final String body, final Class ofClass) {
            super(String.format("Could not parse:\n%s\nas %s", body, ofClass.getCanonicalName()));
        }
    }
}
