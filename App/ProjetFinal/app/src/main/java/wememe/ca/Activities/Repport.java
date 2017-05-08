package wememe.ca.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import wememe.ca.R;

public class Repport extends AppCompatActivity {
    // Déclaration des differentes variables
    private RadioButton Porno;
    private  RadioButton Confidentielle;
    private RadioButton Nude;
    private RadioButton Autre;
    private String[] Repport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repport);
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
         Repport[0]= "1";
        }
        if(Nude.isChecked()){
        Repport[1]= "1";
        }
        if(Confidentielle.isChecked()){
         Repport[2]= "1";
        }
        if(Autre.isChecked()){
         Repport[3] = "1";
         }
   }
}
