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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import co.in.dreamguys.cream.adapter.PBTruckAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.PBTruckAPI;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 06-03-2017.
 */

public class PBTruck extends AppCompatActivity {
    Toolbar mToolbar;
    ListView mPBTrucksWidgets;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = PBTruck.class.getName();
    PBTruckAdapter aPBTruckAdapter;
    public static SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_truck);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getTrucksLists();
    }

    private void getTrucksLists() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(PBTruck.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<PBTruckAPI.PBTruckListResponse> loginCall = apiService.getPBTruckLists();

            loginCall.enqueue(new Callback<PBTruckAPI.PBTruckListResponse>() {
                @Override
                public void onResponse(Call<PBTruckAPI.PBTruckListResponse> call, Response<PBTruckAPI.PBTruckListResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aPBTruckAdapter = new PBTruckAdapter(PBTruck.this, response.body().getData());
                        mPBTrucksWidgets.setAdapter(aPBTruckAdapter);
                        aPBTruckAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PBTruck.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<PBTruckAPI.PBTruckListResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.tool_truck_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mPBTrucksWidgets = (ListView) findViewById(R.id.APBT_LV_trucks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pb_truck, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_filters).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setHint(getString(R.string.str_word_search));
        searchEditText.setTextSize(14);
        searchEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aPBTruckAdapter.setSearchEnabled(true, newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_add) {
            ActivityConstants.callPage(PBTruck.this, AddPBTruck.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
