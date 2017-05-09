package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dnksj on 2017-05-08.
 */

public class FollowRequest extends StringRequest {

    private static final String EMAIL_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=followRequest";
    private Map<String, String> params;

    public FollowRequest(int UserFollowed, int UserFollowing, Response.Listener<String> listener) {
        super(Method.POST, EMAIL_REQUEST_URL, listener,  null);
        params = new HashMap<>();
        params.put("UserFollowed", String.valueOf(UserFollowed));
        params.put("UserFollowing", String.valueOf(UserFollowing));
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
