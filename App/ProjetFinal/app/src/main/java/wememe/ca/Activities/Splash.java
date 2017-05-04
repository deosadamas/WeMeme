package wememe.ca.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        load_data_from_server();

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... Void) {
                com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            utilisateur = new Utilisateur(String.valueOf(object.getInt("id")), object.getString("email"), object.getString("username"), object.getString("date"), object.getString("profilpic"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(Splash.this);
                InformationUserRequest informationUserRequest = new InformationUserRequest(user,password ,responseListener);
                queue.add(informationUserRequest);
                return null;
            }
        };
        task.execute();

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                i.putExtra("user", user);
                i.putExtra("password", password);
                i.putExtra("id_max_feed", String.valueOf(id_max));
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

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

}
