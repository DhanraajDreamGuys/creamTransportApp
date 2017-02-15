package co.in.dreamguys.cream;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.in.dreamguys.cream.utils.Util;

/**
 * Created by user5 on 14-02-2017.
 */

public class Paysheet extends AppCompatActivity {
    Toolbar mToolbar;
    DatePickerDialog fromDatePickerDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysheet);
        initWidgets();

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_paysheet_title));
        mToolbar.setTitleTextColor(Color.WHITE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_search) {
            Util.searchPopUpWindow(Paysheet.this, getLayoutInflater());
        }
        return super.onOptionsItemSelected(item);
    }


}
