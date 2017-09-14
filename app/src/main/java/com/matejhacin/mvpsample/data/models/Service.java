package com.matejhacin.mvpsample.data.models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.matejhacin.mvpsample.view.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class Service {

    @SerializedName("pharmacy_id")
    @Expose
    private String pharmacyId;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("anytime_booking")
    @Expose
    private Boolean anytimeBooking;
    @SerializedName("call_only")
    @Expose
    private Boolean callOnly;
    @SerializedName("is_pinned")
    @Expose
    private Boolean isPinned;
    @SerializedName("service_description")
    @Expose
    private String description;
    @SerializedName("service_enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("service_icon")
    @Expose
    private String icon;
    @SerializedName("service_name")
    @Expose
    private String name;
    @SerializedName("service_price")
    @Expose
    private Integer price;
    @SerializedName("service_quantity")
    @Expose
    private Integer quantity;
    @SerializedName("time_end")
    @Expose
    private Integer timeEnd;
    @SerializedName("time_start")
    @Expose
    private Integer timeStart;

    /*
    Getters
     */

    public String getPharmacyId() {
        return pharmacyId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getAnytimeBookingString(Context context) {
        return StringUtils.booleanToYesOrNo(context, anytimeBooking);
    }

    public String getCallOnlyString(Context context) {
        return StringUtils.booleanToYesOrNo(context, callOnly);
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getTimeEnd() {
        return millisecondsToTimeFormat(timeEnd);
    }

    public String getTimeStart() {
        return millisecondsToTimeFormat(timeStart);
    }

    /*
    Private
     */

    private String millisecondsToTimeFormat(int milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mma");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }
}
