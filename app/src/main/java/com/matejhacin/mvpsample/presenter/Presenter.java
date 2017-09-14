package com.matejhacin.mvpsample.presenter;

import android.content.Context;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class Presenter<T extends Presenter.View> {

    private T View;

    public void setView(T view) {
        View = view;
    }

    public T getView() {
        return View;
    }

    public void terminate() {
        setView(null);
    }

    public interface View {
        Context context();
    }
}
