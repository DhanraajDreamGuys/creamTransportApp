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

import co.in.dreamguys.cream.adapter.PBCustomerAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.PBCustomerAPI;
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

public class PBCustomers extends AppCompatActivity {
    Toolbar mToolbar;
    public static String TAG = PBCustomers.class.getName();
    CustomProgressDialog mCustomProgressDialog;
    ListView mPBCustomersWidgets;
    PBCustomerAdapter aPBCustomerAdapter;
    public static SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_customers);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerLists();
    }

    private void getCustomerLists() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(PBCustomers.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<PBCustomerAPI.PBCustomerResponse> loginCall = apiService.getPBCustomerLists();

            loginCall.enqueue(new Callback<PBCustomerAPI.PBCustomerResponse>() {
                @Override
                public void onResponse(Call<PBCustomerAPI.PBCustomerResponse> call, Response<PBCustomerAPI.PBCustomerResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        aPBCustomerAdapter = new PBCustomerAdapter(PBCustomers.this, response.body().getData());
                        mPBCustomersWidgets.setAdapter(aPBCustomerAdapter);
                        aPBCustomerAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PBCustomers.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<PBCustomerAPI.PBCustomerResponse> call, Throwable t) {
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
        mToolbar.setTitle(getString(R.string.toolbar_add_pb_customer_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mPBCustomersWidgets = (ListView) findViewById(R.id.APBC_LV_customer);
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
        searchEditText.setHint(getString(R.string.str_search));
        searchEditText.setTextSize(14);
        searchEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aPBCustomerAdapter.setSearchEnabled(true, newText);
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
            ActivityConstants.callPage(PBCustomers.this, AddPBCustomer.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
