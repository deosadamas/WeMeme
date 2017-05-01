package wememe.ca.Requetes;

/**
 * Created by dnksj on 2017-04-30.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProfilRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=profil";
    private Map<String, String> params;

    public ProfilRequest(int pMeneurSign,Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", String.valueOf(pMeneurSign));
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
