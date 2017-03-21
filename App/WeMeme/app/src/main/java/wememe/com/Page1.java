package wememe.com;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dnksj on 2017-03-06.
 */

public class Page1 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_page1, container, false);
        Intent intent = getActivity().getIntent();
        String memeur = intent.getStringExtra("memeur");
        String date = intent.getStringExtra("date");
        TextView txtMessage = (TextView)view.findViewById(R.id.txtMessage);
        EditText edtxtMemeur = (EditText)view.findViewById(R.id.edUtilisateur);
        EditText edtxtDate = (EditText)view.findViewById(R.id.txtAge);

        // Display user details
        String message =" Bienvenu " + memeur + " Osti de pogo !!";
        txtMessage.setText(message);
        edtxtMemeur.setText(memeur);
        edtxtDate.setText(date);
        return view;
    }
}
