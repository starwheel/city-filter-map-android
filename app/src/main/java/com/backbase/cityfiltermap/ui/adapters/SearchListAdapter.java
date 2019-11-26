package com.backbase.cityfiltermap.ui.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.domain.model.SearchEntity;
import com.google.android.gms.maps.model.LatLng;

public class SearchListAdapter extends ListAdapter<SearchEntity, SearchListAdapter.ViewHolder> {

    private SearchListCallback callback;
    private LatLng selectedCity;

    public SearchListAdapter(SearchListCallback callback, @NonNull DiffUtil.ItemCallback<SearchEntity> diffCallback) {
        super(diffCallback);
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final SearchEntity entity = getItem(position);

        String city = entity.getName() + ", " + entity.getCountry();
        holder.tvCity.setText(city);
        String coordinates = entity.getCoord().getLat() + " " + entity.getCoord().getLon();
        holder.tvCoords.setText(coordinates);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBackground(holder, entity, selectedCity);
                callback.onSearchEntityClicked(entity);
            }
        });

        updateBackground(holder, entity, selectedCity);
    }

    private void updateBackground(ViewHolder holder, SearchEntity entity, LatLng selectedCity) {
        int color;

        if (selectedCity != null && entity.getCoord().getLat().equals(selectedCity.latitude) &&
                entity.getCoord().getLon().equals(selectedCity.longitude)) {
            color = ContextCompat.getColor(holder.container.getContext(),
                    R.color.listSelectedItemBackground
            );
        } else {
            color = ContextCompat.getColor(holder.container.getContext(), R.color.listBackground);
        }

        holder.container.setBackgroundColor(color);
    }

    public void setSelectedCity(LatLng latLng) {
        this.selectedCity = latLng;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;
        TextView tvCoords;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvCoords = itemView.findViewById(R.id.tvCoords);
        }
    }
}
