package com.backbase.cityfiltermap.ui.search;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.backbase.cityfiltermap.ui.models.SearchEntity;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private SearchUseCase searchUseCase;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    @Inject
    public SearchViewModel(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    public MutableLiveData<Boolean> init() {
        return searchUseCase.init();
    }

    public void filterByKey(String key) {
        searchInput.postValue(key);
    }

    public LiveData<List<SearchEntity>> getSearchLiveData() {
        return Transformations.switchMap(searchInput, new Function<String, LiveData<List<SearchEntity>>>() {
            @Override
            public LiveData<List<SearchEntity>> apply(String input) {
                return searchUseCase.getSearchLiveData(input);
            }
        });
    }
}
