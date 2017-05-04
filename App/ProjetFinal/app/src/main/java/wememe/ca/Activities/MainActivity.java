package wememe.ca.Activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import wememe.ca.R;

public class MainActivity extends FragmentActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    /////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    private Fragment fragment = new photo();
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static final String UPLOAD_URL = "http://wememe.ca/image_serveur/image_upload.php";
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;

    String description;
    String nom;
    String sujet;
    int id_user_post;
    int id_personne;
    Button btnGallerie;
    ImageView imageView;
    Feed_max_id feed_max_id;
    int id_max = 0;


    public Bitmap bitmap;

    private Uri filePath;

    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_personne = load_data_from_server();
        bottomBar = (BottomBar)findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_feed:
                        id_personne = load_data_from_server();
                        bottomBar.setActiveTabColor(getResources().getColor(R.color.colorAccent));
                        changerFragment(new Feed());
                        break;
                    case R.id.tab_recherche:
                        changerFragment(new Recherche());
                        break;
                    case R.id.tab_tendances:
                        changerFragment(new Tendances());
                        break;
                    case R.id.tab_profil:
                        changerFragment(new Profil());
                        break;
                    case R.id.tab_publier:
                        changerFragment(fragment);
                        break;
                }
            }
        });
        bottomBar.selectTabAtPosition(4);
    }

    public void changerFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.finishAffinity();
        }
        else {
            ActivityCompat.finishAffinity(this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                imageView = (ImageView) fragment.getView().findViewById(R.id.imageGallery);
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("sujet", "a");
                data.put("nom", "nom");
                data.put("description", "des");
                data.put("$id_user_post", "0");
                data.put("$id_user_photo", "http://wememe.ca/image_serveur/image_feed/121.png");
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }


    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        if (bottomBar.getCurrentTabPosition() == 1){
                            bottomBar.selectTabAtPosition(0);

                        }
                        else if (bottomBar.getCurrentTabPosition() == 2){
                            bottomBar.selectTabAtPosition(1);
                        }
                        else if (bottomBar.getCurrentTabPosition() == 3){
                            bottomBar.selectTabAtPosition(2);
                        }
                        else if (bottomBar.getCurrentTabPosition() == 4) {
                            bottomBar.selectTabAtPosition(3);
                        }
                    }

                    // Right to left swipe action
                    else
                    {
                        if (bottomBar.getCurrentTabPosition() == 0){
                            bottomBar.selectTabAtPosition(1);

                        }
                        else if (bottomBar.getCurrentTabPosition() == 1){
                            bottomBar.selectTabAtPosition(2);
                        }
                        else if (bottomBar.getCurrentTabPosition() == 2){
                            bottomBar.selectTabAtPosition(3);
                        }
                        else if (bottomBar.getCurrentTabPosition() == 3) {
                            bottomBar.selectTabAtPosition(4);
                        }
                    }
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public int getMaxID(){
        //Get max ID from Connexion
        String maxID = getIntent().getStringExtra("maxID");
        return id_personne;
    }

    public void id_user_post(int id_user_post){
        this.id_user_post = id_user_post;
    }

    public BottomBar getBottomBar(){
        return bottomBar;
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
