package co.in.dreamguys.cream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.CompanyAPI;
import co.in.dreamguys.cream.apis.ListCountriesAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Constants.Company_data;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 10-02-2017.
 */

public class Company extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton mEditCompanyRecords;
    Toolbar mToolbar;
    TextView mCompanyName, mRegNo, mCompanyAddress, mCompanyPhone, mCompanyEmail, mCompanyFax, mCompanySkype;
    ImageView mCompanyLogo;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Company.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getCompanyRecords();
    }

    private void getCompanyRecords() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(Company.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CompanyAPI.CompanyResponse> loginCall = apiService.getCompany();

            loginCall.enqueue(new Callback<CompanyAPI.CompanyResponse>() {
                @Override
                public void onResponse(Call<CompanyAPI.CompanyResponse> call, Response<CompanyAPI.CompanyResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {

                        Company_data = response.body().getData().get(0);

                        mCompanyName.setText(response.body().getData().get(0).getName());
                        mRegNo.setText(response.body().getData().get(0).getReg_no());

                        for (ListCountriesAPI.Datum countryName : Constants.countrieslist) {
                            if (countryName.getId().equalsIgnoreCase(response.body().getData().get(0).getCountry()))
                                mCompanyAddress.setText(response.body().getData().get(0).getStreet() + "\n" + response.body().getData().get(0).getCity() + ", " +
                                        response.body().getData().get(0).getState() + " \n" + countryName.getCountry() + ", " +
                                        response.body().getData().get(0).getPin_code());
                        }


                        mCompanyPhone.setText(response.body().getData().get(0).getPhone());
                        mCompanyEmail.setText(response.body().getData().get(0).getEmail());
                        mCompanyFax.setText(response.body().getData().get(0).getFax());
                        mCompanySkype.setText(response.body().getData().get(0).getSkype());


                    } else {
                        Toast.makeText(Company.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CompanyAPI.CompanyResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        }
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_company_title));
        mToolbar.setTitleTextColor(Color.WHITE);


        mCompanyName = (TextView) findViewById(R.id.AC_TV_company_name);
        mRegNo = (TextView) findViewById(R.id.AC_TV_reg_no);
        mCompanyAddress = (TextView) findViewById(R.id.AC_TV_company_address);
        mCompanyPhone = (TextView) findViewById(R.id.AC_TV_company_phone);
        mCompanyEmail = (TextView) findViewById(R.id.AC_TV_company_email);
        mCompanyFax = (TextView) findViewById(R.id.AC_TV_company_fax_no);
        mCompanySkype = (TextView) findViewById(R.id.AC_TV_company_skype);
        mCompanyLogo = (ImageView) findViewById(R.id.AC_IV_logo);

        mEditCompanyRecords = (FloatingActionButton) findViewById(R.id.fab_edit_company);
        mEditCompanyRecords.setOnClickListener(this);
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
        if (v.getId() == mEditCompanyRecords.getId()) {
            Intent callEditCompany = new Intent(Company.this, EditCompany.class);
            callEditCompany.putExtra(Constants.EXTRAS_COMPANY, Company_data);
            startActivity(callEditCompany);
        }
    }
}
