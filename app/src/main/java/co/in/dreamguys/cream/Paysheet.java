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

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.adapter.PaysheetWeeklyAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.PaysheetLastWeekAPI;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.model.PaysheetReport;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 14-02-2017.
 */

public class Paysheet extends AppCompatActivity {
    Toolbar mToolbar;
    PopupWindow popupSearch;
    Data mData;
    PaysheetReport mPaysheetReport = new PaysheetReport();
    CustomProgressDialog mCustomProgressDialog;
    List<Data> mPaysheetData;
    private static String TAG = Paysheet.class.getName();
    PaysheetWeeklyAdapter aPaysheetWeeklyAdapter;
    ListView mPaysheetView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysheet);
        initWidgets();
        popupSearch = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);

        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(Paysheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> loginCall = apiService.getPaysheetLastWeekReport("");

            loginCall.enqueue(new Callback<PaysheetLastWeekAPI.PaysheetLastWeekResponse>() {
                @Override
                public void onResponse(Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> call, Response<PaysheetLastWeekAPI.PaysheetLastWeekResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        filldataDetails(response.body().getData());
                    } else {
                        Log.i(TAG, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> call, Throwable t) {
                    mCustomProgressDialog.dismiss();
                    Log.i(TAG, t.getMessage());
                }
            });


        }


    }

    private void filldataDetails(List<PaysheetLastWeekAPI.Datum> data) {
        mPaysheetData = new ArrayList<Data>();
        for (int i = 0; i < data.size(); i++) {
            PaysheetLastWeekAPI.Datum model = data.get(i);
            mData = new Data();
            mData.setComment(model.getComment());
            mData.setDolly_no(model.getDolly_no());
            mData.setDuty(model.getDuty());
            mData.setEmail(model.getEmail());
            mData.setEnd_km(model.getEnd_km());
            mData.setFirst_name(model.getFirst_name());
            mData.setFrom(model.getFrom());
            mData.setInspection(model.getInspection());
            mData.setMf_no(model.getMf_no());
            mData.setLast_name(model.getLast_name());
            mData.setOffice_use(model.getOffice_use());
            mData.setPdate(model.getPdate());
            mData.setPid(model.getPid());
            mData.setRt_bd(model.getRt_bd());
            mData.setStart_km(model.getStart_km());
            mData.setTo(model.getTo());
            mData.setTr1_no(model.getTr1_no());
            mData.setTr2_no(model.getTr2_no());
            mData.setTr3_no(model.getTr3_no());
            mData.setTruck_no(model.getTruck_no());
            mData.setUid(model.getUid());
            mData.setUnloading_time(model.getUnloading_time());
            mPaysheetData.add(mData);
            mPaysheetReport.setData(mPaysheetData);
        }

        if (mPaysheetReport.getData().size() > 0) {
            aPaysheetWeeklyAdapter = new PaysheetWeeklyAdapter(Paysheet.this, mPaysheetReport.getData());
            mPaysheetView.setAdapter(aPaysheetWeeklyAdapter);
        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_paysheet_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mPaysheetView = (ListView) findViewById(R.id.AP_RV_paysheet);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_search) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT
            );
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            if (!popupSearch.isShowing()) {
                Util.searchPopUpWindow(Paysheet.this, popupSearch, getLayoutInflater(),mPaysheetView);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mPaysheetView.setLayoutParams(layoutParams);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
