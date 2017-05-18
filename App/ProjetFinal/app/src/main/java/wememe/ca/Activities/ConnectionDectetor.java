package wememe.ca.Activities;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDectetor {

    /*
        Cette classe verifie si l'utilisateur  est connecter a l'internet dans le manifest nous avons besoins de
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    */

    Context context;//Le context

    //Le constructeur qui va nous permettre d'aller chercher le contexte de l'activity choisi
    public ConnectionDectetor(Context context) {
        this.context = context;
    }

    //Cette fonction va simplement verifie si l'utilisateur est connecter a Internet
    public boolean isConnected()
    {
        // ConnectivityManager nous dit simplement si l'internet est disponible ou non
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            // Si l'internet est connecter ou non
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null)
            {
                //Si celui-ci est conneter
                if(networkInfo.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
/*          // Connection par WIFI
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
             // Connection par 3G
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }*/
            }
        }
        return false;
    }
}
