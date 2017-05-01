package wememe.ca.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import wememe.ca.R;
import wememe.ca.Requetes.RegisterRequest;

public class Inscription extends AppCompatActivity {

    Random rdn;
    int code;
    boolean emailTest = false;
    View viewsnack;

    EditText emailInscription;
    EditText usernameInscription;
    EditText mdpInscription;
    EditText verifierMDPInscription;
    EditText dateInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        rdn = new Random();
        //final ImageButton imgIns = (ImageButton) findViewById(R.id.imgInsPro);
        emailInscription = (EditText) findViewById(R.id.fieldEmailInscription);
        usernameInscription = (EditText) findViewById(R.id.fieldUsernameInscription);
        mdpInscription = (EditText) findViewById(R.id.fieldMDPInscription);
        verifierMDPInscription = (EditText) findViewById(R.id.fieldVerifierMDPInscription);
        dateInscription = (EditText) findViewById(R.id.fieldDateInscription);
        final Button btnInscription = (Button) findViewById(R.id.btnInscription);

        emailInscription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_email_focus, 0, 0, 0);
                usernameInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_username_no_focus, 0, 0, 0);
                mdpInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                verifierMDPInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                dateInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_date_no_focus, 0, 0, 0);
            }
        });
        usernameInscription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_email_no_focus, 0, 0, 0);
                usernameInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_username_focus, 0, 0, 0);
                mdpInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                verifierMDPInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                dateInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_date_no_focus, 0, 0, 0);
            }
        });
        mdpInscription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_email_no_focus, 0, 0, 0);
                usernameInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_username_no_focus, 0, 0, 0);
                mdpInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_focus, 0, 0, 0);
                verifierMDPInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                dateInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_date_no_focus, 0, 0, 0);
            }
        });
        verifierMDPInscription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_email_no_focus, 0, 0, 0);
                usernameInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_username_no_focus, 0, 0, 0);
                mdpInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                verifierMDPInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_focus, 0, 0, 0);
                dateInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_date_no_focus, 0, 0, 0);
            }
        });
        dateInscription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_email_no_focus, 0, 0, 0);
                usernameInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_username_no_focus, 0, 0, 0);
                mdpInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                verifierMDPInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
                dateInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_date_focus, 0, 0, 0);
            }
        });
    }

    public void Ins(View view)
    {
        viewsnack = view;
        final String emailText = emailInscription.getText().toString();
        final String usernameText = usernameInscription.getText().toString();
        final String mdpText = mdpInscription.getText().toString();
        final String mdpVerifierText = verifierMDPInscription.getText().toString();
        final String dateText = dateInscription.getText().toString();
        emailInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_email_no_focus, 0, 0, 0);
        usernameInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_username_no_focus, 0, 0, 0);
        mdpInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
        verifierMDPInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.connexion_lock_no_focus, 0, 0, 0);
        dateInscription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.inscription_date_no_focus, 0, 0, 0);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean suc = jsonResponse.getBoolean("success");
                    boolean email = jsonResponse.getBoolean("email");


                    if(suc)
                    {
                        if(email)
                        {
                            Snackbar snackbar;
                            snackbar = Snackbar.make(viewsnack, "Le courriel a été envoyé", Snackbar.LENGTH_LONG);
                            viewsnack = snackbar.getView();
                            viewsnack.setBackgroundColor(Color.parseColor("#371e6d"));
                            snackbar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Inscription.this, Connexion.class);
                                    startActivity(intent);
                                }
                            }, 2000);
                        }
                    }
                    else
                    {
                        if (jsonResponse.has("error_mail")) {
                            AlertDialog.Builder d = new AlertDialog.Builder(Inscription.this);
                            d.setMessage("Cet email est déjà utilisé")
                                    .setNegativeButton("Recommencer", null)
                                    .create()
                                    .show();
                        } else if (jsonResponse.has("error_memeur")) {
                            AlertDialog.Builder d = new AlertDialog.Builder(Inscription.this);
                            d.setMessage("Le nom de memeur est déjà utilisé")
                                    .setNegativeButton("Recommencer", null)
                                    .create()
                                    .show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        if(isValid(dateText) && isValidPassWord(mdpText, mdpVerifierText)) {

            code = rdn.nextInt(9999 - 1000) + 1000;

            RequestQueue queue = Volley.newRequestQueue(Inscription.this);
            RegisterRequest registerRequest = new RegisterRequest(emailText, usernameText, mdpText, dateText, code, responseListener);
            int socketTimeout = 30000;//30 seconds
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            registerRequest.setRetryPolicy(policy);
            queue.add(registerRequest);
        }
    }

    public boolean isValid(String date) {
        if (date.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))" +
                "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))" +
                "$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            return true;
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Inscription.this);
            builder.setMessage("Le format de date est invalide \nDD/MM/YYYY\nDD-MM-YYYY\nDD.MM.YYYY")
                    .setNegativeButton("Recommencer", null)
                    .create()
                    .show();
            return false;
        }
    }

    public boolean isValidPassWord(String p1, String p2){
        if(p1.equals(p2)){
            return true;
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(Inscription.this);
            builder.setMessage("Les mot de passe ne sont pas identiques")
                    .setNegativeButton("Recommencer", null)
                    .create()
                    .show();
            return  false;
        }
    }
}
