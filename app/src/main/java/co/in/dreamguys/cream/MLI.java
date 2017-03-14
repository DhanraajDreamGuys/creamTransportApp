package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import co.in.dreamguys.cream.adapter.MLIsAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.MLIAPI;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.searchPopUpWindow;

/**
 * Created by user5 on 13-03-2017.
 */

public class MLI extends AppCompatActivity implements SearchListViewNotify {
    Toolbar mToolbar;
    ListView mMLIWidgets;
    CustomProgressDialog mCustomProgressDialog;
    MLIsAdapter aMLIsAdapter;
    PopupWindow popupSearch;
    public static String TAG = MLI.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mli);
        popupSearch = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        getMLIs();
    }

    private void getMLIs() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(MLI.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<MLIAPI.MLIsresponse> loginCall = apiService.getMLIs();
            loginCall.enqueue(new Callback<MLIAPI.MLIsresponse>() {
                @Override
                public void onResponse(Call<MLIAPI.MLIsresponse> call, Response<MLIAPI.MLIsresponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aMLIsAdapter = new MLIsAdapter(MLI.this, response.body().getData());
                        mMLIWidgets.setAdapter(aMLIsAdapter);
                        aMLIsAdapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(findViewById(R.id.AMLI_LL_parent), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MLIAPI.MLIsresponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_mli_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mMLIWidgets = (ListView) findViewById(R.id.AMLI_LV_mli);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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
                searchPopUpWindow(MLI.this, popupSearch, Constants.MLI, getLayoutInflater(), mMLIWidgets);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mMLIWidgets.setLayoutParams(layoutParams);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void searchNotify() {
        getMLIs();
    }
}
