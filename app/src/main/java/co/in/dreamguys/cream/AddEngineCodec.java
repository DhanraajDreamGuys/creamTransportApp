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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
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

public class AddEngineCodec extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    Toolbar mToolbar;
    EditText mCodeNo, mCodeDesc, mOperation;
    RadioGroup mCodeColor, mCodeType;
    Button mSave;
    RadioButton mGreen, mRed, mYellow, mCodeCummins, mCodeCatapillar;
    String checkedColor = "", checkedType = "";
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = AddEngineCodec.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_engine_codec);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_engine_codec_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mCodeNo = (EditText) findViewById(R.id.AAEC_ET_code_no);
        mCodeType = (RadioGroup) findViewById(R.id.AAEC_RG_code_type);
        mCodeDesc = (EditText) findViewById(R.id.AAEC_ET_desc);
        mOperation = (EditText) findViewById(R.id.AAEC_ET_operation);
        mCodeColor = (RadioGroup) findViewById(R.id.AAEC_RG_code_colors);
        mSave = (Button) findViewById(R.id.AAEC_BT_save);
        mSave.setOnClickListener(this);
        mCodeColor.setOnCheckedChangeListener(this);
        mCodeType.setOnCheckedChangeListener(this);
        mGreen = (RadioButton) findViewById(R.id.AAEC_RB_green);
        mRed = (RadioButton) findViewById(R.id.AAEC_RB_red);
        mYellow = (RadioButton) findViewById(R.id.AAEC_RB_yellow);
        mCodeCummins = (RadioButton) findViewById(R.id.AAEC_RB_cummins);
        mCodeCatapillar = (RadioButton) findViewById(R.id.AAEC_RB_catapillar);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == mSave.getId()) {
            if (mCodeDesc.getText().toString().isEmpty()) {
                mCodeDesc.setError(getString(R.string.err_code_desc));
                mCodeDesc.requestFocus();
            } else if (mCodeNo.getText().toString().isEmpty()) {
                mCodeNo.setError(getString(R.string.err_code_no));
                mCodeNo.requestFocus();
            } else if (mCodeType.getCheckedRadioButtonId() == -1) {
                Toast.makeText(AddEngineCodec.this, getString(R.string.err_color_type), Toast.LENGTH_SHORT).show();
            } else if (mOperation.getText().toString().isEmpty()) {
                mOperation.setError(getString(R.string.err_code_operation));
                mOperation.requestFocus();
            } else if (mCodeColor.getCheckedRadioButtonId() == -1) {
                Toast.makeText(AddEngineCodec.this, getString(R.string.err_color_codes), Toast.LENGTH_SHORT).show();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(AddEngineCodec.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.addEngineCodec(sendValueWithRetrofit());
                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        mCustomProgressDialog.dismiss();
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(AddEngineCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Constants.ENGINE_CODEC.refresh();
                            finish();
                        } else {
                            Toast.makeText(AddEngineCodec.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        params.put(Constants.PARAMS_CODE, mCodeNo.getText().toString());
        params.put(Constants.PARAMS_DESC, mCodeDesc.getText().toString());
        params.put(Constants.PARAMS_OPERATION, mOperation.getText().toString());
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
