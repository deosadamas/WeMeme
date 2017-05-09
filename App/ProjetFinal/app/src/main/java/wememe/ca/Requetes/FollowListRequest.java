package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by info1 on 2017-04-27.
 */

public class FollowListRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=follow";
    public FollowListRequest(Response.Listener<String> listener) {
        super(Method.GET, REGISTER_REQUEST_URL, listener, null);
    }

}

