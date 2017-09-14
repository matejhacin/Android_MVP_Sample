package com.matejhacin.mvpsample.data.api.client;

import android.util.Pair;

import com.matejhacin.mvpsample.data.api.Constants;
import com.matejhacin.mvpsample.data.api.http.HTTPParams;
import com.matejhacin.mvpsample.data.models.Location;
import com.matejhacin.mvpsample.data.models.LocationList;
import com.matejhacin.mvpsample.data.models.Pharmacy;
import com.matejhacin.mvpsample.data.models.Service;
import com.matejhacin.mvpsample.data.models.ServiceList;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class PharmaciesClient extends BaseClient {

    interface LocationListListener {
        void onLocationsReceived(List<Location> locations);
    }

    interface LocationServiceListener {
        void onLocationPairsReceived(List<Pair<Location, Service>> pairs);
    }

    public interface PharmacyListener {
        void onPharmaciesReceived(List<Pharmacy> pharmacies);
        void onError();
    }

    public void loadLocations(final LocationListListener listener) {
        HTTPParams params = new HTTPParams();
        params.add("lat", "51.657");
        params.add("long", "0.121");
        params.add("radius", "50000.0");
        params.add("unit", "km");

        httpClient.get(Constants.Endpoint.LOCATIONS, params)
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String json) {
                LocationList locations = gson.fromJson(json, LocationList.class);
                listener.onLocationsReceived(locations.getLocations());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                listener.onLocationsReceived(null);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void loadPharmacies(final PharmacyListener listener) {
        loadLocations(new LocationListListener() {
            @Override
            public void onLocationsReceived(List<Location> locations) {
                loadServices(locations, new LocationServiceListener() {
                    @Override
                    public void onLocationPairsReceived(List<Pair<Location, Service>> pairs) {
                        if (listener != null) {
                            if (pairs != null) {
                                listener.onPharmaciesReceived(Pharmacy.createPharmacies(pairs));
                            } else {
                                listener.onError();
                            }
                        }
                    }
                });
            }
        });
    }

    private void loadServices(List<Location> locations, final LocationServiceListener listener) {
        if (locations == null) {
            listener.onLocationPairsReceived(null);
            return;
        }


        Observable.fromIterable(locations)
                .flatMap(new Function<Location, ObservableSource<Pair<Location, Service>>>() {
                    @Override
                    public ObservableSource<Pair<Location, Service>> apply(@NonNull Location location) throws Exception {
                        return Observable.zip(
                                Observable.just(location),
                                httpClient.get(Constants.Endpoint.PHARMACIES.replace(Constants.EndpointParameter.PLACE_ID, location.getId()), null),
                                new BiFunction<Location, String, Pair<Location, Service>>() {
                                    @Override
                                    public Pair<Location, Service> apply(@NonNull Location location, @NonNull String json) throws Exception {
                                        ServiceList serviceList = gson.fromJson(json, ServiceList.class);
                                        Service service = null;
                                        if (!serviceList.getServices().isEmpty()) {
                                            service = serviceList.getServices().get(0);
                                        }
                                        return new Pair<Location, Service>(location, service);
                                    }
                                }
                        );
                    }
                })
        .toList()
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<List<Pair<Location, Service>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull List<Pair<Location, Service>> pairs) {
                if (listener != null)
                    listener.onLocationPairsReceived(pairs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (listener != null)
                    listener.onLocationPairsReceived(null);
            }
        });
    }

}