package com.backbase.cityfiltermap.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.di.ViewModelFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

public class MapFragment extends Fragment {

    private static final String KEY_LOCATION = "key_location";

    @Inject
    ViewModelFactory factory;
    private MapView mapView;
    private GoogleMap mMap;

    public MapFragment() {

    }

    public static MapFragment newInstance(LatLng latLng) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_LOCATION, latLng);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).searchComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
            }
        });

        mapView.onCreate(savedInstanceState);

        SearchViewModel model = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        model.getCoordinatesLiveData().observe(this, new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng latLng) {
                moveCameraTo(latLng);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void moveCameraTo(LatLng coordinate) {
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15);

        if (mMap != null) {
            mMap.animateCamera(location);
            mMap.addMarker(new MarkerOptions().position(coordinate).title(""));
        }
    }
}
