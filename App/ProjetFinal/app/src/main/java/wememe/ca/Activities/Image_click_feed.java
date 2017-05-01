package wememe.ca.Activities;

import android.support.v4.app.Fragment;
import com.roughike.bottombar.BottomBar;
/**
 * Created by dnksj on 2017-05-01.
 */

public class Image_click_feed extends Fragment {
    public void replaceFragment() {
        MainActivity activity = (MainActivity)getActivity();
        BottomBar myBottomBar = activity.getBottomBar();
        myBottomBar.selectTabAtPosition(1);
    }
}
