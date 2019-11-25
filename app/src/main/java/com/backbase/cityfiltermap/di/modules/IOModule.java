package com.backbase.cityfiltermap.di.modules;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IOModule {

    @Provides
    @Singleton
    Executor provideExecutor() {
        return Executors.newFixedThreadPool(8);
    }
}
