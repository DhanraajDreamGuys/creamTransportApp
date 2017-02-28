package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 28-02-2017.
 */

public class AddTripHours extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = AddTripHours.class.getName();
    TextView mFrom, mTo;
    EditText mHours;
    Button mSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip_hours);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_trips_hours_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mFrom = (TextView) findViewById(R.id.AETH_ET_from);
        mTo = (TextView) findViewById(R.id.AETH_ET_to);
        mHours = (EditText) findViewById(R.id.AETH_ET_hours);
        mSave = (Button) findViewById(R.id.AETH_BT_save);

        mFrom.setOnClickListener(this);
        mTo.setOnClickListener(this);
        mSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mFrom.getId()) {
            Constants.AdminMenu.getLocations(AddTripHours.this, mFrom, Constants.FromString);
        } else if (v.getId() == mTo.getId()) {
            Constants.AdminMenu.getLocations(AddTripHours.this, mTo, Constants.ToString);
        } else if (v.getId() == mSave.getId()) {
            if (mFrom.getText().toString().isEmpty()) {
                Toast.makeText(AddTripHours.this, getString(R.string.choose_from), Toast.LENGTH_SHORT).show();
            } else if (mTo.getText().toString().isEmpty()) {
                Toast.makeText(AddTripHours.this, getString(R.string.choose_to), Toast.LENGTH_SHORT).show();
            } else if (mHours.getText().toString().isEmpty()) {
                mHours.setError(getString(R.string.err_hours));
                mHours.requestFocus();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(AddTripHours.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.addTripHours(sendValueWithRetrofit());
                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        mCustomProgressDialog.dismiss();
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(AddTripHours.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Constants.TRIPHOURS.refresh();
                            finish();
                        } else {
                            Toast.makeText(AddTripHours.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                        mCustomProgressDialog.dismiss();
                    }
                });
            }
        }
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_EDIT_ID, "");
        params.put(Constants.PARAMS_TRIP_HOUR, mHours.getText().toString());
        for (BranchAPI.Datum branch : Constants.countries) {
            if (branch.getName().equalsIgnoreCase(mFrom.getText().toString())) {
                params.put(Constants.PARAMS_FROM_TRIP, branch.getId());
            }
        }
        for (BranchAPI.Datum branch : Constants.countries) {
            if (branch.getName().equalsIgnoreCase(mTo.getText().toString())) {
                params.put(Constants.PARAMS_TO_TRIP, branch.getId());
            }
        }
        return params;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
