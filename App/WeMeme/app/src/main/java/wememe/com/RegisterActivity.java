package wememe.com;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    Random rdn;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rdn = new Random();
        //    final ImageButton imgIns = (ImageButton) findViewById(R.id.imgInsPro);
        final EditText edtxtEmailSign = (EditText) findViewById(R.id.txtEmailSign);
        final EditText edtxtMemeurSign = (EditText) findViewById(R.id.txtMemeurSign);
        final EditText edtxtMotDePasseSign = (EditText) findViewById(R.id.txtMDPSign);
        final EditText edtxtMotDePasseSame = (EditText) findViewById(R.id.txtMDPCSign);
        final EditText edtxtDateSign = (EditText) findViewById(R.id.txtDateSign);
        final Button btnSign = (Button) findViewById(R.id.btnSign);

        edtxtEmailSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtEmailSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_email_over,0,0,0);
                edtxtMemeurSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_memeur,0,0,0);
                edtxtDateSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_datecalender,0,0,0);
                edtxtMotDePasseSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtMotDePasseSame.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
            }
        });
        edtxtMemeurSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtEmailSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_email,0,0,0);
                edtxtMemeurSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_memeur_over,0,0,0);
                edtxtMotDePasseSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtMotDePasseSame.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtDateSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_datecalender,0,0,0);
            }
        });
        edtxtMotDePasseSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtEmailSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_email,0,0,0);
                edtxtMemeurSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_memeur,0,0,0);
                edtxtMotDePasseSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_over,0,0,0);
                edtxtMotDePasseSame.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtDateSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_datecalender,0,0,0);
            }
        });
        edtxtMotDePasseSame.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtEmailSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_email,0,0,0);
                edtxtMemeurSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_memeur,0,0,0);
                edtxtMotDePasseSame.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock_over,0,0,0);
                edtxtMotDePasseSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtDateSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_datecalender,0,0,0);
            }
        });
        edtxtDateSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edtxtEmailSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_email,0,0,0);
                edtxtMemeurSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_memeur,0,0,0);
                edtxtMotDePasseSame.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtMotDePasseSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtDateSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_datecalender_over,0,0,0);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailSign = edtxtEmailSign.getText().toString();
                final String memeurSign = edtxtMemeurSign.getText().toString();
                final String motDePasseSign = edtxtMotDePasseSign.getText().toString();
                final String motDePasseSame = edtxtMotDePasseSame.getText().toString();
                final String dateSign = edtxtDateSign.getText().toString();
                edtxtEmailSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_email,0,0,0);
                edtxtMemeurSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_memeur,0,0,0);
                edtxtMotDePasseSame.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtMotDePasseSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock,0,0,0);
                edtxtDateSign.setCompoundDrawablesWithIntrinsicBounds(R.drawable.register_datecalender,0,0,0);

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean suc = jsonResponse.getBoolean("success");

                            if(!suc){
                                if(jsonResponse.has("error_mail"))
                                {
                                    AlertDialog.Builder d = new AlertDialog.Builder(RegisterActivity.this);
                                    d.setMessage("Le email est déjà utilisé")
                                            .setNegativeButton("Recommencer", null)
                                            .create()
                                            .show();
                                }else if(jsonResponse.has("error_memeur")){
                                    AlertDialog.Builder d = new AlertDialog.Builder(RegisterActivity.this);
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

                if(isValid(dateSign) && isValidPassWord(motDePasseSign, motDePasseSame)) {
                    code = rdn.nextInt(9999 - 1000) +1000;
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    RegisterRequest registerRequest = new RegisterRequest(emailSign, memeurSign, motDePasseSign, dateSign, code, responseListener);
                    queue.add(registerRequest);
                    EmailRequest emailRequest = new EmailRequest(emailSign, memeurSign, code,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {}
                    });
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    emailRequest.setRetryPolicy(policy);
                    queue.add(emailRequest);
                    Snackbar snackbar;
                    snackbar = Snackbar.make(v, "Le courriel a été envoyé", Snackbar.LENGTH_LONG);
                    v = snackbar.getView();
                    v.setBackgroundColor(Color.parseColor("#371e6d"));
                    snackbar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);
                }
            }
        });
    }

    public boolean isValid(String date) {
        if (date.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))" +
                "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))" +
                "$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            return true;
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Le Mot de passe n'est pas identique")
                    .setNegativeButton("Recommencer", null)
                    .create()
                    .show();
            return  false;
        }
    }
}