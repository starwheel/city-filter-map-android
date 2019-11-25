package com.backbase.cityfiltermap.ui.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.ui.models.SearchEntity;

public class SearchListAdapter extends ListAdapter<SearchEntity, SearchListAdapter.ViewHolder> {


    public SearchListAdapter(@NonNull AsyncDifferConfig<SearchEntity> config) {
        super(config);
    }

    public SearchListAdapter(@NonNull DiffUtil.ItemCallback<SearchEntity> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchEntity entity = getItem(position);

        holder.tvCity.setText(entity.getName() + ", " + entity.getCountry());
        holder.tvCoords.setText(entity.getCoord().getLat() + " " + entity.getCoord().getLon());
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;
        TextView tvCoords;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvCoords = itemView.findViewById(R.id.tvCoords);
        }
    }

}
