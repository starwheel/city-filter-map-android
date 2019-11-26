package com.backbase.cityfiltermap.ui.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.domain.model.SearchEntity;

public class SearchListAdapter extends ListAdapter<SearchEntity, SearchListAdapter.ViewHolder> {

    private SearchListCallback callback;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SearchEntity entity = getItem(position);

        holder.tvCity.setText(entity.getName() + ", " + entity.getCountry());
        holder.tvCoords.setText(entity.getCoord().getLat() + " " + entity.getCoord().getLon());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onSearchEntityClicked(entity);
            }
        });
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
