package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import co.in.dreamguys.cream.adapter.TripHoursAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.TripHoursAPI;
import co.in.dreamguys.cream.interfaces.FridgeCodeTypeInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.ActivityConstants.callPage;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 28-02-2017.
 */

public class TripHours extends AppCompatActivity implements FridgeCodeTypeInterface {
    Toolbar mToolbar;
    ListView mTripHoursWidget;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = TripHours.class.getName();
    TripHoursAdapter aTripHoursAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_hours);
        Constants.TRIPHOURS = this;
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
        getTripHours();
    }

    private void getTripHours() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(TripHours.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<TripHoursAPI.TripHoursResponse> loginCall = apiService.getTripHoursLists();
            loginCall.enqueue(new Callback<TripHoursAPI.TripHoursResponse>() {
                @Override
                public void onResponse(Call<TripHoursAPI.TripHoursResponse> call, Response<TripHoursAPI.TripHoursResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        if (response.body().getData().size() > 0) {
                            if (mTripHoursWidget != null) {
                                mTripHoursWidget.setAdapter(null);
                                aTripHoursAdapter = new TripHoursAdapter(TripHours.this, response.body().getData());
                                mTripHoursWidget.setAdapter(aTripHoursAdapter);
                                aTripHoursAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(TripHours.this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TripHours.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TripHoursAPI.TripHoursResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_trips_hours_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mTripHoursWidget = (ListView) findViewById(R.id.ATH_LV_tripHours);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_trips_hours, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_add_trip_hours) {
            callPage(TripHours.this, AddTripHours.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void typeSearch(String type) {

    }

    @Override
    public void refresh() {
        getTripHours();
    }
}
