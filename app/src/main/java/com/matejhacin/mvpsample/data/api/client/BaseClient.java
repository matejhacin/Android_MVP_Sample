package com.matejhacin.mvpsample.data.api.client;

import com.google.gson.Gson;
import com.matejhacin.mvpsample.data.api.Constants;
import com.matejhacin.mvpsample.data.api.http.HTTPClient;

/**
 * Created by matejhacin on 07/09/2017.
 */

class BaseClient {

    HTTPClient httpClient;
    Gson gson;

    BaseClient() {
        httpClient = new HTTPClient(Constants.BASE_URL);
        gson = new Gson();
        initHeaders();
    }

    void initHeaders() {
        // Init headers that should be common for all requests here
        httpClient.addHeader("Content-Type", "application/json");
        httpClient.addHeader("Accept", "application/json");
        httpClient.addHeader("Token", Constants.Authorization.AUTH_TOKEN); // Auth token is a constant for demo purposes
    }

}
