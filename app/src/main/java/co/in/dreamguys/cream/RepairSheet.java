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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.in.dreamguys.cream.adapter.RepairsheetAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DeleteRepairsheetAPI;
import co.in.dreamguys.cream.apis.RepairsheetCurrentDayAPI;
import co.in.dreamguys.cream.apis.UpdateSheetAPI;
import co.in.dreamguys.cream.interfaces.RepairsheetNotify;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.model.RepairSheetData;
import co.in.dreamguys.cream.model.RepairSheetReport;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 15-02-2017.
 */

public class RepairSheet extends AppCompatActivity implements SearchListViewNotify, RepairsheetNotify {
    Toolbar mToolbar;
    PopupWindow popupSearch;
    ListView mRepairSheetView;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = RepairSheet.class.getName();
    RepairsheetAdapter aRepairsheetAdapter;
    RepairSheetData mRepairSheetData;
    RepairSheetReport mRepairSheetReport = new RepairSheetReport();
    List<RepairSheetData> mRepairSheetDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_sheet);
        mCustomProgressDialog = new CustomProgressDialog(this);
        popupSearch = new PopupWindow(this);
        initWidgets();
        Constants.Repairsheet = this;
        currentDayRepairSheet();

    }

    private void currentDayRepairSheet() {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(RepairSheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<RepairsheetCurrentDayAPI.RepairsheetResponse> loginCall = apiService.getRepairsheet();
            loginCall.enqueue(new Callback<RepairsheetCurrentDayAPI.RepairsheetResponse>() {
                @Override
                public void onResponse(Call<RepairsheetCurrentDayAPI.RepairsheetResponse> call, Response<RepairsheetCurrentDayAPI.RepairsheetResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        fillRepairSheetData(response.body().getData());
                    } else {
                        Toast.makeText(RepairSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RepairsheetCurrentDayAPI.RepairsheetResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    private void fillRepairSheetData(List<RepairsheetCurrentDayAPI.Datum> data) {
        mRepairSheetDataList = new ArrayList<RepairSheetData>();

        for (int i = 0; i < data.size(); i++) {
            RepairsheetCurrentDayAPI.Datum mData = data.get(i);
            mRepairSheetData = new RepairSheetData();
            mRepairSheetData.setUid(mData.getUid());
            mRepairSheetData.setComments(mData.getComments());
            mRepairSheetData.setTruck_no(mData.getTruck_no());
            mRepairSheetData.setDolly_no(mData.getDolly_no());
            mRepairSheetData.setEmail(mData.getEmail());
            mRepairSheetData.setFaults(mData.getFaults());
            mRepairSheetData.setFirst_name(mData.getFirst_name());
            mRepairSheetData.setLast_name(mData.getLast_name());
            mRepairSheetData.setRdate(mData.getRdate());
            mRepairSheetData.setImage_one(mData.getImage_one());
            mRepairSheetData.setImage_two(mData.getImage_two());
            mRepairSheetData.setRegn1_no(mData.getRegn1_no());
            mRepairSheetData.setRegn_no(mData.getRegn_no());
            mRepairSheetData.setRid(mData.getRid());
            mRepairSheetDataList.add(mRepairSheetData);
            mRepairSheetReport.setData(mRepairSheetDataList);
        }

        if (mRepairSheetReport.getData().size() > 0) {
            aRepairsheetAdapter = new RepairsheetAdapter(RepairSheet.this, mRepairSheetReport.getData());
            mRepairSheetView.setAdapter(aRepairsheetAdapter);
            aRepairsheetAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
        }

    }


    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_repair_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mRepairSheetView = (ListView) findViewById(R.id.ARS_LV_repair_sheet);

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
                Util.searchPopUpWindow(RepairSheet.this, popupSearch, Constants.REPAIR, getLayoutInflater(), mRepairSheetView);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mRepairSheetView.setLayoutParams(layoutParams);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void searchNotify() {
        currentDayRepairSheet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        popupSearch.dismiss();
    }

    @Override
    public void deleteRepairSheet(String delete_id, final int position) {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteRepairsheetAPI.DeleteRepairsheetResponse> repairsheet = apiService.getDeleteRepairsheetReport(delete_id);
        repairsheet.enqueue(new Callback<DeleteRepairsheetAPI.DeleteRepairsheetResponse>() {
            @Override
            public void onResponse(Call<DeleteRepairsheetAPI.DeleteRepairsheetResponse> call, Response<DeleteRepairsheetAPI.DeleteRepairsheetResponse> response) {
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    Toast.makeText(RepairSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    mRepairSheetReport.getData().remove(position);
                    aRepairsheetAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RepairSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                mCustomProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<DeleteRepairsheetAPI.DeleteRepairsheetResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                mCustomProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void viewRepairSheet(String id, final int viewType) {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RepairsheetCurrentDayAPI.RepairsheetResponse> repairsheet = apiService.getViewRepairsheetReport(id);
        repairsheet.enqueue(new Callback<RepairsheetCurrentDayAPI.RepairsheetResponse>() {
            @Override
            public void onResponse(Call<RepairsheetCurrentDayAPI.RepairsheetResponse> call, Response<RepairsheetCurrentDayAPI.RepairsheetResponse> response) {
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    Intent mCallViewPaysheet = new Intent(RepairSheet.this, ViewRepairsheet.class);
                    mCallViewPaysheet.putExtra(Constants.REPAIRSHEETDETAILS, (Serializable) response.body().getData().get(0));
                    mCallViewPaysheet.putExtra(Constants.MODE, viewType);  // Mode 0 means edit and 1 means view
                    startActivity(mCallViewPaysheet);
                } else {
                    Toast.makeText(RepairSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                mCustomProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RepairsheetCurrentDayAPI.RepairsheetResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                mCustomProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void updateRepairSheet(String id, String comments) {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(RepairSheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateSheetAPI.UpdatePaysheetResponse> loginCall = apiService.getUpdateRepairsheetReport(sendValueWithRetrofit(id, comments));
            loginCall.enqueue(new Callback<UpdateSheetAPI.UpdatePaysheetResponse>() {
                @Override
                public void onResponse(Call<UpdateSheetAPI.UpdatePaysheetResponse> call, Response<UpdateSheetAPI.UpdatePaysheetResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Toast.makeText(RepairSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Constants.ViewRepairsheet.finish();
                    } else {
                        Toast.makeText(RepairSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateSheetAPI.UpdatePaysheetResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    private HashMap<String, String> sendValueWithRetrofit(String id, String comments) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_ID, id);
        params.put(Constants.PARAMS_OFFICE_USE, comments);
        return params;
    }


}
