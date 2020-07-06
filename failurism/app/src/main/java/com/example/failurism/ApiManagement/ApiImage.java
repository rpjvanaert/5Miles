package com.example.failurism.ApiManagement;

import java.io.Serializable;

public class ApiImage implements Serializable {
    private String url;
    private String largeURL;
    private Api api;

    public ApiImage(String url, Api api){
        this.url = url;
        this.largeURL = null;
        this.api = api;
    }

    public void setLargeURL(String largeURL){
        this.largeURL = largeURL;
    }

    public String getUrl(){
        System.out.println(this.largeURL == null);
        if (this.largeURL == null){
            return this.url;
        } else {
            return this.largeURL;
        }
    }

    public Api getApi(){ return this.api; }

    public static ApiImage ApiImage(String url, String largeURL, Api api){
        ApiImage apiImage = new ApiImage(url, api);
        apiImage.setLargeURL(largeURL);
        return apiImage;
    }
}