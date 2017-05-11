package wememe.ca.Activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import wememe.ca.R;
import wememe.ca.Requetes.ReportRequest;

public class Report extends AppCompatActivity {
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
        // Prends le ID du poste a report et l'asigne a une variable
        ID = bundle.getInt("id");

        //Assignation des differentes variable
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
       //Utilise le AsyncTask pour executer la requete php qui mets a jours les champ conserner pour les diffrents report
       AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
           @Override
           protected Void doInBackground(Integer... integers) {
                   RequestQueue queue = Volley.newRequestQueue(wememe.ca.Activities.Report.this);
                   ReportRequest request1 = new ReportRequest(String.valueOf(id),String.valueOf(Report[0]),String.valueOf(Report[1]),String.valueOf(Report[2]),String.valueOf(Report[3]), new com.android.volley.Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           Close();
                       }
                   });
                   queue.add(request1);
               return null;
           }


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
