package co.in.dreamguys.cream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.Serializable;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DeleteSheetAPI;
import co.in.dreamguys.cream.apis.TripListAPI;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.interfaces.TripsheetInterface;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 17-02-2017.
 */

public class Trips extends AppCompatActivity implements TripsheetInterface,SearchListViewNotify {
    Toolbar mToolbar;
    PopupWindow mPopsearch;
    ListView mTripWidget;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = Trips.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        mPopsearch = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
        getCurrentTrips();

    }

    private void getCurrentTrips() {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(Trips.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<TripListAPI.TripsResponse> loginCall = apiService.getCurrentTrips();
            loginCall.enqueue(new Callback<TripListAPI.TripsResponse>() {
                @Override
                public void onResponse(Call<TripListAPI.TripsResponse> call, Response<TripListAPI.TripsResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Util.fillTripData(Trips.this, response.body().getData(), mTripWidget);
                    } else {
                        Toast.makeText(Trips.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TripListAPI.TripsResponse> call, Throwable t) {
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

        mTripWidget = (ListView) findViewById(R.id.AT_LV_triplist);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_trips, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.MAT_add_new_trip) {
            ActivityConstants.callPage(this, AddNewTrip.class);
        } else if (item.getItemId() == R.id.MAT_search_trip) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT
            );
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            if (!mPopsearch.isShowing()) {
                Util.searchPopUpWindow(Trips.this, mPopsearch, Constants.TRIPS, getLayoutInflater(), mTripWidget);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mTripWidget.setLayoutParams(layoutParams);
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPopsearch.dismiss();
    }

    @Override
    public void view(String id, final int viewType) {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TripListAPI.TripsResponse> repairsheet = apiService.getViewTripSheet(id);
        repairsheet.enqueue(new Callback<TripListAPI.TripsResponse>() {
            @Override
            public void onResponse(Call<TripListAPI.TripsResponse> call, Response<TripListAPI.TripsResponse> response) {
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    Intent mCallViewPaysheet = new Intent(Trips.this, ViewTripsheet.class);
                    mCallViewPaysheet.putExtra(Constants.TRIPSHEETDETAILS, (Serializable) response.body().getData().get(0));
                    mCallViewPaysheet.putExtra(Constants.MODE, viewType);  // Mode 0 means edit and 1 means view
                    startActivity(mCallViewPaysheet);
                } else {
                    Toast.makeText(Trips.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                mCustomProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TripListAPI.TripsResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                mCustomProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void delete(String delete_id, final int position) {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteSheetAPI.DeleteTripsResponse> repairsheet = apiService.getDeleteTrip(delete_id);
        repairsheet.enqueue(new Callback<DeleteSheetAPI.DeleteTripsResponse>() {
            @Override
            public void onResponse(Call<DeleteSheetAPI.DeleteTripsResponse> call, Response<DeleteSheetAPI.DeleteTripsResponse> response) {
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    Toast.makeText(Trips.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Util.mTripReport.getData().remove(position);
                    Util.aTripAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Trips.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                mCustomProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DeleteSheetAPI.DeleteTripsResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                mCustomProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void searchNotify() {
        getCurrentTrips();
    }
}
