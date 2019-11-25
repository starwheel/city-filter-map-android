package com.backbase.cityfiltermap;

import android.app.Application;

import com.backbase.cityfiltermap.di.AppComponent;
import com.backbase.cityfiltermap.di.DaggerAppComponent;

public class MyApplication extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.factory().create(this);
    }
}

