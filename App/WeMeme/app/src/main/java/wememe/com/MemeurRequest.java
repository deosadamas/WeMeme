package wememe.com;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dnksj on 2017-03-23.
 */

public class MemeurRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://consummative-litres.000webhostapp.com/memeurVerification.php";
    private Map<String, String> params;

    public MemeurRequest(String pmemeur, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("memeur", pmemeur);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}