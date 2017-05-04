package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class InformationUserRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=informationUser";
    private Map<String, String> params;

    public InformationUserRequest(String user, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user", user);
        params.put("password", password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
