package wememe.ca.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
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

import wememe.ca.Class.SaveSharedPreference;
import wememe.ca.R;
import wememe.ca.Requetes.LoginRequest;

public class Connexion extends AppCompatActivity {

    //COMENTAIRE https://material.io/icons/ IMPORTANT ICON !!!!!!!!!

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private String userConnexion_information, mdpConnexion_information;
    EditText userConnexion;
    EditText mdpConnexion;
    String userText, mdpText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //On Get le texte dans les EditText
        userConnexion = (EditText)findViewById(R.id.fieldUserConnexion); //Le nom ou email de l'utilisateur
        mdpConnexion = (EditText)findViewById(R.id.fieldMDPConnexion); // Le mot de passe de l'utilisateur

        //Une condition qui vérifie si l'utilisateur est déja connecter
        if(SaveSharedPreference.getUserName(Connexion.this).length() == 0 && SaveSharedPreference.getUserPassword(Connexion.this).length()== 0)
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
                                //Le validationNomNull et validationEmailNull sont nommer Null a la fin car
                                // lors de la connection on donne 2 choix a l'utilisateur soit il ce connecte avec
                                // son nom ou par son email donc si c'est par son nom le email va etre null donc on
                                // s'assure que le validationEmail reste null
                                Boolean validationNomNull = jsonResponse.getBoolean("validationNomNull");
                                Boolean validationEmailNull = jsonResponse.getBoolean("validationEmailNull");
                                Boolean validationEmail = null;
                                Boolean validationNom = null;

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
                                //Verifier si la connexion du compte est valide
                                if(success)
                                {
                                    //On verifie si le boolean validationEmail n'est pas null Si il est null sa veut dire que l'utilisateur a
                                    // entrer son nom pour ce connecter.
                                    if(validationEmail != null)
                                    {
                                        //Cette condition verifie si l'utilisateur se connecte pour la premiere fois
                                        // Si oui, on ouvre une nouvelle activity pour verifier son compte
                                        // Lors de l'inscription on envoit un code par email
                                        if(validationEmail)
                                        {
                                            Intent intent = new Intent(Connexion.this, VerificationCompte.class);
                                            SaveSharedPreference.setUserName(getApplication(), userText);//Sauvegarde dans l'application sont username
                                            SaveSharedPreference.setPassword(getApplication(), mdpText);//Sauvegarde dans l'application sont password
                                            startActivity(intent);
                                        }
                                        else if(!validationEmail)//Son compte a deja ete confirmer et verifier
                                        {
                                            Intent intent3 = new Intent(Connexion.this, MainActivity.class);
                                            SaveSharedPreference.setUserName(getApplication(), userText);//Sauvegarde dans l'application sont username
                                            SaveSharedPreference.setPassword(getApplication(), mdpText);//Sauvegarde dans l'application sont password
                                            startActivity(intent3);
                                        }
                                    }
                                    if(validationNom != null)
                                    {
                                        //Cette condition verifie si l'utilisateur se connecte pour la premiere fois
                                        // Si oui, on ouvre une nouvelle activity pour verifier son compte
                                        // Lors de l'inscription on envoit un code par email
                                        if(validationNom)
                                        {
                                            Intent intent = new Intent(Connexion.this, VerificationCompte.class);
                                            SaveSharedPreference.setUserName(getApplication(), userText);//Sauvegarde dans l'application sont username
                                            SaveSharedPreference.setPassword(getApplication(), mdpText);//Sauvegarde dans l'application sont password
                                            startActivity(intent);
                                        }
                                        else if(!validationNom)//Son compte a deja ete confirmer et verifier
                                        {
                                            SaveSharedPreference.setUserName(getApplication(), userText);//Sauvegarde dans l'application sont username
                                            SaveSharedPreference.setPassword(getApplication(), mdpText);//Sauvegarde dans l'application sont password
                                            Intent intent3 = new Intent(Connexion.this, Splash.class);
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
                    // Une requete qui vérifie si les informations de la personne sont correcte
                    LoginRequest loginRequest = new LoginRequest(userText, mdpText, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Connexion.this);
                    queue.add(loginRequest);
                }
            });
        }
        else
        {
                // Les informations de l'utilisateur sont deja sauvegarder dans l'application le SaveSharedPreference du username et du password
                Intent intent = new Intent(Connexion.this, Splash.class);
                startActivity(intent);
        }
    }

}
