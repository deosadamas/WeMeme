package wememe.com;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText edtxtMemeurLog = (EditText)findViewById(R.id.txtEmailLogin);
        final EditText edtxtMoteDePasseLog = (EditText)findViewById(R.id.txtMDPLogin);
        final Button btnLogin = (Button)findViewById(R.id.btnLogin);
        final TextView inscriptionLink = (TextView)findViewById(R.id.txtViewInscription);


        //COMENTAIRE https://material.io/icons/ IMPORTANT ICON !!!!!!!!!
        edtxtMemeurLog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtMemeurLog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.username_over,0,0,0);
                edtxtMoteDePasseLog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
            }
        });

        edtxtMoteDePasseLog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtMoteDePasseLog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_over,0,0,0);
                edtxtMemeurLog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.username,0,0,0);
            }
        });

        inscriptionLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registrerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registrerIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memeurLog = edtxtMemeurLog.getText().toString();
                final String motDePasseLog = edtxtMoteDePasseLog.getText().toString();

                // Response est qu'ont recoit les données du serveur
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String memeur = jsonResponse.getString("memeur");
                                String date = jsonResponse.getString("date");

                                Intent intent = new Intent(LoginActivity.this, User_Area_Activity.class);
                                intent.putExtra("memeur", memeur);
                                intent.putExtra("date", date);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Le nom d'Utilisateur ou le mot de passe entré ne correspond à aucun compte.")
                                        .setNegativeButton("Recommencer", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(memeurLog, motDePasseLog, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
