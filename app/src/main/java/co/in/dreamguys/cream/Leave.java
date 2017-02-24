package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import co.in.dreamguys.cream.adapter.LeaveListAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.LeaveAPI;
import co.in.dreamguys.cream.interfaces.LeaveListInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 23-02-2017.
 */

public class Leave extends AppCompatActivity implements LeaveListInterface {

    Toolbar mToolbar;
    ListView mLeaveListWidgets;
    LeaveListAdapter aLeaveListAdapter;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = Leave.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        Constants.LEAVEFORM = this;
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        getLeaveLists();
    }

    public void getLeaveLists() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(Leave.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<LeaveAPI.LeaveListResponse> loginCall = apiService.getLeaveLists();
            loginCall.enqueue(new Callback<LeaveAPI.LeaveListResponse>() {
                @Override
                public void onResponse(Call<LeaveAPI.LeaveListResponse> call, Response<LeaveAPI.LeaveListResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        fillLeaveForm(response.body().getData());
                    } else {
                        Toast.makeText(Leave.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LeaveAPI.LeaveListResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    private void fillLeaveForm(List<LeaveAPI.Datum> data) {
        aLeaveListAdapter = new LeaveListAdapter(Leave.this, data);
        mLeaveListWidgets.setAdapter(aLeaveListAdapter);
        aLeaveListAdapter.notifyDataSetChanged();
    }


    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_leave_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mLeaveListWidgets = (ListView) findViewById(R.id.AL_LV_leave_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyLeaveList() {
        getLeaveLists();
    }
}
