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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.adapter.ViewpaysheetAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UpdateSheetAPI;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.setListViewHeightBasedOnChildren;

/**
 * Created by user5 on 13-02-2017.
 */

public class ViewPaysheet extends AppCompatActivity implements View.OnClickListener {

    ListView mPaysheetViews;
    View mHeaderViewPaysheet;
    Data mData;
    Toolbar mToolbar;
    ViewpaysheetAdapter aViewpaysheetAdapter;
    TextView mName, mSignature, mComments;
    EditText mOfficalUse;
    Button mSaveChanges;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = ViewPaysheet.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paysheet);
        mCustomProgressDialog = new CustomProgressDialog(this);
        mData = (Data) getIntent().getSerializableExtra(Constants.PAYSHEETDETAILS);

        initWidgets();

        mName.setText(mData.getFirst_name() + " " + mData.getLast_name());
        mName.requestFocus();
        mComments.setText(mData.getComment());

        mHeaderViewPaysheet = getLayoutInflater().inflate(R.layout.include_header_view_paysheet, null);
        mPaysheetViews.addHeaderView(mHeaderViewPaysheet);

        aViewpaysheetAdapter = new ViewpaysheetAdapter(ViewPaysheet.this, mData);
        mPaysheetViews.setAdapter(aViewpaysheetAdapter);
        setListViewHeightBasedOnChildren(mPaysheetViews);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mOfficalUse.setFocusable(true);
            mOfficalUse.setClickable(true);
            mOfficalUse.setFocusableInTouchMode(true);
            mSaveChanges.setVisibility(View.VISIBLE);
        } else {
            mOfficalUse.setFocusable(false);
            mOfficalUse.setClickable(false);
            mOfficalUse.setFocusableInTouchMode(false);
            mSaveChanges.setVisibility(View.GONE);
        }
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mToolbar.setTitle(getString(R.string.tool_edit_paysheet_title));
        } else {
            mToolbar.setTitle(getString(R.string.tool_view_paysheet_title));
        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mPaysheetViews = (ListView) findViewById(R.id.AVP_LV_view_paysheet);
        mName = (TextView) findViewById(R.id.AVP_TV_name);
        mSignature = (TextView) findViewById(R.id.AVP_TV_signature);
        mComments = (TextView) findViewById(R.id.AVP_TV_comments);
        mOfficalUse = (EditText) findViewById(R.id.AVP_TV_offical_use);
        mSaveChanges = (Button) findViewById(R.id.AVP_BT_save);
        mSaveChanges.setOnClickListener(this);
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
        if (v.getId() == R.id.AVP_BT_save) {
            if (!isNetworkAvailable(this)) {
                Toast.makeText(ViewPaysheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateSheetAPI.UpdatePaysheetResponse> loginCall = apiService.getUpdatePaysheetReport(sendValueWithRetrofit());
                loginCall.enqueue(new Callback<UpdateSheetAPI.UpdatePaysheetResponse>() {
                    @Override
                    public void onResponse(Call<UpdateSheetAPI.UpdatePaysheetResponse> call, Response<UpdateSheetAPI.UpdatePaysheetResponse> response) {
                        mCustomProgressDialog.dismiss();
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(ViewPaysheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ViewPaysheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    }


    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_ID, mData.getPid());
        params.put(Constants.PARAMS_OFFICE_USE, mOfficalUse.getText().toString());
        return params;
    }
}
