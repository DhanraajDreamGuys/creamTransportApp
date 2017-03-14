package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.in.dreamguys.cream.adapter.LocationAdapter;
import co.in.dreamguys.cream.adapter.StaffReportAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.StaffreportAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 11-03-2017.
 */

public class Staffreport extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ListView mStaffReportWidgets;
    TextView mSelectLocation;
    View vAlertLayout;
    AlertDialog aAlertDialog;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Staffreport.class.getName();
    LocationAdapter aLocationAdapter;
    StaffReportAdapter aStaffReportAdapter;
    String templateId = "";
    ArrayList<String> mLocations = new ArrayList<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reports);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        getStaffreports();

    }

    private void getStaffreports() {
        if (!isNetworkAvailable(this)) {
            Snackbar.make(findViewById(R.id.AAA_LL_parent), getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<StaffreportAPI.StaffReportResponse> loginCall = apiService.getStaffReports();

            loginCall.enqueue(new Callback<StaffreportAPI.StaffReportResponse>() {
                @Override
                public void onResponse(Call<StaffreportAPI.StaffReportResponse> call, Response<StaffreportAPI.StaffReportResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aStaffReportAdapter = new StaffReportAdapter(Staffreport.this, response.body().getData());
                        mStaffReportWidgets.setAdapter(aStaffReportAdapter);
                        aStaffReportAdapter.notifyDataSetChanged();
                        Constants.STAFF_REPORT_DATA = response.body().getData();

                        for (StaffreportAPI.Datum mLocation : response.body().getData()) {
                            if (!mLocations.contains(mLocation.getLocation()))
                                mLocations.add(mLocation.getLocation());
                        }

                    } else {
                        Snackbar.make(findViewById(R.id.AAA_LL_parent), response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<StaffreportAPI.StaffReportResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_staff_report_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mStaffReportWidgets = (ListView) findViewById(R.id.ASR_LV_staff_reports);
        mSelectLocation = (TextView) findViewById(R.id.ASR_TV_location);
        mSelectLocation.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mSelectLocation.getId()) {
            if (Constants.STAFF_REPORT_DATA != null) {
                if (Constants.STAFF_REPORT_DATA.size() > 0) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                    vAlertLayout = getLayoutInflater().inflate(R.layout.dialog_template, null);
                    mBuilder.setView(vAlertLayout);
                    aAlertDialog = mBuilder.create();
                    WindowManager.LayoutParams lp = aAlertDialog.getWindow().getAttributes();
                    lp.dimAmount = 0.0f;
                    lp.gravity = Gravity.TOP;
                    aAlertDialog.getWindow().setAttributes(lp);
                    aAlertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    ListView mTemplateWidgets = (ListView) vAlertLayout.findViewById(R.id.DT_LV_templates);


                    aLocationAdapter = new LocationAdapter(this, mLocations);
                    mTemplateWidgets.setAdapter(aLocationAdapter);
                    mTemplateWidgets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (BranchAPI.Datum branch : Constants.countries) {
                                if (mLocations.get(position).equalsIgnoreCase(branch.getId())) {
                                    mSelectLocation.setText(branch.getName());
                                    templateId = branch.getId();
                                }
                            }
                            aAlertDialog.dismiss();
                        }
                    });
                }
                aAlertDialog.show();
            } else {
                Snackbar.make(findViewById(R.id.AAA_LL_parent), getString(R.string.no_data_found), Snackbar.LENGTH_LONG).show();
            }

        }
    }
}
