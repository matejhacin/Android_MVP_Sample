package com.matejhacin.mvpsample.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class Location {

    @SerializedName("place_id")
    @Expose
    private String id;
    @SerializedName("place_type")
    @Expose
    private String type;
    @SerializedName("place_title")
    @Expose
    private String title;
    @SerializedName("long")
    @Expose
    private String lng;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("current_distance")
    @Expose
    private String distance;

    /*
    Getters
     */

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getDistance() {
        return distance;
    }
}
