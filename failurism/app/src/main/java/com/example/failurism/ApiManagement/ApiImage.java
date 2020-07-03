package com.example.failurism.ApiManagement;

import java.io.Serializable;

public class ApiImage implements Serializable {
    private String url;

    public ApiImage(String url){
        this.url = url;
    }

    public String getUrl(){ return this.url; }
}