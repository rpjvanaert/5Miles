package com.example.failurism.ApiManagement;

import java.io.Serializable;

public class ApiImage implements Serializable {
    private String url;
    private Api api;

    public ApiImage(String url, Api api){
        this.url = url;
        this.api = api;
    }

    public String getUrl(){ return this.url; }

    public Api getApi(){ return this.api; }
}