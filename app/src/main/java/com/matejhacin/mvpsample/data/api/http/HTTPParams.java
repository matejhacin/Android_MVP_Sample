package com.matejhacin.mvpsample.data.api.http;

import java.util.HashMap;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class HTTPParams {

    private HashMap<String, String> params;

    public HTTPParams() {
        params = new HashMap<>();
    }

    public void add(String key, String value) {
        if (key == null || value == null) return;
        params.put(key, value);
    }

    HashMap<String, String> getParams() {
        return params;
    }
}
