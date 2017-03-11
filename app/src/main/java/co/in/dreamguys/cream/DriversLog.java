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

import co.in.dreamguys.cream.adapter.DriverLogAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DriverHoursAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 11-02-2017.
 */

public class DriversLog extends AppCompatActivity {
    Toolbar mToolbar;
    ListView mDriverLogWidgets;
    CustomProgressDialog mCustomProgressDialog;
    DriverLogAdapter aDriverLogAdapter;
    public static String TAG = DriversLog.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_hours);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        currentDayDriverHours();

    }

    private void currentDayDriverHours() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(DriversLog.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<DriverHoursAPI.DriverHoursResponse> loginCall = apiService.getDriverHours();

            loginCall.enqueue(new Callback<DriverHoursAPI.DriverHoursResponse>() {
                @Override
                public void onResponse(Call<DriverHoursAPI.DriverHoursResponse> call, Response<DriverHoursAPI.DriverHoursResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aDriverLogAdapter = new DriverLogAdapter(DriversLog.this, response.body().getData());
                        mDriverLogWidgets.setAdapter(aDriverLogAdapter);
                        aDriverLogAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DriversLog.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DriverHoursAPI.DriverHoursResponse> call, Throwable t) {
                    mCustomProgressDialog.dismiss();
                    Log.i(TAG, t.getMessage());
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
        mToolbar.setTitle(getString(R.string.tool_driver_log_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mDriverLogWidgets = (ListView) findViewById(R.id.ADH_LV_driver_log);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
