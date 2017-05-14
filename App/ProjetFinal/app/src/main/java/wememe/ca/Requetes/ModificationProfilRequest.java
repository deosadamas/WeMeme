package wememe.ca.Requetes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dnksj on 2017-05-13.
 */

public class ModificationProfilRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=newpassword";
    private Map<String, String> params;

    public ModificationProfilRequest(int id,String password, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("password", password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
