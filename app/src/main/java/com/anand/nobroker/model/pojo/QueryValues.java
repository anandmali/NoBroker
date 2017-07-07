package com.anand.nobroker.model.pojo;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

public class QueryValues {

    //Keys
    public static final String LAT_LANG = "lat_lng";
    private static final String RENT = "rent";
    private static final String TRAVEL_TIME = "travelTime";
    private static final String PAGE_NO = "pageNo";
    private static final String TYPE = "type";
    private static final String BUILDING_TYPE = "buildingType";
    private static final String FURNSHING = "furnishing";

    //Values common
    private String latlang = "12.9279232,77.6271078";
    private String rent = "0,500000";
    private String timeTraval = "30";
    private String pageNo = "1";

    //values filter
    private String type;
    private String buildingType;
    private String furnishing;

    public QueryValues(String pageNo, String type, String buildingType, String furnishing) {
        this.pageNo = pageNo;
        this.type = type;
        this.buildingType = buildingType;
        this.furnishing = furnishing;
    }

    public QueryValues() {
        latlang = "12.9279232,77.6271078";
        rent = "0,500000";
        timeTraval = "30";
        pageNo = "1";
    }

    public HashMap<String, String> getCommonQueryMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(LAT_LANG, latlang);
        map.put(RENT, rent);
        map.put(TRAVEL_TIME, timeTraval);
        map.put(PAGE_NO, pageNo);
        return map;
    }

    public HashMap<String, String> getFilterQueryMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(LAT_LANG, latlang);
        map.put(RENT, rent);
        map.put(TRAVEL_TIME, timeTraval);
        map.put(PAGE_NO, pageNo);
        map.put(TYPE, type);
        map.put(BUILDING_TYPE, buildingType);
        map.put(FURNSHING, furnishing);
        return map;
    }

}
