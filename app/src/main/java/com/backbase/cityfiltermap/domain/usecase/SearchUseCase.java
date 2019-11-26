package com.backbase.cityfiltermap.domain.usecase;

import androidx.lifecycle.MutableLiveData;

import com.backbase.cityfiltermap.data.CityStorage;
import com.backbase.cityfiltermap.domain.model.SearchEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class SearchUseCase {

    private final Executor executorService;
    private final CityStorage cityStorage;
    private MutableLiveData<List<SearchEntity>> _searchLiveData;
    private final MutableLiveData<Boolean> initLiveData = new MutableLiveData<>();

    @Inject
    public SearchUseCase(Executor executorService, CityStorage cityStorage) {
        this.executorService = executorService;
        this.cityStorage = cityStorage;
    }

    public MutableLiveData<List<SearchEntity>> getSearchLiveData(final String input) {
        _searchLiveData = new MutableLiveData<>();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<SearchEntity> searchEntities = new ArrayList<>();
                if (input != null && input.length() > 0) {
                    char firstChar = Character.toLowerCase(input.charAt(0));
                    List<SearchEntity> list = cityStorage.getCache().get(firstChar);
                    if (list != null) {
                        String lowerCase = input.toLowerCase();
                        for (SearchEntity searchEntity : list) {
                            if (searchEntity.getName().toLowerCase().startsWith(lowerCase)) {
                                searchEntities.add(searchEntity);
                            }
                        }
                    }
                }

                _searchLiveData.postValue(searchEntities);
            }
        });

        return _searchLiveData;
    }

    public void init() {
        initLiveData.setValue(false);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cityStorage.initCache();
                initLiveData.postValue(true);
            }
        });
    }

    public MutableLiveData<Boolean> getInitLiveData() {
        return initLiveData;
    }
}
