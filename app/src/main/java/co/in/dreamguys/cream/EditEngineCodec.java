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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.FridgeCodeAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 27-02-2017.
 */

public class EditEngineCodec extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Toolbar mToolbar;
    EditText mCodeNo, mCodeDesc, mOperation;
    RadioGroup mCodeColor, mCodeType;
    Button mSave;
    FridgeCodeAPI.Datum mData;
    RadioButton mGreen, mRed, mYellow, mCodeCummins, mCodeCatapillar;
    String checkedColor = "", checkedType = "";
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = EditEngineCodec.class.getName();
    ToggleButton mStatus;
    boolean isStatusUpdate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_engine_codec);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();

        mData = (FridgeCodeAPI.Datum) getIntent().getSerializableExtra(Constants.ENGINE_CODE_DATA);
        if (mData != null) {
            mCodeNo.setText(mData.getCode());
            mCodeDesc.setText(mData.getDescription());
            mOperation.setText(mData.getOperation());
            if (mData.getType().equalsIgnoreCase("cummins")) {
                mCodeCatapillar.setChecked(true);
                checkedType = "cummins";
            } else {
                mCodeCummins.setChecked(true);
                checkedType = "catapillar";
            }
            if (mData.getColor().equalsIgnoreCase(getString(R.string.color_green))) {
                mGreen.setChecked(true);
                checkedColor = mGreen.getText().toString();
            } else if (mData.getColor().equalsIgnoreCase(getString(R.string.color_red))) {
                mRed.setChecked(true);
                checkedColor = mRed.getText().toString();
            } else if (mData.getColor().equalsIgnoreCase(getString(R.string.color_yellow))) {
                mYellow.setChecked(true);
                checkedColor = mYellow.getText().toString();
            }
            if (mData.getStatus().equalsIgnoreCase("0")) {
                mStatus.setChecked(true);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditEngineCodec.this, R.drawable.button_background_green));
            } else {
                mStatus.setChecked(false);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditEngineCodec.this, R.drawable.button_background_red));
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

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_edit_engine_codec_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mCodeNo = (EditText) findViewById(R.id.AEEC_ET_code_no);
        mCodeType = (RadioGroup) findViewById(R.id.AEEC_RG_code_type);
        mCodeDesc = (EditText) findViewById(R.id.AEEC_ET_desc);
        mOperation = (EditText) findViewById(R.id.AEEC_ET_operation);
        mCodeColor = (RadioGroup) findViewById(R.id.AEEC_RG_code_colors);
        mSave = (Button) findViewById(R.id.AEEC_BT_save);
        mSave.setOnClickListener(this);
        mCodeColor.setOnCheckedChangeListener(this);
        mCodeType.setOnCheckedChangeListener(this);
        mGreen = (RadioButton) findViewById(R.id.AEEC_RB_green);
        mRed = (RadioButton) findViewById(R.id.AEEC_RB_red);
        mYellow = (RadioButton) findViewById(R.id.AEEC_RB_yellow);
        mCodeCummins = (RadioButton) findViewById(R.id.AEEC_RB_cummins);
        mCodeCatapillar = (RadioButton) findViewById(R.id.AEEC_RB_catapillar);
        mStatus = (ToggleButton) findViewById(R.id.AEEC_TB_status_update);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mSave.getId()) {
            if (mCodeDesc.getText().toString().isEmpty()) {
                mCodeDesc.setError(getString(R.string.err_code_desc));
                mCodeDesc.requestFocus();
            } else if (mCodeType.getCheckedRadioButtonId() == -1) {
                Toast.makeText(EditEngineCodec.this, getString(R.string.err_color_type), Toast.LENGTH_SHORT).show();
            } else if (mOperation.getText().toString().isEmpty()) {
                mOperation.setError(getString(R.string.err_code_operation));
                mOperation.requestFocus();
            } else if (mCodeColor.getCheckedRadioButtonId() == -1) {
                Toast.makeText(EditEngineCodec.this, getString(R.string.err_color_codes), Toast.LENGTH_SHORT).show();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(EditEngineCodec.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.updateEngineCodec(sendValueWithRetrofit());
                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        mCustomProgressDialog.dismiss();
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(EditEngineCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Constants.ENGINE_CODEC.refresh();
                            finish();
                        } else {
                            Toast.makeText(EditEngineCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == mCodeColor.getId()) {
            mCodeColor.clearFocus();
            if (checkedId == mGreen.getId()) {
                checkedColor = mGreen.getText().toString();
            } else if (checkedId == mRed.getId()) {
                checkedColor = mRed.getText().toString();
            } else if (checkedId == mYellow.getId()) {
                checkedColor = mYellow.getText().toString();
            }
        } else if (group.getId() == mCodeType.getId()) {
            mCodeType.clearFocus();
            if (checkedId == mCodeCummins.getId()) {
                checkedType = "cummins";
            } else if (checkedId == mCodeCatapillar.getId()) {
                checkedType = "catapillar";
            }
        }
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_TYPE, checkedType);
        params.put(Constants.PARAMS_COLOR, checkedColor);
        params.put(Constants.PARAMS_ID, mData.getId());
        params.put(Constants.PARAMS_DESC, mCodeDesc.getText().toString());
        params.put(Constants.PARAMS_OPERATION, mOperation.getText().toString());
        return params;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isStatusUpdate) {
                Constants.ENGINE_CODEC.refresh();
                isStatusUpdate = false;
                finish();
            } else {
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void callStatusApi(boolean status) {
        String value = "";
        if (status) {
            value = "0";
        } else {
            value = "1";
        }

        if (!isNetworkAvailable(this)) {
            Toast.makeText(EditEngineCodec.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.addEngineCodec(mData.getId(), value);
            final String finalValue = value;
            loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                @Override
                public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        if (finalValue.equalsIgnoreCase("0")) {
                            mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditEngineCodec.this, R.drawable.button_background_green));
                        } else {
                            mStatus.setBackgroundDrawable(ContextCompat.getDrawable(EditEngineCodec.this, R.drawable.button_background_red));
                        }
                        isStatusUpdate = true;
                        Toast.makeText(EditEngineCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditEngineCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
