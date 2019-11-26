package com.backbase.cityfiltermap.ui.search;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.backbase.cityfiltermap.MyApplication;
import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.di.ViewModelFactory;
import com.backbase.cityfiltermap.ui.di.SearchComponent;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory factory;
    SearchComponent searchComponent;
    private FrameLayout mapContainer;
    private SearchViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        searchComponent =
                ((MyApplication) getApplicationContext()).appComponent.searchComponent().create();
        searchComponent.inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        model.init();
        mapContainer = findViewById(R.id.map_container);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && mapContainer != null) {
            attachListFragment();
            attachMapFragment(true);
        } else {
            attachListFragment();
        }
    }

    private void attachListFragment() {
        Fragment fr = getSupportFragmentManager().findFragmentByTag("city_list");

        if (fr == null) {
            fr = CityListFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fr, "city_list")
                .commit();
    }


    private void attachMapFragment(Boolean dualPane) {
        MapFragment mapFragment = MapFragment.newInstance(null);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if (dualPane) {
            transaction
                .replace(R.id.map_container, mapFragment)
                .commit();
        } else {
            transaction
                .replace(R.id.fragment_container, mapFragment)
                .addToBackStack("map")
                .commit();
        }

    }

    public void moveTo(LatLng coordinate) {
        int orientation = getResources().getConfiguration().orientation;
        model.moveToEntityDetails(coordinate);
        if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
            attachMapFragment(false);
        }
    }
}
