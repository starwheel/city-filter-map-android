package com.backbase.cityfiltermap.ui.di;

import com.backbase.cityfiltermap.di.annotations.ActivityScope;
import com.backbase.cityfiltermap.ui.search.SearchUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @ActivityScope
    SearchUseCase provideSearchUseCase() {
        return new SearchUseCase();
    }
}

