package com.example.failurism.ApiManagement;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HumanApiManager {
    private static final String LOGTAG = HumanApiManager.class.getName();

    private Context appContext;
    private RequestQueue queue;
    private ApiListener listener;

    public HumanApiManager(Context context, ApiListener listener){
        this.appContext = context;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getImages(){
        final String url = "https://api.generated.photos/api/v1/faces?per_page=1&gender=female&order_by=random";

        final JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(LOGTAG, "Volley response: " + response.toString());
                        try {

                            //For each image, reading the object and getting image url and other information
                            for (int i = 0; i < response.length(); ++i) {
                                JSONObject catObject = response.getJSONObject(i);
                                String url = catObject.getString("url");

                                Log.d(LOGTAG, "Adding url: " + url);
                                listener.onPhotoAvailable(new ApiImage(url));
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
