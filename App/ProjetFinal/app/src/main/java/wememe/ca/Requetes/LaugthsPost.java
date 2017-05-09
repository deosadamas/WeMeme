package wememe.ca.Requetes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dnksj on 2017-05-08.
 */

public class LaugthsPost extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=profilLaughtsPost";
    private Map<String, String> params;

    public LaugthsPost(int id,Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id_user_post", String.valueOf(id));
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
