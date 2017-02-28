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

import co.in.dreamguys.cream.adapter.FridgeCodecAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.FridgeCodeAPI;
import co.in.dreamguys.cream.interfaces.FridgeCodeTypeInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.ActivityConstants.callPage;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.searchFridgeCodec;

/**
 * Created by user5 on 27-02-2017.
 */

public class FridgeCodec extends AppCompatActivity implements FridgeCodeTypeInterface {
    Toolbar mToolbar;
    ListView mFridgeCodecWidgets;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = FridgeCodec.class.getName();
    FridgeCodecAdapter aFridgeCodecAdapter;
    PopupWindow mPopupWindow;
    public static SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_codec);
        Constants.FRIDGE_CODEC = this;
        mPopupWindow = new PopupWindow();
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
        getFridgeCodecList();

    }

    private void getFridgeCodecList() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(FridgeCodec.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<FridgeCodeAPI.FridgecodeResponse> loginCall = apiService.getFridgeCodeList();
            loginCall.enqueue(new Callback<FridgeCodeAPI.FridgecodeResponse>() {
                @Override
                public void onResponse(Call<FridgeCodeAPI.FridgecodeResponse> call, Response<FridgeCodeAPI.FridgecodeResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aFridgeCodecAdapter = new FridgeCodecAdapter(FridgeCodec.this, response.body().getData());
                        mFridgeCodecWidgets.setAdapter(aFridgeCodecAdapter);
                        aFridgeCodecAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(FridgeCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FridgeCodeAPI.FridgecodeResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_fridge_codec_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mFridgeCodecWidgets = (ListView) findViewById(R.id.AFC_LV_fridge_codec_list);
    }

    @Override
    public void typeSearch(String type) {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(FridgeCodec.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<FridgeCodeAPI.FridgecodeResponse> loginCall = apiService.getFridgeCodeList(type);
            loginCall.enqueue(new Callback<FridgeCodeAPI.FridgecodeResponse>() {
                @Override
                public void onResponse(Call<FridgeCodeAPI.FridgecodeResponse> call, Response<FridgeCodeAPI.FridgecodeResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        mFridgeCodecWidgets.setAdapter(null);
                        aFridgeCodecAdapter = new FridgeCodecAdapter(FridgeCodec.this, response.body().getData());
                        mFridgeCodecWidgets.setAdapter(aFridgeCodecAdapter);
                        aFridgeCodecAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(FridgeCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FridgeCodeAPI.FridgecodeResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void refresh() {
        mFridgeCodecWidgets.setAdapter(null);
        getFridgeCodecList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fridge_codec, menu);
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
        searchEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchEditText.setTextSize(14);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aFridgeCodecAdapter.setSearchEnabled(true, newText);
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
                searchFridgeCodec(FridgeCodec.this, mPopupWindow, Constants.FRIDGE_CODEC_STRING, getLayoutInflater(), mFridgeCodecWidgets);
                layoutParams.setMargins(0, (height / 4), 0, 0);
                mFridgeCodecWidgets.setLayoutParams(layoutParams);
            }
        } else if (item.getItemId() == R.id.menu_add_fridge) {
            callPage(FridgeCodec.this, AddFridgeCodec.class);
        }
        return true;
    }
}
