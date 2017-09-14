package com.matejhacin.mvpsample.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matejhacin.mvpsample.R;
import com.matejhacin.mvpsample.data.api.client.PharmaciesClient;
import com.matejhacin.mvpsample.data.models.Pharmacy;
import com.matejhacin.mvpsample.presenter.PharmaciesPresenter;
import com.matejhacin.mvpsample.view.adapter.PharmaciesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PharmaciesActivity extends AppCompatActivity implements PharmaciesPresenter.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorTextView)
    TextView errorTextView;

    private PharmaciesPresenter pharmaciesPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        ButterKnife.bind(this);
        setUpRecyclerView();

        pharmaciesPresenter = new PharmaciesPresenter(new PharmaciesClient());
        pharmaciesPresenter.setView(this);
        pharmaciesPresenter.loadPharmacies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        pharmaciesPresenter.terminate();
    }

    @Override
    public void showLoadingState() {
        recyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorState() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderPharmacies(List<Pharmacy> pharmacies) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        PharmaciesAdapter adapter = (PharmaciesAdapter) recyclerView.getAdapter();
        adapter.setPharmacies(pharmacies);
    }

    @Override
    public Context context() {
        return PharmaciesActivity.this;
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PharmaciesAdapter());
    }
}
