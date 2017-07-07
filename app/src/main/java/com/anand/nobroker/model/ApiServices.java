package com.anand.nobroker.model;

import com.anand.nobroker.model.pojo.Properties;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiServices {
    @GET("filter/region/ChIJLfyY2E4UrjsRVq4AjI7zgRY/")
    Call<Properties> getProperties(@QueryMap(encoded = true)Map<String, String> queries);

}
