package com.backbase.cityfiltermap.di;

import android.content.Context;

import com.backbase.cityfiltermap.di.modules.IOModule;
import com.backbase.cityfiltermap.di.modules.StorageModule;
import com.backbase.cityfiltermap.di.modules.ViewModelModule;
import com.backbase.cityfiltermap.ui.di.SearchComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        IOModule.class,
        StorageModule.class,
        ViewModelModule.class,
})
public interface AppComponent {

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance Context applicationContext);
    }

    SearchComponent.Factory searchComponent();
}

