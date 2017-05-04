package wememe.ca.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wememe.ca.R;
import wememe.ca.Requetes.FollowRequest;
import wememe.ca.Requetes.InformationUserRequest;
import wememe.ca.Requetes.LoginRequest;
import wememe.ca.Requetes.ProfilRequest;

public class Connexion extends AppCompatActivity {

    //COMENTAIRE https://material.io/icons/ IMPORTANT ICON !!!!!!!!!

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    Feed_max_id feed_max_id;
    private String userConnexion_information, mdpConnexion_information;
    EditText userConnexion;
    EditText mdpConnexion;
    String userText, mdpText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        userConnexion = (EditText)findViewById(R.id.fieldUserConnexion);
        mdpConnexion = (EditText)findViewById(R.id.fieldMDPConnexion);
        if(SaveSharedPreference.getUserName(Connexion.this).length() == 0)
        {
            //Initialisation des variables
            userConnexion.getBackground().setColorFilter(getResources().getColor(R.color.colorTexte), PorterDuff.Mode.SRC_IN);
            mdpConnexion.getBackground().setColorFilter(getResources().getColor(R.color.colorTexte), PorterDuff.Mode.SRC_IN);
            final Button btnConnexion = (Button)findViewById(R.id.btnConnexion);
            final TextView creerCompteConnexion = (TextView)findViewById(R.id.txtCreerCompteConnexion);
            userConnexion_information = userConnexion.getText().toString();
            mdpConnexion_information = mdpConnexion.getText().toString();

            //SetFocusEvent pour le fieldUserConnexion
            userConnexion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    userConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_user_focus,0,0,0);
                    userConnexion.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    mdpConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus,0,0,0);
                    mdpConnexion.getBackground().setColorFilter(getResources().getColor(R.color.colorTexte), PorterDuff.Mode.SRC_IN);
                }
            });

            //SetFocusEvent pour le fieldMDPConnexion
            mdpConnexion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    mdpConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_focus,0,0,0);
                    mdpConnexion.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    userConnexion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_user_no_focus,0,0,0);
                    userConnexion.getBackground().setColorFilter(getResources().getColor(R.color.colorTexte), PorterDuff.Mode.SRC_IN);
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
                    userText = userConnexion.getText().toString();
                    mdpText = mdpConnexion.getText().toString();

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
                                            intent3.putExtra("user", userText);
                                            intent3.putExtra("password", mdpText);
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
                                            SaveSharedPreference.setUserName(getApplication(), userText);
                                            Intent intent3 = new Intent(Connexion.this, MainActivity.class);
                                            intent3.putExtra("user", userText);
                                            intent3.putExtra("password", mdpText);
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
        else
        {
                Intent intent = new Intent(Connexion.this, MainActivity.class);
                userText = userConnexion.getText().toString();
                mdpText = mdpConnexion.getText().toString();
                intent.putExtra("user", userText);
                intent.putExtra("password", mdpText);
                startActivity(intent);
        }
    }
}
