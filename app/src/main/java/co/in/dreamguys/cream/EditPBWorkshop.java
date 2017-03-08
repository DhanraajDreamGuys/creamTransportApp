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
import co.in.dreamguys.cream.apis.PBWorkShopAPI;
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

public class EditPBWorkshop extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    public static String TAG = EditPBWorkshop.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    PBWorkShopAPI.Datum mData;
    Button mSave;
    SwitchCompat mStatus;
    EditText mManager, mName, mAddress, mPhoneNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_edit_phonebook);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        mData = (PBWorkShopAPI.Datum) getIntent().getSerializableExtra(Constants.PB_WORKSHOP_DATA);
        if (mData != null) {
            mName.setText(mData.getName());
            mPhoneNo.setText(mData.getPhone_no());
            mAddress.setText(mData.getAddress());
            mManager.setText(mData.getManager());

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
        if (mName.getText().toString().isEmpty()) {
            mName.setError(getString(R.string.str_entr_name));
            mName.requestFocus();
        } else if (mPhoneNo.getText().toString().isEmpty()) {
            mPhoneNo.setError(getString(R.string.err_phone));
            mPhoneNo.requestFocus();
        } else if (mAddress.getText().toString().isEmpty()) {
            mAddress.setError(getString(R.string.err_address));
            mAddress.requestFocus();
        } else if (mManager.getText().toString().isEmpty()) {
            mManager.setError(getString(R.string.err_manager));
            mManager.requestFocus();
        } else if (!isNetworkAvailable(this)) {
            Toast.makeText(EditPBWorkshop.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.editPBWorkshop(sendValueWithRetrofit());
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Toast.makeText(EditPBWorkshop.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditPBWorkshop.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        mToolbar.setTitle(getString(R.string.toolbar_edit_pb_workshop_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (EditText) findViewById(R.id.AWEPB_ET_name);
        mAddress = (EditText) findViewById(R.id.AWEPB_ET_address);
        mPhoneNo = (EditText) findViewById(R.id.AWEPB_ET_phoneno);
        mManager = (EditText) findViewById(R.id.AWEPB_ET_managername);
        mStatus = (SwitchCompat) findViewById(R.id.AWEPB_SC_status);
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_ID, mData.getId());
        params.put(Constants.PARAMS_LINK_NAME, mName.getText().toString());
        params.put(Constants.PARAMS_PHONE_NO, mPhoneNo.getText().toString());
        params.put(Constants.PARAMS_ADDRESS, mAddress.getText().toString());
        params.put(Constants.PARAMS_MANAGER, mManager.getText().toString());
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
            Toast.makeText(EditPBWorkshop.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.editWorkshop(mData.getId(), value);
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
                        Toast.makeText(EditPBWorkshop.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPBWorkshop.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
