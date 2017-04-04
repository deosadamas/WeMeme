package wememe.com;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-04-04.
 */

public class CodeRequest extends StringRequest {

    private static final String Code_REQUEST_URL = "http://bronze4life.ca/mobile_app/index.php?prefix=json&p=code";
    private Map<String, String> params;

    public CodeRequest(String pemail, int pcode,Response.Listener<String> listener) {
        super(Method.POST, Code_REQUEST_URL, listener,  null);
        params = new HashMap<>();
        params.put("nom", pemail);
        params.put("code", String.valueOf(pcode));
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}