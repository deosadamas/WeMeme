package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-05-04.
 */

public class LikeRequest extends StringRequest {

    private static final String Code_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=like";
    private Map<String, String> params;

    public LikeRequest(String puser, int pcode, Response.Listener<String> listener) {
        super(Method.POST, Code_REQUEST_URL, listener,  null);
        params = new HashMap<>();
        params.put("userid", puser);
        params.put("memeid", String.valueOf(pcode));
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}