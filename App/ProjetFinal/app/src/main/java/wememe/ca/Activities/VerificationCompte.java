package wememe.ca.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import wememe.ca.Class.SaveSharedPreference;
import wememe.ca.R;
import wememe.ca.Requetes.CodeRequest;

public class VerificationCompte extends AppCompatActivity {

    EditText codeText;
    View viewSnackbar;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_compte);

        codeText = (EditText) findViewById(R.id.fieldCodeVerification);

        Button btnVerification = (Button)findViewById(R.id.btnVerification);

        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSnackbar = view;
                code = Integer.parseInt(codeText.getText().toString());
                RequestQueue queue = Volley.newRequestQueue(VerificationCompte.this);
                CodeRequest codeRequest = new CodeRequest(SaveSharedPreference.getUserName(getApplication()), code, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                Snackbar snackbar;
                                snackbar = Snackbar.make(viewSnackbar, "Le code est valider", Snackbar.LENGTH_LONG);
                                viewSnackbar = snackbar.getView();
                                viewSnackbar.setBackgroundColor(Color.parseColor("#371e6d"));
                                snackbar.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(VerificationCompte.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }, 2000);
                            }
                            else
                            {
                                Snackbar snackbar;
                                snackbar = Snackbar.make(viewSnackbar, "Erreur : Code invalide", Snackbar.LENGTH_LONG);
                                viewSnackbar = snackbar.getView();
                                viewSnackbar.setBackgroundColor(Color.parseColor("#371e6d"));
                                snackbar.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                queue.add(codeRequest);
            }
        });
    }
}
