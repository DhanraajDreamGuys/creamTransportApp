package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 17-02-2017.
 */

public class ViewRepairsheet extends AppCompatActivity {
    Toolbar mToolbar;
    EditText mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_repair_sheet);

        initWidgets();

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mToolbar.setTitle(getString(R.string.tool_edit_repairsheet_title));
            mName.setFocusable(true);
            mName.setClickable(true);
            mName.setFocusableInTouchMode(true);
        } else {
            mToolbar.setTitle(getString(R.string.tool_view_repairsheet_title));
        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (EditText) findViewById(R.id.AVRS_ET_name);

    }
}
