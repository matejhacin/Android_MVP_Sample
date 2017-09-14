package com.matejhacin.mvpsample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class ServiceList {

    @SerializedName("data")
    @Expose
    private List<Service> services = null;

    public List<Service> getServices() {
        return services;
    }
}
