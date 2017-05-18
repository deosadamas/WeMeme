package wememe.ca.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference  {

    /*
        Cette classe va simplement sauvegarder les informations de l'utilisateur dans le telephone
        PREF_USER_NAME= "username"
        PREF_USER_Password= "password"
    */

    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_Password= "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    //Fonction qui set le username de la personne
    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    //Fonction qui set le password de la personne
    public static void setPassword(Context ctx, String password)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_Password, password);
        editor.commit();
    }

    //Fonction qui retourne le username sauvegarder
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    //Fonction qui retourne le password sauvegarder
    public static String getUserPassword(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_Password, "");
    }
}
