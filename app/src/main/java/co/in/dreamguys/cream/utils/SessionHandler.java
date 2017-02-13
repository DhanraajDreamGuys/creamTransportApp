package co.in.dreamguys.cream.utils;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by user5 on 30-01-2017.
 */

public class SessionHandler extends Application {
    static SharedPreferences myPrefs;
    static SharedPreferences.Editor prefsEditor;
    public static final String PREFERENCEVARIABLE = "DriverAdmin";

    public void onCreate() {
        super.onCreate();
        myPrefs = this.getSharedPreferences(PREFERENCEVARIABLE, MODE_PRIVATE);
    }

    /***
     * String preference(getter,setter)
     ***/
    public static void setStringPref(String key, String value) {
        prefsEditor = myPrefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static String getStringPref(String key) {
        return myPrefs.getString(key, "");
    }

    /***
     * Integer preference(getter,setter)
     ***/
    public static void setIntPref(String key, int value) {
        prefsEditor = myPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public static int getIntPref(String key) {
        return myPrefs.getInt(key, 0);
    }


    public static void clearPrefs() {
        myPrefs.edit().clear().apply();
    }


}
