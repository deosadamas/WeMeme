package weinnovate.wememe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
    }

    public void Connection (View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void SignIn (View view){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

}
