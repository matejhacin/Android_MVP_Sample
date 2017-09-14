package com.matejhacin.mvpsample.data.models;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class Pharmacy {

    private Location location;
    private Service service;

    public Pharmacy(Location location, Service service) {
        this.location = location;
        this.service = service;
    }

    public Location getLocation() {
        return location;
    }

    public Service getService() {
        return service;
    }

    public static List<Pharmacy> createPharmacies(List<Pair<Location, Service>> pairs) {
        List<Pharmacy> pharmacies = new ArrayList<>();

        for (Pair<Location, Service> pair : pairs) {
            Pharmacy pharmacy = new Pharmacy(pair.first, pair.second);
            pharmacies.add(pharmacy);
        }

        return pharmacies;
    }
}
