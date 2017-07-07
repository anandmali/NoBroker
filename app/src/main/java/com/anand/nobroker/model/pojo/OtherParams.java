
package com.anand.nobroker.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherParams {

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("topPropertyId")
    @Expose
    private Object topPropertyId;
    @SerializedName("total_count")
    @Expose
    private Long totalCount;
    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("region_id")
    @Expose
    private String regionId;
    @SerializedName("searchToken")
    @Expose
    private Object searchToken;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getTopPropertyId() {
        return topPropertyId;
    }

    public void setTopPropertyId(Object topPropertyId) {
        this.topPropertyId = topPropertyId;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Object getSearchToken() {
        return searchToken;
    }

    public void setSearchToken(Object searchToken) {
        this.searchToken = searchToken;
    }

}
