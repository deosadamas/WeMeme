package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-04-06.
 */

public class CodeRequest extends StringRequest {

    private static final String Code_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=codewememe";
    private Map<String, String> params;

    public CodeRequest(String puser, int pcode,Response.Listener<String> listener) {
        super(Method.POST, Code_REQUEST_URL, listener,  null);
        params = new HashMap<>();
        params.put("user", puser);
        params.put("code", String.valueOf(pcode));
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
