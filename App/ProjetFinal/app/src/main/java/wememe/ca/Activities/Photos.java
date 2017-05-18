package wememe.ca.Activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import wememe.ca.R;

/**
 * Created by info1 on 2017-05-11.
 */

public class Photos extends Fragment {

    Button btnGallery;
    Button btnUpload;
    EditText edtDescription;
    View view;

    public Photos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Variables
        view = inflater.inflate(R.layout.fragment_photo, container, false);
        btnGallery = (Button)view.findViewById(R.id.btnGallery);
        btnUpload = (Button)view.findViewById(R.id.btnUpload);
        edtDescription = (EditText)view.findViewById(R.id.fieldDescription);
        edtDescription.getBackground().setColorFilter(getResources().getColor(R.color.colorTexte), PorterDuff.Mode.SRC_IN);
        //Le boutton gallery sert a appeller la methode showFileChooser de la mainActivity pour montrer sa gallerie de photo
        //à l'utilisateur
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity)getActivity();
                activity.showFileChooser();
            }
        });
        //le button upload appelle uploadImage pour envoyer une image au serveur
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                activity.uploadImage();
            }
        });
        //Changer la couleur du texte box selon s'il est selectionné ou pas
        edtDescription.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus){
                    edtDescription.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                }
                else{
                    edtDescription.getBackground().setColorFilter(getResources().getColor(R.color.colorTexte), PorterDuff.Mode.SRC_IN);
                }
            }
        });
        return view;
    }


}
