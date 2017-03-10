package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import co.in.dreamguys.cream.adapter.AlertAdapter;

/**
 * Created by user5 on 09-03-2017.
 */

public class Alerts extends AppCompatActivity {
    Toolbar mToolbar;
    ListView mAlertsWidgets;
    String[] alerts;
    AlertAdapter aAlertAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        initWidgets();
        alerts = new String[]{"Alert All", "Alert Paysheet", "Alert Templates"};

        aAlertAdapter = new AlertAdapter(Alerts.this, alerts);
        mAlertsWidgets.setAdapter(aAlertAdapter);

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_alert_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mAlertsWidgets = (ListView) findViewById(R.id.AA_LV_alerts);
    }
}
