package co.in.dreamguys.cream;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.in.dreamguys.cream.adapter.PaysheetWeeklyAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DriverListsAPI;
import co.in.dreamguys.cream.apis.PaysheetLastWeekAPI;
import co.in.dreamguys.cream.apis.PrintURLAPI;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.interfaces.TextViewInstances;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.model.PaysheetReport;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Download;
import co.in.dreamguys.cream.utils.DownloadService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static co.in.dreamguys.cream.utils.Constants.LEAVEFORMSTRING;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.searchPopUpWindow;

/**
 * Created by user5 on 14-02-2017.
 */

public class Paysheet extends AppCompatActivity implements SearchListViewNotify, TextViewInstances {
    Toolbar mToolbar;
    PopupWindow popupSearch;
    Data mData;
    PaysheetReport mPaysheetReport = new PaysheetReport();
    CustomProgressDialog mCustomProgressDialog;
    List<Data> mPaysheetData;
    private static String TAG = Paysheet.class.getName();
    PaysheetWeeklyAdapter aPaysheetWeeklyAdapter;
    ListView mPaysheetView;
    TextView name, sDate, eDate;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysheet);
        initWidgets();
        registerReceiver();
        popupSearch = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        LastWeeklyReport();
    }

    private void LastWeeklyReport() {
        if (!isNetworkAvailable(this)) {
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
                        Toast.makeText(Paysheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            aPaysheetWeeklyAdapter.notifyDataSetChanged();
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
                searchPopUpWindow(Paysheet.this, popupSearch, Constants.PAYSHEET, getLayoutInflater(), mPaysheetView);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mPaysheetView.setLayoutParams(layoutParams);
            }
        } else if (item.getItemId() == R.id.MS_print) {
            getPrintUrl();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void searchNotify() {
        LastWeeklyReport();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popupSearch.dismiss();
    }

    private void getPrintUrl() {
        if (name != null && sDate != null && eDate != null)
            if (name.getText().toString().isEmpty()) {
                name.setError(getString(R.string.err_driver_name));
                name.requestFocus();
            } else if (sDate.getText().toString().isEmpty()) {
                sDate.setError(getString(R.string.err_start_date));
                sDate.requestFocus();
            } else if (eDate.getText().toString().isEmpty()) {
                eDate.setError(getString(R.string.err_end_date));
                eDate.requestFocus();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<PrintURLAPI.PrintURLResponse> loginCall = apiService.getPrintURL(sendValueWithRetrofits());

                loginCall.enqueue(new Callback<PrintURLAPI.PrintURLResponse>() {
                    @Override
                    public void onResponse(Call<PrintURLAPI.PrintURLResponse> call, Response<PrintURLAPI.PrintURLResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            String URL = response.body().getData().getLink();
                            if (checkPermission()) {
                                String LEAVEFORMSTRING = "PAYMENT SHEET USER";
                                name.setText("");
                                sDate.setText("");
                                eDate.setText("");
                                startDownload(URL, LEAVEFORMSTRING);
                            } else {
                                requestPermission();
                            }
                        } else {
                            Toast.makeText(Paysheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mCustomProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<PrintURLAPI.PrintURLResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                        mCustomProgressDialog.dismiss();
                    }
                });
            }
        else
            Toast.makeText(Paysheet.this, getString(R.string.fields_empty), Toast.LENGTH_SHORT);
    }

    private HashMap<String, String> sendValueWithRetrofits() {
        HashMap<String, String> params = new HashMap<>();
        for (DriverListsAPI.Datum driverId : Constants.driverList) {
            String drviername = driverId.getFirst_name() + " " + driverId.getLast_name();
            if (drviername.equalsIgnoreCase(name.getText().toString()))
                params.put(Constants.PARAMS_LINK_NAME, driverId.getId());
        }
        params.put(Constants.PARAMS_SDATE, sDate.getText().toString());
        params.put(Constants.PARAMS_END_DATE, eDate.getText().toString());
        params.put(Constants.PARAMS_TYPE, "PAYMENT SHEET USER");
        return params;
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Message_Progress");
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("Message_Progress")) {
                Download download = intent.getParcelableExtra("download");
                if (download.getProgress() == 100) {
                    Snackbar.make(findViewById(R.id.AELF_LL_parent), "Download Completed...", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(URL, LEAVEFORMSTRING);
                } else {
                    Snackbar.make(findViewById(R.id.AELF_LL_parent), "Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void startDownload(String link, String LEAVEFORMSTRING) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(Constants.URL_LINK, link);
        intent.putExtra(Constants.TYPE, LEAVEFORMSTRING);
        startService(intent);
    }


    @Override
    public void printInstance(TextView name, TextView sDate, TextView eDate) {
        this.name = name;
        this.sDate = sDate;
        this.eDate = eDate;
    }
}
