package wememe.com;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dnksj on 2017-04-04.
 */

public class FirstLoginRequest extends StringRequest {

    private static final String Verf_REQUEST_URL = "http://bronze4life.ca/mobile_app/index.php?prefix=json&p=verification";
    private Map<String, String> params;

    public FirstLoginRequest(String pnom, Response.Listener<String> listener) {
        super(Method.POST, Verf_REQUEST_URL, listener,  null);
        params = new HashMap<>();
        params.put("nom", pnom);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}