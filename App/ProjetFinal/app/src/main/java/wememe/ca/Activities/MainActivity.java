package wememe.ca.Activities;

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
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import wememe.ca.R;

public class MainActivity extends FragmentActivity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;


    public static final String UPLOAD_URL = "http://wememe.ca/image_serveur/image_upload.php";
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;

    String description;
    String nom;
    String sujet;

    boolean dernierFeed = false;
    boolean dernierRecherche = false;
    boolean dernierTendances = false;
    boolean dernierProfil = false;

    private Bitmap bitmap;

    private Uri filePath;

    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = (BottomBar)findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_feed:
                        changerFragment(new Feed());
                        dernierFeed = true;
                        dernierRecherche = false;
                        dernierTendances = false;
                        dernierProfil = false;
                        break;
                    case R.id.tab_recherche:
                        changerFragment(new Recherche());
                        dernierFeed = false;
                        dernierRecherche = true;
                        dernierTendances = false;
                        dernierProfil = false;
                        break;
                    case R.id.tab_tendances:
                        changerFragment(new Tendances());
                        dernierFeed = false;
                        dernierRecherche = false;
                        dernierTendances = true;
                        dernierProfil = false;
                        break;
                    case R.id.tab_profil:
                        changerFragment(new Profil());
                        dernierFeed = false;
                        dernierRecherche = false;
                        dernierTendances = false;
                        dernierProfil = true;
                        break;
                    case R.id.tab_publier:
                        description = "La description du test";
                        sujet = "Le sujet du test";
                        nom = "Le nom du test";
                        showFileChooser();
                        break;
                }
            }
        });
        bottomBar.setDefaultTab(R.id.tab_feed);
        dernierFeed = true;
    }

    private void changerFragment(Fragment fragment){
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadImage();
                if (dernierFeed){
                    bottomBar.setDefaultTab(R.id.tab_feed);
                }
                else if (dernierRecherche)
                {
                    bottomBar.setDefaultTab(R.id.tab_recherche);
                }
                else if (dernierTendances)
                {
                    bottomBar.setDefaultTab(R.id.tab_tendances);
                }
                else if (dernierProfil)
                {
                    bottomBar.setDefaultTab(R.id.tab_profil);
                }
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

    private void uploadImage(){
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
                data.put("sujet", sujet);
                data.put("nom", nom);
                data.put("description", description);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
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
                        if (dernierRecherche){
                            bottomBar.selectTabAtPosition(0);
                            dernierFeed = true;
                            dernierRecherche = false;
                            dernierTendances = false;
                            dernierProfil = false;
                        }
                        else if (dernierTendances){
                            showFileChooser();
                            description = "La description du test";
                            sujet = "Le sujet du test";
                            nom = "Le nom du test";
                        }
                        else if (dernierProfil){
                            bottomBar.selectTabAtPosition(3);
                            dernierFeed = false;
                            dernierRecherche = false;
                            dernierTendances = true;
                            dernierProfil = false;
                        }
                    }

                    // Right to left swipe action
                    else
                    {
                        if (dernierRecherche){
                            showFileChooser();
                            description = "La description du test";
                            sujet = "Le sujet du test";
                            nom = "Le nom du test";
                        }
                        else if (dernierTendances){
                            bottomBar.selectTabAtPosition(4);
                            dernierFeed = false;
                            dernierRecherche = false;
                            dernierTendances = false;
                            dernierProfil = true;
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
        return Integer.parseInt(maxID);
    }

    public BottomBar getBottomBar(){
        return bottomBar;
    }
}
