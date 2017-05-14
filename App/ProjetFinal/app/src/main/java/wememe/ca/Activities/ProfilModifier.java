package wememe.ca.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import wememe.ca.Class.SaveSharedPreference;
import wememe.ca.R;
import wememe.ca.Requetes.ModificationProfilRequest;

public class ProfilModifier extends AppCompatActivity {

    private EditText password;
    private EditText password1;
    private ImageView imagePhotoProfil;
    private Button btnUpload;
    private Button btnEnregistrer;
    public static final String UPLOAD_URL = "http://wememe.ca/image_serveur/image_profil_upload.php";
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    public Bitmap bitmap;
    public Bitmap bitmapResize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_modifier);

        password = (EditText) findViewById(R.id.txt_Pass_Modif_Profil);
        password1 = (EditText) findViewById(R.id.txt_Pass_Modif_Profil2);
        btnEnregistrer= (Button)findViewById(R.id.btn_Enregistre_Modi_Profil);
        imagePhotoProfil= (ImageView)findViewById(R.id.imageProfilModifier);
        btnUpload = (Button)findViewById(R.id.btn_Up_modif_Prof);
        btnUpload.setEnabled(false);
        imagePhotoProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                btnUpload.setEnabled(true);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().equals(password1.getText().toString()))
                {

                    ModificationProfilRequest modificationProfilRequest = new ModificationProfilRequest(MainActivity.utilisateur.getId(), password.getText().toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ProfilModifier.this, response, Toast.LENGTH_SHORT).show();
                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(ProfilModifier.this);
                    queue.add(modificationProfilRequest);
                    SaveSharedPreference.setPassword(getApplication(), password.getText().toString());
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfilModifier.this);
                    builder.setMessage("Les mot de passe ne sont pas identiques")
                            .setNegativeButton("Recommencer", null)
                            .create()
                            .show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmapResize = bitmap.createScaledBitmap(bitmap, 250, 200, true);
                imagePhotoProfil.setImageBitmap(bitmapResize);
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
                loading = ProgressDialog.show(ProfilModifier.this, "Uploading...", null,true,true);
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
                data.put("image", uploadImage);
                data.put("id", String.valueOf(MainActivity.utilisateur.getId()));
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmapResize);
    }

    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
}
