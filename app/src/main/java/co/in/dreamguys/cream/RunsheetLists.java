package co.in.dreamguys.cream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import co.in.dreamguys.cream.adapter.RunsheetAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.RunsheetAPI;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 13-03-2017.
 */

public class RunsheetLists extends AppCompatActivity implements SearchListViewNotify {
    Toolbar mToolbar;
    ListView mRunsheetWidgets;
    RunsheetAPI.Datum mData;
    RunsheetAdapter aRunsheetAdapter;
    String selectedDay = "", selectedId = "";
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = RunsheetLists.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runsheet_lists);
        Constants.eRUNSHEET = RunsheetLists.this;
        selectedDay = getIntent().getStringExtra(Constants.WEEK_DAY);
        selectedId = getIntent().getStringExtra(Constants.LOCATION);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        if (!selectedDay.isEmpty() && !selectedId.isEmpty()) {
            getRunsheets();
        }


    }

    private void getRunsheets() {
        if (!isNetworkAvailable(this)) {
            Snackbar.make(findViewById(R.id.ARS_LL_parent), getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<RunsheetAPI.RunsheetResponse> loginCall = apiService.getRunsheets(selectedDay, selectedId);
            loginCall.enqueue(new Callback<RunsheetAPI.RunsheetResponse>() {
                @Override
                public void onResponse(Call<RunsheetAPI.RunsheetResponse> call, Response<RunsheetAPI.RunsheetResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        mRunsheetWidgets.setAdapter(null);
                        aRunsheetAdapter = new RunsheetAdapter(RunsheetLists.this, response.body().getData());
                        mRunsheetWidgets.setAdapter(aRunsheetAdapter);
                        aRunsheetAdapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(findViewById(R.id.ARS_LL_parent), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RunsheetAPI.RunsheetResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_runsheet_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mRunsheetWidgets = (ListView) findViewById(R.id.ARL_LV_runsheets);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_users, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.MAU_add_new_users) {
            Intent mCallnRuncsheet = new Intent(this, AddNewRunsheet.class);
            mCallnRuncsheet.putExtra(Constants.LOCATION, selectedId);
            startActivity(mCallnRuncsheet);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void searchNotify() {
        if (!selectedDay.isEmpty() && !selectedId.isEmpty()) {
            getRunsheets();
        }
    }
}
