package com.matejhacin.mvpsample.data.api.http;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class HTTPClient {

    private HashMap<String, String> headerMap;
    private String baseUrl;

    public HTTPClient(String baseUrl) {
        headerMap = new HashMap<>();
        this.baseUrl = baseUrl;
    }

    /*
    Public
     */

    public void addHeader(String key, String value) {
        if (key == null || value == null) return;
        headerMap.put(key, value);
    }

    public Observable<String> get(String endpoint, @Nullable HTTPParams params) {
        return request(endpoint, HTTPMethod.GET, params);
    }

    /*
    Private
     */

    private Observable<String> request(final String endpoint, final HTTPMethod method, @Nullable HTTPParams params) {
        final String requestUrl = createUrl(endpoint, params);

        return Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                try {
                    return Observable.just(downloadData(requestUrl, method));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private String downloadData(String url, HTTPMethod method) throws IOException {
        if (url == null || method == null) throw null;

        InputStream inputStream = null;

        try {
            URL requestUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) requestUrl.openConnection();
            httpConnection.setRequestMethod(method.getValue());
            appendHeaders(httpConnection);
            httpConnection.connect();

            inputStream = httpConnection.getInputStream();
            return convertToString(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private void appendHeaders(HttpURLConnection connection) {
        for (String key : headerMap.keySet()) {
            String value = headerMap.get(key);
            connection.setRequestProperty(key, value);
        }
    }

    private String convertToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return new String(stringBuilder);
    }

    private String createUrl(String endpoint, @Nullable HTTPParams params) {
        Uri.Builder uri = Uri.parse(baseUrl)
                .buildUpon()
                .path(endpoint);

        if (params != null) {
            HashMap<String, String> paramsMap = params.getParams();
            for (String key : paramsMap.keySet()) {
                String value = paramsMap.get(key);
                uri.appendQueryParameter(key, value);
            }
        }

        return uri.build().toString();
    }

}
