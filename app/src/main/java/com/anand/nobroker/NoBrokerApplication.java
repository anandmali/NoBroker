package com.anand.nobroker;

import android.app.Application;

import com.anand.nobroker.dagger.components.AppComponent;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class NoBrokerApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        appComponent = DaggerAppComponent.builder()
//                .apiModule(new ApiModule())
//                .build();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
