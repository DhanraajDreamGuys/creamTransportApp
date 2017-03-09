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

import co.in.dreamguys.cream.adapter.TyreRepairAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.TyreRepairAPI;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.searchPopUpWindow;

/**
 * Created by user5 on 11-02-2017.
 */

public class Tyrerepair extends AppCompatActivity implements SearchListViewNotify {
    Toolbar mToolbar;
    ListView mTyreRepairWidgets;
    PopupWindow popupSearch;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Tyrerepair.class.getName();
    TyreRepairAdapter aTyreRepairAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyre_repair);
        popupSearch = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTyreRepairs();
    }

    private void getTyreRepairs() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(Tyrerepair.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<TyreRepairAPI.TyrerepairResponse> loginCall = apiService.getTyreRepairs();

            loginCall.enqueue(new Callback<TyreRepairAPI.TyrerepairResponse>() {
                @Override
                public void onResponse(Call<TyreRepairAPI.TyrerepairResponse> call, Response<TyreRepairAPI.TyrerepairResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aTyreRepairAdapter = new TyreRepairAdapter(Tyrerepair.this, response.body().getData());
                        mTyreRepairWidgets.setAdapter(aTyreRepairAdapter);
                        aTyreRepairAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Tyrerepair.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<TyreRepairAPI.TyrerepairResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_tyre_repair_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mTyreRepairWidgets = (ListView) findViewById(R.id.ATR_LV_tyre_repair);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tyre_repair, menu);
        return super.onCreateOptionsMenu(menu);
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
                searchPopUpWindow(Tyrerepair.this, popupSearch, Constants.TYREREPAIR, getLayoutInflater(), mTyreRepairWidgets);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mTyreRepairWidgets.setLayoutParams(layoutParams);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void searchNotify() {
        getTyreRepairs();
    }
}
