package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import co.in.dreamguys.cream.apis.PBWorkShopAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

/**
 * Created by user5 on 06-03-2017.
 */

public class ViewPBWorkshop extends AppCompatActivity {
    Toolbar mToolbar;
    public static String TAG = ViewPBWorkshop.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    PBWorkShopAPI.Datum mData;
    EditText mManager, mName, mAddress, mPhoneNo;
    SwitchCompat mStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_view_phonebook);

        initWidgets();

        mData = (PBWorkShopAPI.Datum) getIntent().getSerializableExtra(Constants.PB_WORKSHOP_DATA);
        if (mData != null) {
            mName.setText(mData.getName());
            mPhoneNo.setText(mData.getPhone_no());
            mManager.setText(mData.getManager());
            mAddress.setText(mData.getAddress());
            if (mData.getStatus().equalsIgnoreCase("0")) {
                mStatus.setChecked(true);
                mStatus.setText(getString(R.string.active));
            } else {
                mStatus.setChecked(false);
                mStatus.setText(getString(R.string.inactive));
            }
        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_view_pb_workshop_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (EditText) findViewById(R.id.AWVPB_ET_name);
        mAddress = (EditText) findViewById(R.id.AWVPB_ET_address);
        mPhoneNo = (EditText) findViewById(R.id.AWVPB_ET_phoneno);
        mManager = (EditText) findViewById(R.id.AWVPB_ET_managername);
        mStatus = (SwitchCompat) findViewById(R.id.AWVPB_SC_status);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
