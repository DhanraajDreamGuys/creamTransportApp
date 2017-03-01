package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import co.in.dreamguys.cream.adapter.UserStatusAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UserstatuslistAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 28-02-2017.
 */

public class UserStatus extends AppCompatActivity {
    Toolbar mToolbar;
    ListView mUserStatusWidget;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = UserStatus.class.getName();
    UserStatusAdapter aUserStatusAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status_list);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
        getUserStatusList();
    }

    private void getUserStatusList() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(UserStatus.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UserstatuslistAPI.UserStatusListResponse> loginCall = apiService.getUserStatusLists();
            loginCall.enqueue(new Callback<UserstatuslistAPI.UserStatusListResponse>() {
                @Override
                public void onResponse(Call<UserstatuslistAPI.UserStatusListResponse> call, Response<UserstatuslistAPI.UserStatusListResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aUserStatusAdapter = new UserStatusAdapter(UserStatus.this, response.body().getData().getUser_list());
                        mUserStatusWidget.setAdapter(aUserStatusAdapter);
                    } else {
                        Toast.makeText(UserStatus.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserstatuslistAPI.UserStatusListResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_edit_trips_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mUserStatusWidget = (ListView) findViewById(R.id.AUSL_LV_user_status);
    }

}
