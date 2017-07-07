package com.anand.nobroker.dagger;

import com.anand.nobroker.dagger.components.AppComponent;
import com.anand.nobroker.dagger.components.DaggerAppComponent;
import com.anand.nobroker.dagger.module.ApiModule;

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
