package com.anand.nobroker.model.pojo;

import java.util.HashMap;

public class QueryValues {

    //Keys
    private static final String LAT_LANG = "lat_lng";
    private static final String RENT = "rent";
    private static final String TRAVEL_TIME = "travelTime";
    private static final String PAGE_NO = "pageNo";
    private static final String TYPE = "type";
    private static final String BUILDING_TYPE = "buildingType";
    private static final String FURNISHING = "furnishing";

    //Values common
    private String latlang = "12.9279232,77.6271078";
    private String rent = "0,500000";
    private Integer timeTraval = 30;
    private Integer pageNo = 1;

    //values filter
    private String type;
    private String buildingType;
    private String furnishing;

    public QueryValues(Integer pageNo, String type, String buildingType, String furnishing) {
        this.pageNo = pageNo;
        this.type = type;
        this.buildingType = buildingType;
        this.furnishing = furnishing;
    }

    public HashMap<String, Object> getFilterQueryMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(LAT_LANG, latlang);
        map.put(RENT, rent);
        map.put(TRAVEL_TIME, timeTraval);
        map.put(PAGE_NO, pageNo);
        if (type != null)
            map.put(TYPE, type);
        if (buildingType != null)
            map.put(BUILDING_TYPE, buildingType);
        if (furnishing != null)
            map.put(FURNISHING, furnishing);
        return map;
    }
}
