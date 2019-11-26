package com.backbase.cityfiltermap.ui.search;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private LatLng placeLocation;

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
        ((MainActivity) context).searchComponent.inject(this);

        super.onAttach(context);
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
        ImageView ivBack = view.findViewById(R.id.ivBack);
        if (getArguments() != null) {
            placeLocation = getArguments().getParcelable(KEY_LOCATION);
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                if (placeLocation != null) {
                    moveCameraTo(placeLocation);
                }
            }
        });

        mapView.onCreate(savedInstanceState);

        SearchViewModel model = ViewModelProviders.of(getActivity(), factory).get(SearchViewModel.class);
        model.getCoordinatesLiveData().observe(this, new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng latLng) {
                placeLocation = latLng;
                moveCameraTo(latLng);
            }
        });

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroy() {
        // Always check if mapView is not null, onDestroy() might be called before onViewCreated().
        // Alternatively cleanup mapView in the onDestroyView() instead of onDestroy().
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

    public void moveCameraTo(LatLng coordinate) {
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15);

        if (mMap != null) {
            mMap.clear();
            mMap.animateCamera(location);
            mMap.addMarker(new MarkerOptions().position(coordinate).title(""));
        }
    }
}
