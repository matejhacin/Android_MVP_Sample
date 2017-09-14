package com.matejhacin.mvpsample.presenter;

import com.matejhacin.mvpsample.data.api.client.PharmaciesClient;
import com.matejhacin.mvpsample.data.models.Pharmacy;

import java.util.List;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class PharmaciesPresenter extends Presenter<PharmaciesPresenter.View> implements PharmaciesClient.PharmacyListener {

    private PharmaciesClient client;

    public PharmaciesPresenter(PharmaciesClient client) {
        this.client = client;
    }

    public void loadPharmacies() {
        getView().showLoadingState();
        client.loadPharmacies(this);
    }

    /*
    PharmacyListener
     */

    @Override
    public void onPharmaciesReceived(List<Pharmacy> pharmacies) {
        if (pharmacies != null) {
            getView().renderPharmacies(pharmacies);
        } else {
            getView().showErrorState();
        }
    }

    @Override
    public void onError() {
        getView().showErrorState();
    }

    /*
    End PharmacyListener
     */

    public interface View extends Presenter.View {
        void showLoadingState();
        void showErrorState();
        void renderPharmacies(List<Pharmacy> pharmacies);
    }
}
