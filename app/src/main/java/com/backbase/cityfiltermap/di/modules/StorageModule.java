package com.backbase.cityfiltermap.di.modules;

import android.content.Context;

import com.backbase.cityfiltermap.data.CityStorage;
import com.backbase.cityfiltermap.data.LocalCityStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    @Provides
    @Singleton
    CityStorage provideCityStorage(Context application) {
        return new LocalCityStorage(application);
    }

}
