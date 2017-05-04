package wememe.ca.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wememe.ca.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recherche extends Fragment {

    TextView txtTest;

    public Recherche() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recherche, container, false);

        txtTest = (TextView)view.findViewById(R.id.txtTest);
        txtTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTest.setText("reussi");
            }
        });
        return view;
    }

}
