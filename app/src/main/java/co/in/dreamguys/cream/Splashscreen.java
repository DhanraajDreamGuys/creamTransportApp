package co.in.dreamguys.cream;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.SessionHandler;

/**
 * Created by user5 on 14-02-2017.
 */

public class Splashscreen extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SessionHandler.getStringPref(Constants.USER_ID).isEmpty()) {
                    ActivityConstants.callPage(Splashscreen.this, AdminMenu.class);
                    finish();
                } else {
                    ActivityConstants.callPage(Splashscreen.this, Login.class);
                    finish();
                }
            }
        }, 3000);
    }
}
