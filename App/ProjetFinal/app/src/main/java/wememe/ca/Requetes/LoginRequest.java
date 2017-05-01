package wememe.ca.Requetes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-04-06.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=login";
    private Map<String, String> params;

    public LoginRequest(String pMemeurLog, String pMotDePasseLog, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user", pMemeurLog);
        params.put("password", pMotDePasseLog);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}