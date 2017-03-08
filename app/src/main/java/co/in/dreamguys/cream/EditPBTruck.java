package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.PBTruckAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 06-03-2017.
 */

public class EditPBTruck extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    public static String TAG = EditPBTruck.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    PBTruckAPI.Datum mData;
    EditText mTruckNo, mPhoneNo;
    Button mSave;
    SwitchCompat mStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_edit_phonebook);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        mData = (PBTruckAPI.Datum) getIntent().getSerializableExtra(Constants.PB_TRUCK_DATA);
        if (mData != null) {
            mTruckNo.setText(mData.getTruck_no());
            mPhoneNo.setText(mData.getPhone_no());

            if (mData.getStatus().equalsIgnoreCase("0")) {
                mStatus.setChecked(true);
                mStatus.setText(getString(R.string.active));
            } else {
                mStatus.setChecked(false);
                mStatus.setText(getString(R.string.inactive));
            }
        }

        mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callStatusApi(true);
                } else {
                    callStatusApi(false);
                }
            }
        });

    }

    private void editPBTruck() {
        if (mTruckNo.getText().toString().isEmpty()) {
            mTruckNo.setError(getString(R.string.err_truck_no));
            mTruckNo.requestFocus();
        } else if (mPhoneNo.getText().toString().isEmpty()) {
            mPhoneNo.setError(getString(R.string.err_phone));
            mPhoneNo.requestFocus();
        } else if (!isNetworkAvailable(this)) {
            Toast.makeText(EditPBTruck.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.editPBTrucks(sendValueWithRetrofit());
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Toast.makeText(EditPBTruck.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditPBTruck.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_edit_pb_truck_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mTruckNo = (EditText) findViewById(R.id.ATEPB_ET_truckno);
        mPhoneNo = (EditText) findViewById(R.id.ATEPB_ET_phoneno);
        mSave = (Button) findViewById(R.id.ATEPB_BT_save);
        mStatus = (SwitchCompat) findViewById(R.id.ATEPB_BT_status);

        mSave.setOnClickListener(this);

    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_ID, mData.getId());
        params.put(Constants.PARAMS_TRUCK_ID, mTruckNo.getText().toString());
        params.put(Constants.PARAMS_PHONE_NO, mPhoneNo.getText().toString());
        return params;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mSave.getId()) {
            editPBTruck();
        }
    }

    private void callStatusApi(boolean status) {
        String value = "";
        if (status) {
            value = "0";
        } else {
            value = "1";
        }

        if (!isNetworkAvailable(this)) {
            Toast.makeText(EditPBTruck.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.editStatus(mData.getId(), mData.getTruck_no(), value);
            final String finalValue = value;
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        if (finalValue.equalsIgnoreCase("0")) {
                            mStatus.setText(getString(R.string.active));
                        } else {
                            mStatus.setText(getString(R.string.inactive));
                        }
                        Toast.makeText(EditPBTruck.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPBTruck.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
