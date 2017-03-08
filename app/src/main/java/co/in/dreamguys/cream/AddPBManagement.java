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

public class AddPBManagement extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    public static String TAG = AddPBManagement.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    PBWorkShopAPI.Datum mData;
    Button mAdd;
    EditText mManager, mName, mAddress, mPhoneNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pb_management);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_add_pb_workshop_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (EditText) findViewById(R.id.AAPBM_ET_name);
        mAddress = (EditText) findViewById(R.id.AAPBM_ET_address);
        mPhoneNo = (EditText) findViewById(R.id.AAPBM_ET_phoneno);
        mManager = (EditText) findViewById(R.id.AAPBM_ET_position);
        mAdd = (Button) findViewById(R.id.AAPBM_BT_save);
        mAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mAdd.getId()) {
            addPhoneBookWorkshop();
        }
    }

    private void addPhoneBookWorkshop() {
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
            mManager.setError(getString(R.string.err_position));
            mManager.requestFocus();
        } else if (!isNetworkAvailable(this)) {
            Toast.makeText(AddPBManagement.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.addPBManagement(sendValueWithRetrofit());
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Toast.makeText(AddPBManagement.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddPBManagement.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_LINK_NAME, mName.getText().toString());
        params.put(Constants.PARAMS_PHONE_NO, mPhoneNo.getText().toString());
        params.put(Constants.PARAMS_ADDRESS, mAddress.getText().toString());
        params.put(Constants.PARAMS_MANAGER, mManager.getText().toString());
        return params;
    }
}
