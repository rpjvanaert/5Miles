package five.miles.failurism.QuoteManagement;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuoteManager {
    private static final String LOGTAG = QuoteManager.class.getName();

    private Context appContext;
    private RequestQueue queue;
    private QuoteListener listener;

    public QuoteManager(Context context, QuoteListener listener){
        this.listener = listener;
        this.appContext = context;
        this.queue = Volley.newRequestQueue(this.appContext);
    }

    public void getQuotes(){
        final String path = "https://www.dropbox.com/s/xq8svbjmfiauc2e/Quotes.json?dl=0";

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                path,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("quotes");

                            //For each image, reading the object and getting image url and other information
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                String quote = jsonArray.getString(i);

                                listener.onQuoteAvailable(quote);
                            }
                        } catch (JSONException e) {
                            // On JSONException, create log message.
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
                        listener.onQuoteError(new Error(error.getLocalizedMessage()));
                    }
                }
        );
        this.queue.add(request);
    }
}
