package com.matejhacin.mvpsample.data.api.http;

/**
 * Created by matejhacin on 07/09/2017.
 */

// Only added GET for now since this simple HTTP client only supports this for now
public enum HTTPMethod {
    GET("GET");

    private String value;

    HTTPMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
