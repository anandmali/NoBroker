package com.anand.nobroker.dagger.components;

import com.anand.nobroker.dagger.module.ApiModule;
import com.anand.nobroker.view.activities.MainActivity;
import com.anand.nobroker.view.fragments.FilterFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApiModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
}
