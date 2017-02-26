package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.in.dreamguys.cream.model.FuelSheetModel;
import co.in.dreamguys.cream.utils.Constants;

import static co.in.dreamguys.cream.utils.Util.getDateFormat;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 25-02-2017.
 */

public class ViewFuelsheet extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mDate;
    EditText mName, mTruckNo, mPlace, mDiseal, mOil, mCurrentKm, mPreviousKM, mMileage;
    Button mSave;
    View mFuelsheetView;
    FuelSheetModel mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra(Constants.FUEL_SHEET, -1) == 0) {
            mFuelsheetView = getLayoutInflater().inflate(R.layout.activity_edit_fuel_sheet, null);
            setContentView(mFuelsheetView);
            initEditWidgets();
        } else {
            mFuelsheetView = getLayoutInflater().inflate(R.layout.activity_view_fuel_sheet, null);
            setContentView(mFuelsheetView);
            initWidgets();
        }


        mData = (FuelSheetModel) getIntent().getSerializableExtra(Constants.FUEL_SHEET_DATA);
        if (mData != null) {
            String date = getDateFormat(mData.getFdate());
            mDate.setText(date);
            mName.setText(mData.getFirstName() + " " + mData.getLastName());
            mPlace.setText(mData.getPlace());
            mDiseal.setText(mData.getPlace());
            mCurrentKm.setText(mData.getCurKm());
            mPreviousKM.setText(mData.getPreKm());
            mOil.setText(mData.getOil());
            mMileage.setText("");
            mTruckNo.setText(mData.getTruckNo());
        }

    }

    private void initEditWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_edit_fuel_title));
        mToolbar.setTitleTextColor(Color.WHITE);


        mDate = (TextView) mFuelsheetView.findViewById(R.id.AEFS_TV_date);
        mName = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_name);
        mPlace = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_location);
        mDiseal = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_diseal);
        mOil = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_oil);
        mCurrentKm = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_current_km);
        mPreviousKM = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_previous_km);
        mTruckNo = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_truck_no);
        mMileage = (EditText) mFuelsheetView.findViewById(R.id.AEFS_ET_mileage);
        mSave = (Button) mFuelsheetView.findViewById(R.id.AEFS_BT_save);
        mDate.setOnClickListener(this);
        mSave.setOnClickListener(this);
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_view_fuel_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mDate = (TextView) mFuelsheetView.findViewById(R.id.AVFS_TV_date);
        mName = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_name);
        mPlace = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_location);
        mDiseal = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_diseal);
        mOil = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_oil);
        mCurrentKm = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_current_km);
        mPreviousKM = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_previous_km);
        mTruckNo = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_truck_no);
        mMileage = (EditText) mFuelsheetView.findViewById(R.id.AVFS_ET_mileage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AEFS_BT_save) {
            if (mDate.getText().toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.err_start_date), Toast.LENGTH_SHORT).show();
            } else if (mName.getText().toString().isEmpty()) {
                mName.setError(getString(R.string.err_first_name));
                mName.requestFocus();
            } else if (mPlace.getText().toString().isEmpty()) {
                mPlace.setError(getString(R.string.err_location));
                mPlace.requestFocus();
            } else if (mTruckNo.getText().toString().isEmpty()) {
                mTruckNo.setError(getString(R.string.err_truck_no));
                mTruckNo.requestFocus();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {

            }
        } else if (v.getId() == R.id.AEFS_TV_date) {
            Constants.AdminMenu.getFromDate(ViewFuelsheet.this, mDate);
        }
    }
}
