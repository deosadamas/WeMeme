package wememe.ca.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import wememe.ca.Class.Utilisateur;
import wememe.ca.R;
import wememe.ca.Requetes.InformationUserRequest;

public class Splash extends Activity {
    String user, password;
    public static int id_max;
    public  static Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashh);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        password = intent.getStringExtra("password");

        // Cette methode met dans un object static Utilisateur c'est information
        load_information_utilisateur();
        // Cette methode va chercher l'id max de la table feed et l'assigne dans la variable static id_max
        load_data_from_server();


        //Commence l'animation
        // Simplement dans le dosssier anim j'ai le rotate.xml avec les caracteristique que jai donner
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //A la fin de l'animation je regarde si l'utilisateur est pas null sinon je recmmence l'animation tant que
                // l'utilisateur n'est pas null
                    if(utilisateur != null)
                    {
                        iv.startAnimation(an2);
                        finish();
                        Intent i = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(i);

                    }else
                    {
                        animation.setDuration(1000);
                        load_information_utilisateur();
                        iv.startAnimation(an);
                    }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //Une fonction qui retourne id_max dans la base de donnee de la table Feed
    public int load_data_from_server() {
        AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... Integer) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=feed_id")
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    JSONObject object = array.getJSONObject(0);
                    id_max = object.getInt("MAX(id)")+1;
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return id_max;
            }
        };
        task.execute();
        return id_max;
    }

    //Une methode qui va chercher les informations de l'utilisateur dans la base de donnee dans la table Register
    private void load_information_utilisateur()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... Void) {
                com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            //Assigne les object json de la reponse du serveur dans la object Utilisateur
                             utilisateur = new Utilisateur(object.getInt("id"), object.getString("email"), object.getString("username"), object.getString("date"), object.getString("profilpic"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(Splash.this);
                //Simplement 3 paramettre important,
                // Le temps pour chaque requete
                // Le nombre d'essais
                // Le temps exponentiel du socket pour chaque essais reessayer
                RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                InformationUserRequest informationUserRequest = new InformationUserRequest(user,password ,responseListener);
                informationUserRequest.setRetryPolicy(policy);
                queue.add(informationUserRequest);
                return null;
            }
        };
        task.execute();
    }

}
