package wememe.com;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

public class User_Area_Activity extends AppCompatActivity {

    BottomBar bottomBar;
    BottomBarBadge unread;
    TextView txtMessage;
    EditText etUsername;
    EditText etAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__area_);
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int i) {

                if(i == R.id.Page1)
                {
                    Page1 h = new Page1();
                    getFragmentManager().beginTransaction().replace(R.id.frame, h).commit();
                }
                else if (i == R.id.Page2)
                {
                    Page2 h = new Page2();
                    getFragmentManager().beginTransaction().replace(R.id.frame, h).commit();
                }
                else if (i == R.id.Page3)
                {
                    Page3 h = new Page3();
                    getFragmentManager().beginTransaction().replace(R.id.frame, h).commit();
                }
                else if (i == R.id.Page4)
                {
                    Page4 h = new Page4();
                    getFragmentManager().beginTransaction().replace(R.id.frame, h).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int i) {

            }
        });

        bottomBar.mapColorForTab(0, "#F44336");
        bottomBar.mapColorForTab(1, "#03A9F4");
        bottomBar.mapColorForTab(2, "#1A237E");
        bottomBar.mapColorForTab(3, "#FFCA28");

     //   unread = bottomBar.makeBadgeForTabAt(2, "#FF0000", 50);
     //   unread = bottomBar.makeBadgeForTabAt(3, "#FF0000", 50);
      //  unread.show();
    }


    public void Quitter(View view)
    {
        Intent intent = new Intent(User_Area_Activity.this, LoginActivity.class);
        startActivity(intent);
    }
}
