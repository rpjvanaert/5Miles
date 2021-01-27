package five.miles.failurism.ApiManagement;


public interface ApiListener {
    void onPhotoAvailable(ApiImage image);
    void onPhotoError(Error error);
}
