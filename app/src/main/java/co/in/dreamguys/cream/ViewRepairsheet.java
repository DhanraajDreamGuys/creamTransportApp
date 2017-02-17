package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 17-02-2017.
 */

public class ViewRepairsheet extends AppCompatActivity {
    Toolbar mToolbar;
    EditText mfaults_to_repair,mComments;
    Button mButton;


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
            mComments.setFocusable(true);
            mComments.setClickable(true);
            mComments.setFocusableInTouchMode(true);
            mButton.setText("Save");
        } else {
            mToolbar.setTitle(getString(R.string.tool_view_repairsheet_title));
            mComments.setFocusable(false);
            mComments.setClickable(false);
            mComments.setFocusableInTouchMode(false);
            mButton.setVisibility(View.GONE);
        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mComments = (EditText) findViewById(R.id.AVRS_ET_workshop_comments);
mButton = (Button)findViewById(R.id.AVRS_BT_save);
    }
}
