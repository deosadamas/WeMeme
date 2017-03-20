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
    TextView txtMessage;
    EditText etUsername;
    EditText etAge;
    public String name;
    public String username;
    public int age;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_page1, container, false);
        Intent intent = getActivity().getIntent();
        name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");
        age = intent.getIntExtra("age", -1);
        txtMessage = (TextView)view.findViewById(R.id.txtMessage);
        etUsername = (EditText)view.findViewById(R.id.txtUser);
        etAge = (EditText)view.findViewById(R.id.txtAge);

        // Display user details
        String message = name + " Bienvenu Osti de pogo !!";
        txtMessage.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
        return view;
    }
}
