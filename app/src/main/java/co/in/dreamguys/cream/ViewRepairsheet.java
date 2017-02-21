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

import co.in.dreamguys.cream.apis.RepairsheetCurrentDayAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

/**
 * Created by user5 on 17-02-2017.
 */

public class ViewRepairsheet extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    EditText mComments, mName, mDate, mTruckNo, mDollyNo, mRegno, mRegno1, mFaultRepair;
    Button mButton;
    RepairsheetCurrentDayAPI.Datum mRepairSheetData;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = ViewRepairsheet.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_repair_sheet);
        mCustomProgressDialog = new CustomProgressDialog(this);
        Constants.ViewRepairsheet = this;
        initWidgets();

        mRepairSheetData = (RepairsheetCurrentDayAPI.Datum) getIntent().getSerializableExtra(Constants.REPAIRSHEETDETAILS);

        mName.setText(mRepairSheetData.getFirst_name() + " " + mRepairSheetData.getLast_name());
        mDate.setText(mRepairSheetData.getRdate());
        mTruckNo.setText(mRepairSheetData.getTruck_no());
        mRegno.setText(mRepairSheetData.getRegn_no());
        mRegno1.setText(mRepairSheetData.getRegn1_no());
        mDollyNo.setText(mRepairSheetData.getDolly_no());
        mFaultRepair.setText(mRepairSheetData.getFaults());
        mComments.setText(mRepairSheetData.getComments());

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mName = (EditText) findViewById(R.id.AVRS_ET_name);
        mDate = (EditText) findViewById(R.id.AVRS_ET_date);
        mTruckNo = (EditText) findViewById(R.id.AVRS_ET_truck_no);
        mRegno = (EditText) findViewById(R.id.AVRS_ET_Trego_no);
        mDollyNo = (EditText) findViewById(R.id.AVRS_ET_dolly_no);
        mRegno1 = (EditText) findViewById(R.id.AVRS_ET_Drego_no);
        mFaultRepair = (EditText) findViewById(R.id.AVRS_ET_faults_repair);
        mComments = (EditText) findViewById(R.id.AVRS_ET_workshop_comments);
        mButton = (Button) findViewById(R.id.AVRS_BT_save);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mToolbar.setTitle(getString(R.string.tool_edit_repairsheet_title));
            mComments.setFocusable(true);
            mComments.setClickable(true);
            mComments.setFocusableInTouchMode(true);
            mButton.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setTitle(getString(R.string.tool_view_repairsheet_title));
            mComments.setFocusable(false);
            mComments.setClickable(false);
            mComments.setFocusableInTouchMode(false);
        }

        mToolbar.setTitleTextColor(Color.WHITE);
        mButton.setOnClickListener(this);
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
        if (v.getId() == R.id.AVRS_BT_save) {
            Constants.Repairsheet.updateRepairSheet(mRepairSheetData.getRid(), mComments.getText().toString());
        }
    }
}
