package com.backbase.cityfiltermap.di.modules;

import androidx.lifecycle.ViewModel;

import com.backbase.cityfiltermap.di.ViewModelKey;
import com.backbase.cityfiltermap.ui.search.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel model);

}
