package wememe.ca.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wememe.ca.Class.DataLike;
import wememe.ca.Class.Data_Feed;
import wememe.ca.Class.Like;
import wememe.ca.Class.MyReport;
import wememe.ca.R;
import wememe.ca.Requetes.ReportRequest;

public class Repport extends AppCompatActivity {
    // Déclaration des differentes variables
    private RadioButton Porno;
    private int ID;
    private  RadioButton Confidentielle;
    private RadioButton Nude;
    private RadioButton Autre;
    private int[] Report = {0,0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repport);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getInt("id");
        Confidentielle = (RadioButton)findViewById(R.id.rbtn_Confidentielle);
        Autre = (RadioButton)findViewById(R.id.rbtn_Autre);
        Porno = (RadioButton)findViewById(R.id.rbtn_Porno);
        Nude = (RadioButton)findViewById(R.id.rbtn_Nude);
    }

    public void Envoyer(View view) {
        // Tableau String [0] = Prono
        // Tableau String [1] = Nude
        // Tableau String [2] = Confidentielle
        // Tableau String [3] = Autre

        if(Porno.isChecked()){
         Report[0]= 1;
        }
        if(Nude.isChecked()){
        Report[1]= 1;
        }
        if(Confidentielle.isChecked()){
         Report[2]= 1;
        }
        if(Autre.isChecked()){
         Report[3] = 1;
         }

         Traitement(ID,this);
   }

   public void Traitement(final int id ,final Context context){
       //Utilise le AsyncTask simplement pour  télécharger l'information dans le background de l'application
       AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
           @Override
           protected Void doInBackground(Integer... integers) {
                   RequestQueue queue = Volley.newRequestQueue(Repport.this);
                   ReportRequest request1 = new ReportRequest(String.valueOf(id),String.valueOf(Report[0]),String.valueOf(Report[1]),String.valueOf(Report[2]),String.valueOf(Report[3]), new com.android.volley.Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           Close();
                       }
                   });
                   queue.add(request1);
               return null;
           }

           //A chaque fois qu'il a de nouvelle information sa les ajoutes et rafraichit le recycleview
           @Override
           protected void onPostExecute(Void aVoid) {

           }
       };
       task.execute(id);//Execute la asynctaks
   }

   public void Close(){
       this.finish();
   }
}
