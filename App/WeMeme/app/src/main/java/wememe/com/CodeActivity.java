package wememe.com;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        final Bundle data = getIntent().getExtras();
        Button btnSuivant = (Button)findViewById(R.id.btnSuivant);
        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CodeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button btne = (Button)findViewById(R.id.btnen);
        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj = new JSONObject();
                try {
                    String email = data.get("email").toString();
                    String memeur = data.get("nom").toString();
                    obj.put("email", email);
                    obj.put("nom", memeur);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                        "http://bronze4life.ca/mobile_app/index.php?prefix=json&p=emailsend", obj, new
                        Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject jsonResults) {
                                AlertDialog.Builder d = new AlertDialog.Builder(CodeActivity.this);
                                d.setMessage(jsonResults.toString())
                                        .setNegativeButton("Recommencer", null)
                                        .create()
                                        .show();
                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                );

                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);
                mRequestQueue.add(request);
            }
        });
    }
}
