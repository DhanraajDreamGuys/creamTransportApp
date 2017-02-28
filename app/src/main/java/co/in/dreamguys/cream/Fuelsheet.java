package co.in.dreamguys.cream;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.FuelsheetAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.interfaces.SearchListViewNotify;
import co.in.dreamguys.cream.interfaces.TripsheetInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.aFuelSheetAdapter;
import static co.in.dreamguys.cream.utils.Util.fillFuelSheetData;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.mFuelSheetData;
import static co.in.dreamguys.cream.utils.Util.searchPopUpWindow;

/**
 * Created by user5 on 25-02-2017.
 */

public class Fuelsheet extends AppCompatActivity implements SearchListViewNotify, TripsheetInterface {
    Toolbar mToolbar;
    ListView mFuelsheetWidgets;
    PopupWindow mPopupWindow;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Fuelsheet.class.getName();
    public static SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_sheet);
        initWidgets();
        Constants.FUELSHEETCLASS = this;
        mPopupWindow = new PopupWindow(this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        getFuelsheetLists();
    }

    private void getFuelsheetLists() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(Fuelsheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<FuelsheetAPI.FuelSheetListResponse> loginCall = apiService.getFuelsheetLists();

            loginCall.enqueue(new Callback<FuelsheetAPI.FuelSheetListResponse>() {
                @Override
                public void onResponse(Call<FuelsheetAPI.FuelSheetListResponse> call, Response<FuelsheetAPI.FuelSheetListResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        fillFuelSheetData(Fuelsheet.this, response.body().getData(), mFuelsheetWidgets);
                    } else {
                        Toast.makeText(Fuelsheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }


                @Override
                public void onFailure(Call<FuelsheetAPI.FuelSheetListResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.toolbar_fuel_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mFuelsheetWidgets = (ListView) findViewById(R.id.AFS_LV_fuellist);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fuel_sheet, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_report_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setHint(getString(R.string.str_word_search));
        searchEditText.setTextSize(12);
        searchEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aFuelSheetAdapter.setSearchEnabled(true, newText);
                return true;
            }
        });

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
            if (!mPopupWindow.isShowing()) {
                searchPopUpWindow(Fuelsheet.this, mPopupWindow, Constants.FUELSHEET, getLayoutInflater(), mFuelsheetWidgets);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mFuelsheetWidgets.setLayoutParams(layoutParams);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void searchNotify() {
        getFuelsheetLists();
    }

    @Override
    public void view(String id, int viewType) {

    }

    @Override
    public void delete(String delete_id, final int position) {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UpdateUsersAPI.UpdateUsersResponse> repairsheet = apiService.getDeleteFuelSheet(delete_id);
        repairsheet.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
            @Override
            public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    Toast.makeText(Fuelsheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    mFuelSheetData.getData().remove(position);
                    aFuelSheetAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Fuelsheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                mCustomProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                mCustomProgressDialog.dismiss();
            }
        });
    }
}
