package com.example.azbowtest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo implements Serializable {
    @SerializedName("id")

    private String id;
    @SerializedName("width")

    private Integer width;
    @SerializedName("height")

    private Integer height;
    @SerializedName("urls")

    private Urls urls;
    public Urls getUrls() {
        return urls;
    }
    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
