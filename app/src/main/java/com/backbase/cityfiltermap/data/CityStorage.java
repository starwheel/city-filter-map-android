package com.backbase.cityfiltermap.data;

import com.backbase.cityfiltermap.domain.model.SearchEntity;

import java.util.List;
import java.util.TreeMap;

public interface CityStorage {

    void initCache();
    TreeMap<Character, List<SearchEntity>> getCache();
    void setCache(TreeMap<Character, List<SearchEntity>> cache);
}
