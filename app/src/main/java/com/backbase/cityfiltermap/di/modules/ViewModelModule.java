package com.backbase.cityfiltermap.di.modules;

import androidx.lifecycle.ViewModel;

import com.backbase.cityfiltermap.di.ViewModelKey;
import com.backbase.cityfiltermap.ui.search.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel model);

}
