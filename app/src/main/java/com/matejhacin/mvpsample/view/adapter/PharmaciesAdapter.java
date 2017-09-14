package com.matejhacin.mvpsample.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matejhacin.mvpsample.R;
import com.matejhacin.mvpsample.data.models.Location;
import com.matejhacin.mvpsample.data.models.Pharmacy;
import com.matejhacin.mvpsample.data.models.Service;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matejhacin on 07/09/2017.
 */

public class PharmaciesAdapter extends RecyclerView.Adapter<PharmaciesAdapter.PharmaciesViewHolder> {

    private List<Pharmacy> pharmacies;
    private int expandedPosition = -1;

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
        notifyDataSetChanged();
    }

    @Override
    public PharmaciesAdapter.PharmaciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pharmacy, parent, false);
        return new PharmaciesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PharmaciesAdapter.PharmaciesViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        final Pharmacy pharmacy = pharmacies.get(position);

        final boolean isExpanded = position == expandedPosition;
        holder.details.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Item can only expand/collapse if it has a service
                if (pharmacy.getService() != null) {
                    expandedPosition = isExpanded ? -1 : position;
                    notifyDataSetChanged();
                }
            }
        });

        // Binding data to view
        Location location = pharmacy.getLocation();
        Service service = pharmacy.getService();

        if (location != null) {
            holder.titleTextView.setText(location.getTitle());
            holder.distanceTextView.setText(
                    String.format(context.getString(R.string.pharmacy_distance), location.getDistance())
            );
        }
        if (service != null) {
            holder.nameTextView.setText(service.getName());
            holder.descriptionTextView.setText(service.getDescription());
            holder.anytimeBookingTextView.setText(
                    String.format(context.getString(R.string.pharmacy_anytime_booking), service.getAnytimeBookingString(context))
            );
            holder.callOnlyTextView.setText(
                    String.format(context.getString(R.string.pharmacy_call_only), service.getCallOnlyString(context))
            );
            holder.priceTextView.setText(
                    String.format(context.getString(R.string.pharmacy_price), service.getPrice())
            );
            holder.quantityTextView.setText(
                    String.format(context.getString(R.string.pharmacy_quantity), service.getQuantity())
            );
            holder.openTimeTextView.setText(
                    String.format(context.getString(R.string.pharmacy_opens), service.getTimeStart())
            );
            holder.closeTimeTextView.setText(
                    String.format(context.getString(R.string.pharmacy_closes), service.getTimeEnd())
            );

            holder.cardView.setCardBackgroundColor(null);
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_gray));
        }
    }

    @Override
    public int getItemCount() {
        return pharmacies != null ? pharmacies.size() : 0;
    }

    public static class PharmaciesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.distanceTextView)
        TextView distanceTextView;
        @BindView(R.id.nameTextView)
        TextView nameTextView;
        @BindView(R.id.descriptonTextView)
        TextView descriptionTextView;
        @BindView(R.id.anytimeBookingTextView)
        TextView anytimeBookingTextView;
        @BindView(R.id.callOnlyTextView)
        TextView callOnlyTextView;
        @BindView(R.id.priceTextView)
        TextView priceTextView;
        @BindView(R.id.quantityTextView)
        TextView quantityTextView;
        @BindView(R.id.openTimeTextView)
        TextView openTimeTextView;
        @BindView(R.id.closeTimeTextView)
        TextView closeTimeTextView;
        @BindView(R.id.detailLinearLayout)
        ViewGroup details;
        @BindView(R.id.cardView)
        CardView cardView;

        public PharmaciesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
