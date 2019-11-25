package com.backbase.cityfiltermap.ui.search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApplication) getApplication()).appComponent.searchComponent().create().inject(this);
        searchComponent = ((MyApplication) getApplicationContext())
                .appComponent.searchComponent().create();
        searchComponent.inject(this);

        setContentView(R.layout.activity_main);
        SearchViewModel model = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        model.init();

        attachCityList();
    }

    private void attachCityList() {
        CityListFragment fr = CityListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fr)
                .commit();
    }

    private void attachMapFragment(LatLng latLng) {
        MapFragment mapFragment = MapFragment.newInstance(latLng);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mapFragment)
                .addToBackStack("map")
                .commit();
    }

    public void moveTo(LatLng coordinate) {
        attachMapFragment(coordinate);
    }
}
