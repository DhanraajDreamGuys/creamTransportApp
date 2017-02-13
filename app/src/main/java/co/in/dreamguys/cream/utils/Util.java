package co.in.dreamguys.cream.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Patterns;

/**
 * Created by user5 on 13-02-2017.
 */

public class Util {

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public static boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static boolean isNetworkAvailable1(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
