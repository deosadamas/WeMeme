package wememe.com;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
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

import java.util.HashMap;
import java.util.Map;

public class CodeActivity extends AppCompatActivity {
    EditText edtxtCode;
    View viewsnackbar;
    int code;
    String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        Bundle data = getIntent().getExtras();
        nom = data.get("nom").toString();
        edtxtCode = (EditText) findViewById(R.id.edtxtCode);
    }

    public void Suivant(View view)
    {
        viewsnackbar = view;
        code = Integer.parseInt(edtxtCode.getText().toString());
        RequestQueue queue = Volley.newRequestQueue(CodeActivity.this);
        CodeRequest codeRequest = new CodeRequest("", code, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success)
                    {
                        Snackbar snackbar;
                        snackbar = Snackbar.make(viewsnackbar, "Le code est valider", Snackbar.LENGTH_LONG);
                        viewsnackbar = snackbar.getView();
                        viewsnackbar.setBackgroundColor(Color.parseColor("#371e6d"));
                        snackbar.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(CodeActivity.this, User_Area_Activity.class);
                                startActivity(intent);
                            }
                        }, 2000);
                    }
                    else
                    {
                        Snackbar snackbar;
                        snackbar = Snackbar.make(viewsnackbar, "Erreur : Code invalide ", Snackbar.LENGTH_LONG);
                        viewsnackbar = snackbar.getView();
                        viewsnackbar.setBackgroundColor(Color.parseColor("#371e6d"));
                        snackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        queue.add(codeRequest);
    }
}
