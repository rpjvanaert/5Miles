package com.example.braindeadism;

public interface ApiListener {
    void onPhotoAvailable(String url);
    void onPhotoError(Error error);
}
