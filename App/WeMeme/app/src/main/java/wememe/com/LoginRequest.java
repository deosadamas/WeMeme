package wememe.com;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by info1 on 2017-03-20.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://bronze4life.ca/login.php";
    private Map<String, String> params;

    public LoginRequest(String pMemeurLog, String pMotDePasseLog, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("memeur", pMemeurLog);
        params.put("password", pMotDePasseLog);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
