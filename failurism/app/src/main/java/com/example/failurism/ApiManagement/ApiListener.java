package com.example.failurism.ApiManagement;


public interface ApiListener {
    void onPhotoAvailable(ApiImage image);
    void onPhotoError(Error error);
}
