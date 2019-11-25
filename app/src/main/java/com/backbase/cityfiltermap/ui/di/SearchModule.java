package com.backbase.cityfiltermap.ui.di;

import android.content.Context;

import com.backbase.cityfiltermap.di.annotations.ActivityScope;
import com.backbase.cityfiltermap.ui.search.SearchUseCase;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @ActivityScope
    SearchUseCase provideSearchUseCase(Context context, Executor executor) {
        return new SearchUseCase(context, executor);
    }

}

