package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import co.in.dreamguys.cream.adapter.SettingsAdapter;

/**
 * Created by user5 on 23-02-2017.
 */

public class Settings extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mListViewWidgets;
    String[] settings;
    SettingsAdapter aSettingsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initWidgets();
        settings = new String[]{
                getString(R.string.str_working_status),
                getString(R.string.str_custom_fields),
                getString(R.string.str_useful_links),
                getString(R.string.str_app_settings),
        };


        aSettingsAdapter = new SettingsAdapter(Settings.this, settings);
        mListViewWidgets.setAdapter(aSettingsAdapter);

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_settings_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mListViewWidgets = (ListView) findViewById(R.id.AS_LV_settings);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
