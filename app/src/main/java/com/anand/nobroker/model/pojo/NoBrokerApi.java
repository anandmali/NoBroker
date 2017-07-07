package com.anand.nobroker.model.pojo;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public class NoBrokerApi {

    private HashMap<String, String> queryData;

    public interface ApiServices {
        @GET("filter/region/ChIJLfyY2E4UrjsRVq4AjI7zgRY/")
        Observable<Properties> getProperties(@QueryMap(encoded = true) Map<String, String> queries);
    }

//    private Observable<Properties> postsObservable = new Retrofit.Builder()
//            .baseUrl("http://www.nobroker.in/api/v1/property/")
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build().create(ApiServices.class).getProperties(queryData).cache();

    public Observable<Properties> getPropertiesObservable(HashMap<String, String> queryValues) {
        return new Retrofit.Builder()
                .baseUrl("http://www.nobroker.in/api/v1/property/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class).getProperties(queryValues).cache();
    }

    public HashMap<String, String> getQueryData() {
        return queryData;
    }

    public void setQueryData(HashMap<String, String> queryData) {
        this.queryData = queryData;
    }

}
