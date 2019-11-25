package com.backbase.cityfiltermap.ui.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backbase.cityfiltermap.R;
import com.backbase.cityfiltermap.di.ViewModelFactory;
import com.backbase.cityfiltermap.ui.adapters.SearchListAdapter;
import com.backbase.cityfiltermap.ui.adapters.SearchListCallback;
import com.backbase.cityfiltermap.ui.models.SearchEntity;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

public class CityListFragment extends Fragment implements SearchListCallback {

    private static final String TAG = "CityListFragment";
    private EditText editText;
    private RecyclerView rvList;
    private SearchListAdapter listAdapter;

    private SearchViewModel model;

    @Inject
    ViewModelFactory factory;

    public CityListFragment() {
    }

    public static CityListFragment newInstance() {
        Bundle args = new Bundle();
        CityListFragment fragment = new CityListFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_city_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.editText);
        rvList = view.findViewById(R.id.rvList);
        model = ViewModelProviders.of(getActivity(), factory).get(SearchViewModel.class);
        model.getSearchLiveData().observe(this, new Observer<List<SearchEntity>>() {
            @Override
            public void onChanged(List<SearchEntity> searchEntities) {
                listAdapter.submitList(null);
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
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));


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

        model.getInitLiveData().observe(this, new Observer<Boolean>() {
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

    @Override
    public void onSearchEntityClicked(SearchEntity searchEntity) {
        if (searchEntity.getCoord() != null) {
            LatLng coordinate = new LatLng(searchEntity.getCoord().getLat(),
                    searchEntity.getCoord().getLon());
            model.moveToEntityDetails(coordinate);
            if (getActivity() != null) {
                ((MainActivity) getActivity()).moveTo(coordinate);
            }

            hideKeyboard(editText);
        }
    }

    private void hideKeyboard(EditText et) {
        InputMethodManager imm =
                (InputMethodManager) et.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }
}
