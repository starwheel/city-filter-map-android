package com.backbase.cityfiltermap.ui.search;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.backbase.cityfiltermap.ui.models.SearchEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class SearchUseCase {

    private final Context application;
    private final Executor executorService;
    private MutableLiveData<List<SearchEntity>> _searchLiveData;
    private TreeMap<Character, List<SearchEntity>> cache;

    @Inject
    public SearchUseCase(Context application, Executor executorService) {
        this.application = application;
        this.executorService = executorService;
        cache = new TreeMap<>();
    }

    MutableLiveData<List<SearchEntity>> getSearchLiveData(final String input) {
        _searchLiveData = new MutableLiveData<>();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<SearchEntity> searchEntities = new ArrayList<>();
                if (!TextUtils.isEmpty(input)) {
                    char firstChar = Character.toLowerCase(input.charAt(0));
                    List<SearchEntity> list = cache.get(firstChar);
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
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    InputStream is = application.getAssets().open("cities.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, "UTF-8");

                    Type type = new TypeToken<List<SearchEntity>>() {}.getType();

                    Gson gson = new GsonBuilder().create();
                    List<SearchEntity> list = gson.fromJson(json, type);

                    for (SearchEntity searchEntity : list) {
                        char firstChar = Character.toLowerCase(searchEntity.getName().charAt(0));
                        List<SearchEntity> sortedList = cache.get(firstChar);

                        if (sortedList == null) {
                            sortedList = new ArrayList<>();
                        }
                        sortedList.add(searchEntity);
                        cache.put(firstChar, sortedList);
                    }

                    for (Character character : cache.keySet()) {
                        if (cache.get(character) != null) {
                            Collections.sort(cache.get(character), new Comparator<SearchEntity>() {
                                @Override
                                public int compare(SearchEntity searchEntity, SearchEntity t1) {
                                    return searchEntity.getName().compareTo(t1.getName());
                                }
                            });
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}
