package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ToggleButton;

import co.in.dreamguys.cream.apis.PBTruckAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

/**
 * Created by user5 on 06-03-2017.
 */

public class ViewPBTruck extends AppCompatActivity {
    Toolbar mToolbar;
    public static String TAG = ViewPBTruck.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    PBTruckAPI.Datum mData;
    EditText mTruckNo, mPhoneNo;
    ToggleButton mStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_view_phonebook);

        initWidgets();

        mData = (PBTruckAPI.Datum) getIntent().getSerializableExtra(Constants.PB_TRUCK_DATA);
        if (mData != null) {
            mTruckNo.setText(mData.getTruck_no());
            mPhoneNo.setText(mData.getPhone_no());

            if (mData.getStatus().equalsIgnoreCase("0")) {
                mStatus.setChecked(true);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(ViewPBTruck.this, R.drawable.button_background_green));
            } else {
                mStatus.setChecked(false);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(ViewPBTruck.this, R.drawable.button_background_red));
            }
        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_view_pb_truck_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mTruckNo = (EditText) findViewById(R.id.ATVPB_ET_truckno);
        mPhoneNo = (EditText) findViewById(R.id.ATVPB_ET_phoneno);
        mStatus = (ToggleButton) findViewById(R.id.ATVPB_TB_status);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
