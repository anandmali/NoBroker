package com.anand.nobroker.dagger.module;

import com.anand.nobroker.model.pojo.NoBrokerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    NoBrokerApi provideNoBrokerApi() {
        return new NoBrokerApi();
    }
}
