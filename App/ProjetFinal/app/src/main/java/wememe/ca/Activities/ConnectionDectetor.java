package wememe.ca.Activities;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dnksj on 2017-05-12.
 */

public class ConnectionDectetor {
    Context context;

    public ConnectionDectetor(Context context) {
        this.context = context;
    }

    public boolean isConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null)
            {
                if(networkInfo.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
