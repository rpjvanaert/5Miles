package five.miles.failurism.ApiManagement;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class PixabayApiManager {
    private static final String LOGTAG = CatApiManager.class.getName();

    private Context appContext;
    private RequestQueue queue;
    private ApiListener listener;

    public PixabayApiManager(Context context, ApiListener listener){
        this.appContext = context;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getImages(){
        Random rng = new Random();
        String pageNumber = "" + (rng.nextInt(10) + 1);
        String order = "popular";
        String editorsChoice = "" + rng.nextBoolean();
        if (rng.nextBoolean()){
            order = "latest";
        }
        final String url = "https://pixabay.com/api/?key=2766011-3e8868270ec4b407ae9004d5c&safesearch=true&per_page=50&order=" + order + "&editors_choice=" + editorsChoice + "&page=" + pageNumber;

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOGTAG, "Volley response: " + response.toString());
                        try {


                            JSONArray hits = response.getJSONArray("hits");

                            for (int i = 0; i < hits.length(); ++i){
                                JSONObject imageJSONObject = hits.getJSONObject(i);
                                String url = imageJSONObject.getString("webformatURL");
                                String largeUrl = imageJSONObject.getString("largeImageURL");

                                Log.d(LOGTAG, "Adding url: " + url);
                                listener.onPhotoAvailable(ApiImage.ApiImage(url, largeUrl, Api.PIXABAY));
                            }
                        } catch (JSONException e) {
                            // On JSONException, create log message.
                            Log.e(LOGTAG, "Error while parsing JSON data: " + e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * onErrorResponse
                     * Creates log message on error
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOGTAG, error.getLocalizedMessage());
                        listener.onPhotoError(new Error(error.getLocalizedMessage()));
                    }
                }
        );
        // Request done, add it in queue.
        this.queue.add(request);
    }
}