package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.apis.UsefulLinksAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by Dhanraaj on 3/2/2017.
 */
public class EditUsefulLinks extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    EditText mLinkName, mLinks;
    Button mAdd;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = EditUsefulLinks.class.getName();
    ToggleButton mStatus;
    UsefulLinksAPI.Datum mData;
    boolean isStatusUpdate = false;
    String value = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_useful_links);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();

        mData = (UsefulLinksAPI.Datum) getIntent().getSerializableExtra(Constants.EDIT_USEFUL_LINKS);
        if (mData != null) {
            mLinkName.setText(mData.getName());
            mLinks.setText(mData.getLink());
            if (mData.getStatus().equalsIgnoreCase("0")) {
                mStatus.setChecked(true);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditUsefulLinks.this, R.drawable.button_background_green));
            } else {
                mStatus.setChecked(false);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditUsefulLinks.this, R.drawable.button_background_red));
            }
        }
        mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == mStatus.getId()) {
                    if (isChecked) {
                        callStatusApi(true);
                    } else {
                        callStatusApi(false);
                    }
                }
            }
        });
    }

    private void callStatusApi(boolean status) {

        if (status) {
            value = "0";
        } else {
            value = "1";
        }

        if (!isNetworkAvailable(this)) {
            Toast.makeText(EditUsefulLinks.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.addUsefulLinks(sendValueWithRetrofit());
            final String finalValue = value;
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        if (finalValue.equalsIgnoreCase("0")) {
                            mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditUsefulLinks.this, R.drawable.button_background_green));
                        } else {
                            mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditUsefulLinks.this, R.drawable.button_background_red));
                        }
                        isStatusUpdate = true;
                        Toast.makeText(EditUsefulLinks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditUsefulLinks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_useful_links_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mLinkName = (EditText) findViewById(R.id.AEUL_ET_name);
        mLinks = (EditText) findViewById(R.id.AEUL_ET_links);
        mAdd = (Button) findViewById(R.id.AEUL_BT_save);
        mAdd.setOnClickListener(this);
        mStatus = (ToggleButton) findViewById(R.id.AEUL_TB_status_update);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mAdd.getId()) {
            if (mLinkName.getText().toString().isEmpty()) {
                mLinkName.setError(getString(R.string.err_link_name));
                mLinkName.requestFocus();
            } else if (mLinks.getText().toString().isEmpty()) {
                mLinks.setError(getString(R.string.err_links));
                mLinks.requestFocus();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(EditUsefulLinks.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.addUsefulLinks(sendValueWithRetrofit());

                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(EditUsefulLinks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Constants.USEFULLINK.refresh();
                            finish();
                        } else {
                            Toast.makeText(EditUsefulLinks.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isStatusUpdate) {
            Constants.USEFULLINK.refresh();
            isStatusUpdate = false;
            finish();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_LINK_NAME, mLinkName.getText().toString());
        params.put(Constants.PARAMS_WEB_LINK, mLinks.getText().toString());
        params.put(Constants.PARAMS_LINK_ID, mData.getId());
        params.put(Constants.PARAMS_LINK_STATUS, value);
        return params;
    }
}
