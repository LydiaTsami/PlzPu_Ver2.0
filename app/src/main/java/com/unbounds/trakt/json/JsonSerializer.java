package com.unbounds.trakt.json;

/**
 * Created by maclir on 2015-12-06.
 */

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serializes any POJO to Json.
 * <p/>
 * Created by maclir on 2015-12-06.
 */
public final class JsonSerializer {

    private JsonSerializer() {
    }

    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(
            FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public static <T> T fromJson(final String json, final Class<T> classOfT) {
        try {
            return json == null ? null : GSON.fromJson(json, classOfT);
        } catch (final Exception exception) {
            throw new JsonSerializerException(json, classOfT);
        }
    }

    public static String toJson(final Object object) {
        return GSON.toJson(object);
    }

    public static String toJson(final Object object, final Class className) {
        return GSON.toJson(object, className);
    }

    static class JsonSerializerException extends RuntimeException {
        JsonSerializerException(final String json, final Class classOfT) {
            super(String.format("Could not parse:\n%s\nas %s", json, classOfT.getCanonicalName()));
        }
    }
}
