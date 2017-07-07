
package com.anand.nobroker.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("imagesMap")
    @Expose
    private ImagesMap imagesMap;
    @SerializedName("displayPic")
    @Expose
    private Boolean displayPic;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;

    public ImagesMap getImagesMap() {
        return imagesMap;
    }

    public void setImagesMap(ImagesMap imagesMap) {
        this.imagesMap = imagesMap;
    }

    public Boolean getDisplayPic() {
        return displayPic;
    }

    public void setDisplayPic(Boolean displayPic) {
        this.displayPic = displayPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
