package co.in.dreamguys.cream.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by user5 on 30-01-2017.
 */

public class ActivityConstants {

    public static void callPage(Context mContext, Class mClass) {
        mContext.startActivity(new Intent(mContext, mClass));
    }
}
