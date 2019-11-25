package com.backbase.cityfiltermap.ui.search;


import androidx.lifecycle.MutableLiveData;

import com.backbase.cityfiltermap.ui.models.SearchEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SearchUseCase {

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    private MutableLiveData<List<SearchEntity>> _searchLiveData;

    @Inject
    public SearchUseCase() {

    }

    MutableLiveData<List<SearchEntity>> getSearchLiveData(String input) {
        _searchLiveData = new MutableLiveData<>();

        execute(new Runnable() {
            @Override
            public void run() {
                List<SearchEntity> searchEntities = new ArrayList<SearchEntity>();
                searchEntities.add(new SearchEntity());
                searchEntities.add(new SearchEntity());
                searchEntities.add(new SearchEntity());
                searchEntities.add(new SearchEntity());

                _searchLiveData.postValue(searchEntities);
            }
        });

        return _searchLiveData;
    }

}
