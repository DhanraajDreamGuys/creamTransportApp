package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.AddTripAPI;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 11-02-2017.
 */

public class AddNewTrip extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Toolbar mToolbar;
    TextView mChooseDriver, mFrom, mTo, mDate, mSetDate;
    EditText mTruckNo, mTrailers, mManifestNo, mDollyNo, mLoadavailable, mFromWho, mLoadDue, mBy, mChangeOver, mDriver, mComments;
    CheckBox mExpress, mNoDanger, mHasDanger, mProductMarket;
    Button mSend, mCancel;
    String checkedExpress = "", checkedNodanger = "", checkedHasDanger = "", checkedProduct = "", checkedItems = "";
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = AddNewTrip.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trips);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_trips_title));
        mToolbar.setTitleTextColor(Color.WHITE);


        mChooseDriver = (TextView) findViewById(R.id.ANT_TV_choose_driver);
        mChooseDriver.setOnClickListener(this);
        mFrom = (TextView) findViewById(R.id.ANT_TV_from);
        mFrom.setOnClickListener(this);
        mTo = (TextView) findViewById(R.id.ANT_TV_to);
        mTo.setOnClickListener(this);
        mDate = (TextView) findViewById(R.id.ANT_TV_date);
        mDate.setOnClickListener(this);
        mSetDate = (TextView) findViewById(R.id.ANT_TV_set_date);
        mSetDate.setOnClickListener(this);
        mTruckNo = (EditText) findViewById(R.id.ANT_ET_truck);
        mTrailers = (EditText) findViewById(R.id.ANT_ET_traliers);
        mManifestNo = (EditText) findViewById(R.id.ANT_ET_manifest);
        mDollyNo = (EditText) findViewById(R.id.ANT_ET_dolly);
        mLoadavailable = (EditText) findViewById(R.id.ANT_ET_load_availble_after);
        mLoadDue = (EditText) findViewById(R.id.ANT_ET_load_due_in);
        mFromWho = (EditText) findViewById(R.id.ANT_ET_from);
        mBy = (EditText) findViewById(R.id.ANT_ET_by);
        mChangeOver = (EditText) findViewById(R.id.ANT_ET_change_over);
        mDriver = (EditText) findViewById(R.id.ANT_ET_driver);
        mComments = (EditText) findViewById(R.id.ANT_ET_comment);
        mSend = (Button) findViewById(R.id.ANT_BT_send);
        mCancel = (Button) findViewById(R.id.ANT_BT_cancel);
        mSend.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        mExpress = (CheckBox) findViewById(R.id.ANT_CB_exp_general);
        mExpress.setOnCheckedChangeListener(this);
        mNoDanger = (CheckBox) findViewById(R.id.ANT_CB_no_danger_goods);
        mNoDanger.setOnCheckedChangeListener(this);
        mHasDanger = (CheckBox) findViewById(R.id.ANT_CB_has_danger_goods);
        mHasDanger.setOnCheckedChangeListener(this);
        mProductMarket = (CheckBox) findViewById(R.id.ANT_CB_product_market);
        mProductMarket.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ANT_TV_choose_driver) {
            Constants.AdminMenu.getDrivers(AddNewTrip.this, mChooseDriver);
        } else if (v.getId() == R.id.ANT_TV_from) {
            Constants.AdminMenu.getLocations(AddNewTrip.this, mFrom, "From");
        } else if (v.getId() == R.id.ANT_TV_to) {
            Constants.AdminMenu.getLocations(AddNewTrip.this, mTo, "To");
        } else if (v.getId() == R.id.ANT_TV_date) {
            Constants.AdminMenu.getFromDate(AddNewTrip.this, mDate);
        } else if (v.getId() == R.id.ANT_TV_set_date) {
            Constants.AdminMenu.getFromDate(AddNewTrip.this, mSetDate);
        } else if (v.getId() == R.id.ANT_BT_send) {
            addNewTrip();
        } else if (v.getId() == R.id.ANT_BT_cancel) {
            finish();
        }
    }

    private void addNewTrip() {
        if (mFrom.getText().toString().isEmpty()) {
            mFrom.setError(getString(R.string.err_from));
            mFrom.requestFocus();
        } else if (mTo.getText().toString().isEmpty()) {
            mTo.setError(getString(R.string.err_to));
            mTo.requestFocus();
        } else if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(AddNewTrip.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<AddTripAPI.AddTripResponse> loginCall = apiService.addNewTrip(sendValueWithRetrofit());
            loginCall.enqueue(new Callback<AddTripAPI.AddTripResponse>() {
                @Override
                public void onResponse(Call<AddTripAPI.AddTripResponse> call, Response<AddTripAPI.AddTripResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Toast.makeText(AddNewTrip.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddNewTrip.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddTripAPI.AddTripResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ANT_CB_exp_general) {
            if (isChecked) {
                checkedExpress = buttonView.getText().toString() + ",";
            } else {
                checkedExpress = "";
            }
        } else if (buttonView.getId() == R.id.ANT_CB_has_danger_goods) {
            if (isChecked) {
                checkedHasDanger = buttonView.getText().toString() + ",";
            } else {
                checkedHasDanger = "";
            }
        } else if (buttonView.getId() == R.id.ANT_CB_no_danger_goods) {
            if (isChecked) {
                checkedNodanger = buttonView.getText().toString() + ",";
            } else {
                checkedNodanger = "";
            }
        } else if (buttonView.getId() == R.id.ANT_CB_product_market) {
            if (isChecked) {
                checkedProduct = buttonView.getText().toString() + ",";
            } else {
                checkedProduct = "";
            }
        }
        checkedItems = checkedExpress + checkedNodanger + checkedHasDanger + checkedProduct;
        Log.i(TAG, checkedItems.trim());
    }


    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_DRIVER_ID, Constants.driverList.get(Util.adapterPosition).getId());
        params.put(Constants.PARAMS_TRUCK_NO, mTruckNo.getText().toString());
        params.put(Constants.PARAMS_TRAILERS, mTrailers.getText().toString());
        params.put(Constants.PARAMS_MNO, mManifestNo.getText().toString());
        params.put(Constants.PARAMS_DOLLYNO, mDollyNo.getText().toString());
        params.put(Constants.PARAMS_LTIME, mLoadavailable.getText().toString());
        params.put(Constants.PARAMS_LFROM, mFromWho.getText().toString());
        params.put(Constants.PARAMS_LDUE, mLoadDue.getText().toString());
        params.put(Constants.PARAMS_IDFROM, mBy.getText().toString());
        params.put(Constants.PARAMS_CTRUCK, mChangeOver.getText().toString());
        params.put(Constants.PARAMS_CDRIVER, mDriver.getText().toString());
        params.put(Constants.PARAMS_ITYPE, checkedItems);
        params.put(Constants.PARAMS_SDATE, mSetDate.getText().toString());
        params.put(Constants.PARAMS_FROM, Constants.countries.get(Constants.From).getId());
        params.put(Constants.PARAMS_TRIP_TO, Constants.countries.get(Constants.To).getId());
        params.put(Constants.PARAMS_ADMIN_COMMENT, mComments.getText().toString());
        return params;
    }
}
