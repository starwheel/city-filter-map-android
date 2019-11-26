package com.backbase.cityfiltermap.data;

import android.content.Context;

import androidx.annotation.VisibleForTesting;

import com.backbase.cityfiltermap.domain.model.SearchEntity;
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

public class LocalCityStorage implements CityStorage {
    private TreeMap<Character, List<SearchEntity>> cache;
    private boolean initialized = false;
    private final Context application;

    public LocalCityStorage(Context application) {
        this.application = application;
        cache = new TreeMap<>();
    }

    public void initCache() {
        if (initialized) return;
        String json = null;
        try {
            InputStream is = application.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            Type type = new TypeToken<List<SearchEntity>>() {}.getType();

            Gson gson = new GsonBuilder().create();// TODO inject
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
                    sortList(cache.get(character));
                }
            }
            initialized = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static void sortList(List<SearchEntity> list) {
        Collections.sort(list, new Comparator<SearchEntity>() {
            @Override
            public int compare(SearchEntity searchEntity, SearchEntity t1) {
                return searchEntity.getName().compareTo(t1.getName());
            }
        });
    }

    public TreeMap<Character, List<SearchEntity>> getCache() {
        return cache;
    }

    @VisibleForTesting
    public void setCache(TreeMap<Character, List<SearchEntity>> cache) {
        this.cache = cache;
    }
}
