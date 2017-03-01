package co.in.dreamguys.cream;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.apis.UserstatuslistAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 01-03-2017.
 */

public class EditUserStatus extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mName, mReason;
    Button mStatusUpdated;
    UserstatuslistAPI.User_list mData;
    String status = "", statusString = "";
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = EditUserStatus.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_status);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        mData = (UserstatuslistAPI.User_list) getIntent().getSerializableExtra(Constants.USER_STATUS_DATA);
        if (mData != null) {
            mName.setText(mData.getFirst_name() + " " + mData.getLast_name());
            mReason.setText(mData.getReason());
            if (mData.getWorking_sts().equalsIgnoreCase("on")) {
                mStatusUpdated.setText(getString(R.string.active));
                mStatusUpdated.setBackgroundColor(ContextCompat.getColor(this, R.color.accept_color));
                statusString = "on";
            } else {
                mStatusUpdated.setText(getString(R.string.inactive));
                mStatusUpdated.setBackgroundColor(ContextCompat.getColor(this, R.color.inactive_color));
                statusString = "off";
            }
        }


        mStatusUpdated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReason.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserStatus.this, getString(R.string.no_reason), Toast.LENGTH_SHORT).show();
                } else {
                    if (statusString.equalsIgnoreCase("on")) {
                        callStatusApi(false);
                        mStatusUpdated.setBackgroundColor(ContextCompat.getColor(EditUserStatus.this, R.color.inactive_color));
                    } else {
                        callStatusApi(true);
                        mStatusUpdated.setBackgroundColor(ContextCompat.getColor(EditUserStatus.this, R.color.accept_color));
                    }
                }
            }
        });
    }


    private void callStatusApi(final boolean status) {
        if (status) {
            statusString = "on";
        } else {
            statusString = "off";
        }


        if (!isNetworkAvailable(this)) {
            Toast.makeText(EditUserStatus.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.updateUserStatus(sendValueWithRetrofit());
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        if (statusString.equalsIgnoreCase("on")) {
                            mStatusUpdated.setText(getString(R.string.active));
                            mStatusUpdated.setBackgroundColor(ContextCompat.getColor(EditUserStatus.this, R.color.accept_color));
                        } else {
                            mStatusUpdated.setText(getString(R.string.inactive));
                            mStatusUpdated.setBackgroundColor(ContextCompat.getColor(EditUserStatus.this, R.color.inactive_color));
                        }
                        Toast.makeText(EditUserStatus.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Constants.USER_STATUS.refresh();
                        finish();
                    } else {
                        Toast.makeText(EditUserStatus.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure
                        (Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.edit_user_status_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (TextView) findViewById(R.id.AEUS_TV_name);
        mReason = (TextView) findViewById(R.id.AEUS_TV_reason);
        mStatusUpdated = (Button) findViewById(R.id.AEUS_TB_status_update);

        mReason.setOnClickListener(this);

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
        if (v.getId() == mReason.getId()) {
            statusDialog();
        }
    }

    private void statusDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditUserStatus.this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditUserStatus.this, android.R.layout.select_dialog_singlechoice);

        for (UserstatuslistAPI.Statu status : Constants.USER_STATUS_LISTS) {
            arrayAdapter.add(status.getName());
        }
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mReason.setText(arrayAdapter.getItem(which));
                dialog.dismiss();
            }
        });
        builderSingle.show();

    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_UID, mData.getId());
        params.put(Constants.PARAMS_REASON, mReason.getText().toString());
        params.put(Constants.PARAMS_ONOFF, statusString);
        return params;
    }
}
