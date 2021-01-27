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

public class DogApiManager {
    private static final String LOGTAG = CatApiManager.class.getName();

    private Context appContext;
    private RequestQueue queue;
    private ApiListener listener;

    public DogApiManager(Context context, ApiListener listener){
        this.appContext = context;
        this.listener = listener;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getImages(){
        final String url = "https://dog.ceo/api/breeds/image/random/50";

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOGTAG, "Volley response: " + response.toString());
                        Log.d(LOGTAG, "Response length: " + response.length());
                        try {

                            JSONArray urlArray = response.getJSONArray("message");
                            //For each image, reading the object and getting image url and other information
                            for (int i = 0; i < urlArray.length(); ++i) {
                                String url = urlArray.getString(i);

                                Log.d(LOGTAG, "Adding url: " + url);
                                listener.onPhotoAvailable(new ApiImage(url, Api.DOG));
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
