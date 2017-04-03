package wememe.com;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-03-20.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://bronze4life.ca/mobile_app/index.php?prefix=json&p=register";
    private Map<String, String> params;

    public RegisterRequest(String pEmailSign, String pMeneurSign, String pMotDePasseSign, String pDateSign, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", pEmailSign);
        params.put("nom", pMeneurSign);
        params.put("password", pMotDePasseSign);
        params.put("date", pDateSign);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}



