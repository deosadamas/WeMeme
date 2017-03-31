package wememe.com;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-03-31.
 */

public class EmailRequest extends StringRequest {

    private static final String EMAIL_REQUEST_URL = "http://bronze4life.ca/mobile_app/index.php?prefix=json&p=email";
    private Map<String, String> params;

    public EmailRequest(String pEmailSign, Response.Listener<String> listener) {
        super(Method.POST, EMAIL_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", pEmailSign);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}