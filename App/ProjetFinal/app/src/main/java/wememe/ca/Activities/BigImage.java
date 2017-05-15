package wememe.ca.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import wememe.ca.R;

public class BigImage extends AppCompatActivity {

    ImageView img;
    static String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        img = (ImageView) findViewById(R.id.imgBigImage);
        if(b != null)
        {
            Glide.with(getApplication()).load(b).into(img);
        }
    }

    public static void getImage(String a)
    {
        b = a;
    }
}
