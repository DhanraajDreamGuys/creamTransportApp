package co.in.dreamguys.cream;

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

import co.in.dreamguys.cream.adapter.AccidentReportAdapter;
import co.in.dreamguys.cream.apis.AccidentReportAPI;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Constants.ACCIDENT_REPORT;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.searchPopUpWindow;


/**
 * Created by user5 on 11-02-2017.
 */

public class Accidentreport extends AppCompatActivity implements SearchListViewNotify {
    Toolbar mToolbar;
    PopupWindow popupSearch;
    ListView mAccidentListWidgets;
    AccidentReportAdapter aAccidentReportAdapter;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Accidentreport.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_report);
        popupSearch = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
        getAccidentReports();

    }

    private void getAccidentReports() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(Accidentreport.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<AccidentReportAPI.AccidentReportResponse> loginCall = apiService.getAccidentResports();

            loginCall.enqueue(new Callback<AccidentReportAPI.AccidentReportResponse>() {
                @Override
                public void onResponse(Call<AccidentReportAPI.AccidentReportResponse> call, Response<AccidentReportAPI.AccidentReportResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aAccidentReportAdapter = new AccidentReportAdapter(Accidentreport.this, response.body().getData());
                        mAccidentListWidgets.setAdapter(aAccidentReportAdapter);
                        aAccidentReportAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Accidentreport.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AccidentReportAPI.AccidentReportResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_accident_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mAccidentListWidgets = (ListView) findViewById(R.id.AAR_LV_accident_report);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accident_report, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.MAR_search_accident_report) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT
            );
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            if (!popupSearch.isShowing()) {
                searchPopUpWindow(Accidentreport.this, popupSearch, ACCIDENT_REPORT, getLayoutInflater(), mAccidentListWidgets);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mAccidentListWidgets.setLayoutParams(layoutParams);
            }
        }
        return true;
    }

    @Override
    public void searchNotify() {
        getAccidentReports();
    }
}
