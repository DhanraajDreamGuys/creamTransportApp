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

import co.in.dreamguys.cream.adapter.DetailLogAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DetailLogAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 13-03-2017.
 */

public class DetailDriverLog extends AppCompatActivity {
    Toolbar mToolbar;
    ListView mDriverLogWidgets;
    CustomProgressDialog mCustomProgressDialog;
    DetailLogAdapter aDetailLogAdapter;
    String userID = "";
    public static String TAG = DetailDriverLog.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_driver_hours);
        userID = getIntent().getStringExtra(Constants.ID);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        getDetailLog();
    }

    private void getDetailLog() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(DetailDriverLog.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<DetailLogAPI.DetailLogResponse> loginCall = apiService.getDetailDriverHours(userID);

            loginCall.enqueue(new Callback<DetailLogAPI.DetailLogResponse>() {
                @Override
                public void onResponse(Call<DetailLogAPI.DetailLogResponse> call, Response<DetailLogAPI.DetailLogResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aDetailLogAdapter = new DetailLogAdapter(DetailDriverLog.this, response.body().getData());
                        mDriverLogWidgets.setAdapter(aDetailLogAdapter);
                        aDetailLogAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DetailDriverLog.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DetailLogAPI.DetailLogResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_detail_driver_log_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mDriverLogWidgets = (ListView) findViewById(R.id.ADDH_LV_detail_driver_log);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
