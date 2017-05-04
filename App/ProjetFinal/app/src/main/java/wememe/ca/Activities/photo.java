package wememe.ca.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import wememe.ca.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class photo extends Fragment{

    Button btnGallery;
    Button btnUpload;
    EditText edtDescription;
    View view;

    public photo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_photo, container, false);
        btnGallery = (Button)view.findViewById(R.id.btnGallery);
        btnUpload = (Button)view.findViewById(R.id.btnUpload);
        edtDescription = (EditText)view.findViewById(R.id.fieldDescription);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity)getActivity();
                activity.showFileChooser();
              //  image.setImageBitmap(activity.bitmap);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                activity.uploadImage();
            }
        });
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
