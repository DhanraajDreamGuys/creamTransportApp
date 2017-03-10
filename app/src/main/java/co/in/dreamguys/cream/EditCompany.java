package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import co.in.dreamguys.cream.apis.CompanyAPI;
import co.in.dreamguys.cream.apis.ListCountriesAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.buildCountryAlert;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 10-02-2017.
 */

public class EditCompany extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    EditText mCompanyName, mRegNo, mCompanyStreet, mCompanyCity, mCompanyState,
            mCompanyPhone, mCompanyEmail, mCompanyFax, mCompanySkype, mCompanyPhone2, mCompanyPinCode, mCompanyNotes;
    TextView mCompanyCountry;
    Button mSave;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = EditCompany.class.getName();
    CompanyAPI.Datum mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);

        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        mData = (CompanyAPI.Datum) getIntent().getSerializableExtra(Constants.EXTRAS_COMPANY);
        if (mData != null) {
            mCompanyName.setText(mData.getName());
            mRegNo.setText(mData.getReg_no());
            mCompanyEmail.setText(mData.getEmail());
            mCompanyPhone.setText(mData.getPhone());
            mCompanyPhone2.setText(mData.getPhone2());
            mCompanyFax.setText(mData.getFax());
            mCompanySkype.setText(mData.getSkype());
            mCompanyStreet.setText(mData.getStreet());
            mCompanyCity.setText(mData.getCity());
            mCompanyState.setText(mData.getState());
            mCompanyPinCode.setText(mData.getPin_code());
            mCompanyNotes.setText(mData.getNotes());
            for (ListCountriesAPI.Datum countryName : Constants.countrieslist) {
                if (countryName.getId().equalsIgnoreCase(mData.getCountry()))
                    mCompanyCountry.setText(countryName.getCountry());
            }

        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_edit_company_title));
        mToolbar.setTitleTextColor(Color.WHITE);


        mCompanyName = (EditText) findViewById(R.id.AEC_ET_company_name);
        mRegNo = (EditText) findViewById(R.id.AEC_ET_reg_no);
        mCompanyStreet = (EditText) findViewById(R.id.AEC_ET_company_street_no);
        mCompanyCity = (EditText) findViewById(R.id.AEC_ET_company_city);
        mCompanyState = (EditText) findViewById(R.id.AEC_ET_company_state);
        mCompanyPinCode = (EditText) findViewById(R.id.AEC_ET_company_pincode);
        mCompanyPhone2 = (EditText) findViewById(R.id.AEC_ET_company_phone2);
        mCompanyNotes = (EditText) findViewById(R.id.AEC_ET_company_notes);
        mCompanyPhone = (EditText) findViewById(R.id.AEC_ET_company_phone_1);
        mCompanyEmail = (EditText) findViewById(R.id.AEC_ET_company_email);
        mCompanyFax = (EditText) findViewById(R.id.AEC_ET_company_fax);
        mCompanySkype = (EditText) findViewById(R.id.AEC_ET_company_skype);

        mCompanyCountry = (TextView) findViewById(R.id.AEC_ET_company_country);
        mCompanyCountry.setOnClickListener(this);
        mSave = (Button) findViewById(R.id.AEC_BT_save);
        mSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mCompanyCountry.getId()) {
            buildCountryAlert(EditCompany.this, getLayoutInflater(), mCompanyCountry);
        } else if (v.getId() == mSave.getId()) {
            if (mCompanyName.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_company_name));
            } else if (mRegNo.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_reg_no));
            } else if (mCompanyEmail.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_email));
            } else if (!Util.isValidEmail(mCompanyEmail.getText().toString())) {
                showErrorToast(getString(R.string.err_emailInvalid));
            } else if (mCompanyPhone.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_phone));
            } else if (mCompanyStreet.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_street_no));
            } else if (mCompanyCity.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_city));
            } else if (mCompanyState.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_state));
            } else if (mCompanyCountry.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_country));
            } else if (mCompanyPinCode.getText().toString().isEmpty()) {
                showErrorToast(getString(R.string.err_psotal_code));
            } else if (!isNetworkAvailable(this)) {
                showErrorToast(getString(R.string.no_internet_connection));
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.saveCompanyDetails(sendValueWithRetrofit());

                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(EditCompany.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditCompany.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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


    private void showErrorToast(String msg) {
        Snackbar.make(findViewById(R.id.AEC_LL_parent), msg, Snackbar.LENGTH_SHORT).show();
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_ID, mData.getId());
        params.put(Constants.PARAMS_LINK_NAME, mCompanyName.getText().toString());
        params.put(Constants.PARAMS_REG_NO, mRegNo.getText().toString());
        params.put(Constants.PARAMS_EMAIL, mCompanyEmail.getText().toString());
        params.put(Constants.PARAMS_PHONE, mCompanyPhone.getText().toString());
        params.put(Constants.PARAMS_PHONE_NO_2, mCompanyPhone2.getText().toString());
        params.put(Constants.PARAMS_FAX, mCompanyFax.getText().toString());
        params.put(Constants.PARAMS_SKYPE, mCompanySkype.getText().toString());
        params.put(Constants.PARAMS_STREET, mCompanyStreet.getText().toString());
        params.put(Constants.PARAMS_CITY, mCompanyCity.getText().toString());
        params.put(Constants.PARAMS_STATE, mCompanyState.getText().toString());

        for (ListCountriesAPI.Datum countryName : Constants.countrieslist) {
            if (countryName.getCountry().equalsIgnoreCase(mCompanyCountry.getText().toString()))
                params.put(Constants.PARAMS_COUNTRY, countryName.getId());
        }

        params.put(Constants.PARAMS_PIN_CODE, mCompanyPinCode.getText().toString());
        params.put(Constants.PARAMS_NOTES, mCompanyNotes.getText().toString());
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
