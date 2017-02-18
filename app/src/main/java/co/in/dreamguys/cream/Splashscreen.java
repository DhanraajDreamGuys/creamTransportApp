package co.in.dreamguys.cream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.CountriesAPI;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.SessionHandler;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 14-02-2017.
 */

public class Splashscreen extends AppCompatActivity {

    private static String TAG = Splashscreen.class.getName();
    CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mCustomProgressDialog = new CustomProgressDialog(this);
        getFromandTo();
    }

    private void getFromandTo() {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(Splashscreen.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CountriesAPI.CountryListResponse> loginCall = apiService.getCountries();
            mCustomProgressDialog.showDialog();
            loginCall.enqueue(new Callback<CountriesAPI.CountryListResponse>() {
                @Override
                public void onResponse(Call<CountriesAPI.CountryListResponse> call, Response<CountriesAPI.CountryListResponse> response) {
                    mCustomProgressDialog.dismiss();
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
                public void onFailure(Call<CountriesAPI.CountryListResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }
}
