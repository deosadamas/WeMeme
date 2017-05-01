package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by info1 on 2017-04-27.
 */

public class FollowRequest {

    public static final String user_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=follow";
    public int followed;
    public int follower;
    public List<DataFollow> dataFollows;

    public StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, user_REQUEST_URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try{
                JSONArray array = new JSONArray(response);
                JSONObject object = array.getJSONObject(0);
                followed = object.getInt("UserFollowed");
                follower = object.getInt("UserFollowing");
                for (int i = 0; i <= array.length(); i++){
                    JSONObject object2 = array.getJSONObject(1);
                    DataFollow dataFollow = new DataFollow(object2.getInt("UserFollowed"), object2.getInt("UserFollowing"), object2.getString("DateFollow"));
                    dataFollows.add(dataFollow);
                }
            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }
    },null);
}

