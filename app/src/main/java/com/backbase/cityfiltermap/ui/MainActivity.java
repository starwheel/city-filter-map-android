package com.backbase.cityfiltermap.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backbase.cityfiltermap.MyApplication;
import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.di.ViewModelFactory;
import com.backbase.cityfiltermap.ui.adapters.SearchListAdapter;
import com.backbase.cityfiltermap.ui.adapters.SearchListCallback;
import com.backbase.cityfiltermap.ui.models.SearchEntity;
import com.backbase.cityfiltermap.ui.search.SearchViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, SearchListCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private SearchViewModel model;

    @Inject
    ViewModelFactory factory;
    private EditText editText;
    private RecyclerView rvList;
    private SearchListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).appComponent.searchComponent().create().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        rvList = findViewById(R.id.rvList);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        model = ViewModelProviders.of(this,factory).get(SearchViewModel.class);

        model.getSearchLiveData().observe(this, new Observer<List<SearchEntity>>() {
            @Override
            public void onChanged(List<SearchEntity> searchEntities) {
                Log.d(TAG, "onChanged: " + searchEntities.size());
                listAdapter.submitList(null); // It is faster to first erase the list
                listAdapter.submitList(searchEntities);
            }
        });

        listAdapter = new SearchListAdapter(this, new DiffUtil.ItemCallback<SearchEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull SearchEntity oldItem, @NonNull SearchEntity newItem) {
                return oldItem.get_id().equals(newItem.get_id());
            }

            @Override
            public boolean areContentsTheSame(@NonNull SearchEntity oldItem, @NonNull SearchEntity newItem) {
                return oldItem.equals(newItem);
            }
        });

        rvList.setAdapter(listAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                model.filterByKey(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        model.init().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean initiated) {
                if (initiated != null) {
                    editText.setEnabled(initiated);
                } else {
                    editText.setEnabled(false);
                }
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onSearchEntityClicked(SearchEntity searchEntity) {
        if (searchEntity.getCoord() != null) {
            LatLng coordinate = new LatLng(searchEntity.getCoord().getLat(),
                    searchEntity.getCoord().getLon());
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 15);

            if (mMap != null) {
                mMap.animateCamera(location);
            }
        }
    }
}
