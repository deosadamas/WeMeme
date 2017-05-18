package wememe.ca.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import wememe.ca.R;

public class BigImage extends AppCompatActivity {

    /*
        Cette activity va simplement prendre l'image de la personne qui a doubleclick sur une et l'ouvrir dans
        une nouvelle activity pour la mettre en plein ecran
    */

    ImageView img;
    static String imageFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        img = (ImageView) findViewById(R.id.imgBigImage);

        if(imageFeed != null)//Si l'image est pas trouver donc null ou l'affiche pas dans l'imageview
        {
            //Cette fonction de Glide nous perment simplement d'assoicier un image pour l'inserer dans un ImageView
            Glide.with(getApplication()).load(imageFeed).into(img);
        }
    }

    //Cette Fonction nous permet d'aller chercher l'image doubleclick par l'utilisateur
    //En paramettre un String (url) de l'image le Glide est une librairie qui va prendre le
    // String de l'image est la faire afficher
    public static void getImage(String image)
    {
        imageFeed = image;
    }
}
