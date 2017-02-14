package co.in.dreamguys.cream;

import android.content.Intent;
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

import co.in.dreamguys.cream.apis.AccountAPI;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.SessionHandler;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 14-02-2017.
 */

public class Account extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    EditText mFirstname, mLastname, mEmailAddress, mUserType;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Account.class.getName();
    Button mUpdateAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mCustomProgressDialog = new CustomProgressDialog(Account.this);
        initWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAccountData();
    }

    private void getAccountData() {

        if (Util.isNetworkAvailable(Account.this)) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<AccountAPI.AccountResponse> loginCall = apiService.getAccount(SessionHandler.getStringPref(Constants.USER_ID));
            mCustomProgressDialog.showDialog();
            loginCall.enqueue(new Callback<AccountAPI.AccountResponse>() {
                @Override
                public void onResponse(Call<AccountAPI.AccountResponse> call, Response<AccountAPI.AccountResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        mFirstname.setText(response.body().getData().get(0).getFirst_name());
                        mLastname.setText(response.body().getData().get(0).getLast_name());
                        mEmailAddress.setText(response.body().getData().get(0).getEmail());
                        if (response.body().getData().get(0).getUser_type().equals(1)) {
                            mUserType.setText(getString(R.string.str_administrator));
                        }
                    } else {
                        Toast.makeText(Account.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AccountAPI.AccountResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_account_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mFirstname = (EditText) findViewById(R.id.AA_ET_firstname);
        mLastname = (EditText) findViewById(R.id.AA_ET_lastname);
        mEmailAddress = (EditText) findViewById(R.id.AA_ET_email);
        mUserType = (EditText) findViewById(R.id.AA_ET_userType);
        mUpdateAccount = (Button) findViewById(R.id.AA_BT_update);
        mUpdateAccount.setOnClickListener(this);
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
        if (v.getId() == R.id.AA_BT_update) {
            Intent mCallEditAccount = new Intent(Account.this, EditAccount.class);
            mCallEditAccount.putExtra(Constants.FIRSTNAME, mFirstname.getText().toString());
            mCallEditAccount.putExtra(Constants.LASTNAME, mLastname.getText().toString());
            mCallEditAccount.putExtra(Constants.EMAIL, mEmailAddress.getText().toString());
            mCallEditAccount.putExtra(Constants.USERTYPE, mUserType.getText().toString());
            startActivity(mCallEditAccount);
        }
    }
}
