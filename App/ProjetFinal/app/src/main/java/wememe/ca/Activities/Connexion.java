package wememe.ca.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import wememe.ca.R;
import wememe.ca.Requetes.LoginRequest;

public class Connexion extends AppCompatActivity {

    //COMENTAIRE https://material.io/icons/ IMPORTANT ICON !!!!!!!!!

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    Feed_max_id feed_max_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        feed_max_id = new Feed_max_id();
        RequestQueue queue = Volley.newRequestQueue(Connexion.this);
        queue.add(feed_max_id.stringRequest);

        //Initialisation des variables
        final EditText userConnexion = (EditText)findViewById(R.id.fieldUserConnexion);
        final EditText mdpConnexion = (EditText)findViewById(R.id.fieldMDPConnexion);
        final Button btnConnexion = (Button)findViewById(R.id.btnConnexion);
        final TextView creerCompteConnexion = (TextView)findViewById(R.id.txtCreerCompteConnexion);

        //SetFocusEvent pour le fieldUserConnexion
        userConnexion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                userConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_user_focus,0,0,0);
                mdpConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus,0,0,0);
            }
        });

        //SetFocusEvent pour le fieldMDPConnexion
        mdpConnexion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mdpConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_focus,0,0,0);
                userConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_user_no_focus,0,0,0);
            }
        });

        //SetOnClickEvent pour le txtCreerConnexion
        creerCompteConnexion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registrerIntent = new Intent(Connexion.this, Inscription.class);
                startActivity(registrerIntent);
            }
        });

        //SetOnClickEvent pour le btnConnexion
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userText = userConnexion.getText().toString();
                final String mdpText = mdpConnexion.getText().toString();

                //Response recois les reponses des fichiers php dans un tableau
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Varibales
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            Boolean validationNomNull = jsonResponse.getBoolean("validationNomNull");
                            Boolean validationEmailNull = jsonResponse.getBoolean("validationEmailNull");
                            Boolean validationEmail = null;
                            Boolean validationNom = null;
                            //
                            //Verifier si on utilise le username ou l'email
                            if(!validationEmailNull)
                            {
                                validationEmail = jsonResponse.getBoolean("validationEmail");
                            }
                            if(!validationNomNull)
                            {
                                validationNom = jsonResponse.getBoolean("validationNom");
                            }
                            //
                            //Verifier si la connexion est valide
                            if(success)
                            {
                                if(validationEmail != null)
                                {
                                    if(validationEmail)
                                    {
                                        Intent intent = new Intent(Connexion.this, VerificationCompte.class);
                                        intent.putExtra("user", userText);
                                        startActivity(intent);
                                    }
                                    else if(!validationEmail)
                                    {
                                        Intent intent3 = new Intent(Connexion.this, MainActivity.class);
                                        intent3.putExtra("maxID", String.valueOf(feed_max_id.id));
                                        startActivity(intent3);
                                    }
                                }
                                if(validationNom != null)
                                {
                                    if(validationNom)
                                    {
                                        Intent intent = new Intent(Connexion.this, VerificationCompte.class);
                                        intent.putExtra("user", userText);
                                        startActivity(intent);
                                    }
                                    else if(!validationNom)
                                    {
                                        Intent intent3 = new Intent(Connexion.this, MainActivity.class);
                                        intent3.putExtra("maxID", String.valueOf(feed_max_id.id));
                                        startActivity(intent3);
                                    }
                                }
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Connexion.this);
                                builder.setMessage("Le nom d'utilisateur ou le mot de passe entré ne correspond à aucun compte.")
                                        .setNegativeButton("Recommencer", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userText, mdpText, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Connexion.this);
                queue.add(loginRequest);
            }
        });
    }
}
