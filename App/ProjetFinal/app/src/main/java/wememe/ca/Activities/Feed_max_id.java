package wememe.ca.Activities;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by info1 on 2017-04-27.
 */

public class Feed_max_id {
    private static final String Id_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=feed_id";
    public int id = 0;

    public StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Id_REQUEST_URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONArray array = new JSONArray(response);
                JSONObject object = array.getJSONObject(0);
                id = object.getInt("MAX(id)")+1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    },null);
}
