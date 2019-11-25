package com.backbase.cityfiltermap.ui.di;

import com.backbase.cityfiltermap.data.CityStorage;
import com.backbase.cityfiltermap.di.annotations.ActivityScope;
import com.backbase.cityfiltermap.ui.search.SearchUseCase;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @ActivityScope
    SearchUseCase provideSearchUseCase(Executor executor, CityStorage cityStorage) {
        return new SearchUseCase(executor, cityStorage);
    }

}

