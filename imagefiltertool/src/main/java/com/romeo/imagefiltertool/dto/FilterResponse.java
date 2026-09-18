package com.romeo.imagefiltertool.dto;

public class FilterResponse {
    private String imageBase64;
    private String filterType;

    public FilterResponse(String imageBase64, String filterType) {
        this.imageBase64 = imageBase64;
        this.filterType = filterType;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
}
