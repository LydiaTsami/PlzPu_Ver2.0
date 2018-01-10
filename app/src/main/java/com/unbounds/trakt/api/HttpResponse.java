package com.unbounds.trakt.api;

import java.util.Map;

/**
 * Created by maclir on 2015-11-08.
 */
public interface HttpResponse {
    Map<String, String> getHeaders();

    String getBody();

    <T> T getModel(Class<T> ofClass);

    String getHeader(String name);
}
