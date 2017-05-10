package wememe.ca.Requetes;

/**
 * Created by info1 on 2017-05-10.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by info1 on 2017-05-10.
 */

public class ReportRequest extends StringRequest {


    private static final String REGISTER_REQUEST_URL = "http://wememe.ca/mobile_app/index.php?prefix=json&p=updateReport";
    private Map<String, String> params;

    public ReportRequest(String pID, String pR_1, String pR_2, String pR_3, String pR_4, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", pID);
        params.put("R_1", pR_1);
        params.put("R_2", pR_2);
        params.put("R_3", pR_3);
        params.put("R_4", pR_4);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


