package co.in.dreamguys.cream;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.CustomFieldTypeAPI;
import co.in.dreamguys.cream.apis.ListCountriesAPI;
import co.in.dreamguys.cream.apis.UserTypeAPI;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.SessionHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 14-02-2017.
 */

public class Splashscreen extends AppCompatActivity {

    private static String TAG = Splashscreen.class.getName();
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mProgressBar = (ProgressBar) findViewById(R.id.AS_PB_progress);
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        if (!isNetworkAvailable(getApplicationContext())) {
            Toast.makeText(Splashscreen.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            getFromandTo();
            new CountryList().execute();
            new UserType().execute();
            new CustomFieldTypes().execute();
        }
    }

    private void getFromandTo() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(Splashscreen.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<BranchAPI.CountryListResponse> loginCall = apiService.getCountries();
            loginCall.enqueue(new Callback<BranchAPI.CountryListResponse>() {
                @Override
                public void onResponse(Call<BranchAPI.CountryListResponse> call, Response<BranchAPI.CountryListResponse> response) {
//                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Constants.countries = response.body().getData();
                        if (!SessionHandler.getStringPref(Constants.USER_ID).isEmpty()) {
                            ActivityConstants.callPage(Splashscreen.this, AdminMenu.class);
                            finish();
                        } else {
                            ActivityConstants.callPage(Splashscreen.this, Login.class);
                            finish();
                        }
                    } else {
                        Toast.makeText(Splashscreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BranchAPI.CountryListResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                }
            });
        }
    }

    private class CountryList extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            if (!isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(Splashscreen.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<ListCountriesAPI.CountryListResponse> loginCall = apiService.getListCountries();
                loginCall.enqueue(new Callback<ListCountriesAPI.CountryListResponse>() {
                    @Override
                    public void onResponse(Call<ListCountriesAPI.CountryListResponse> call, Response<ListCountriesAPI.CountryListResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Constants.countrieslist = response.body().getData();
                        } else {
                            Toast.makeText(Splashscreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListCountriesAPI.CountryListResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                    }
                });
            }
            return null;
        }
    }

    private class UserType extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            if (!isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(Splashscreen.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UserTypeAPI.UsersTypeResponse> loginCall = apiService.getUsersType();
                loginCall.enqueue(new Callback<UserTypeAPI.UsersTypeResponse>() {
                    @Override
                    public void onResponse(Call<UserTypeAPI.UsersTypeResponse> call, Response<UserTypeAPI.UsersTypeResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Constants.usertype = response.body().getData();
                        } else {
                            Toast.makeText(Splashscreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserTypeAPI.UsersTypeResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                    }
                });
            }
            return null;
        }
    }

    private class CustomFieldTypes extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            if (!isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(Splashscreen.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<CustomFieldTypeAPI.CustomFieldTypeResponse> loginCall = apiService.getCustomFieldsType();
                loginCall.enqueue(new Callback<CustomFieldTypeAPI.CustomFieldTypeResponse>() {
                    @Override
                    public void onResponse(Call<CustomFieldTypeAPI.CustomFieldTypeResponse> call, Response<CustomFieldTypeAPI.CustomFieldTypeResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Constants.CUSTOM_FIELD_TYPE = response.body().getData();
                        } else {
                            Toast.makeText(Splashscreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomFieldTypeAPI.CustomFieldTypeResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                    }
                });
            }
            return null;
        }
    }
}
