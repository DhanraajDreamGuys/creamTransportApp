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
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.LoginAPI;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.SessionHandler;
import co.in.dreamguys.cream.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 09-02-2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button login;
    Toolbar mToolbar;
    public static String TAG = Login.class.getName();
    CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCustomProgressDialog = new CustomProgressDialog(this);

        initviews();

    }


    public void initviews() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        email = (EditText) findViewById(R.id.ALET_email);
        password = (EditText) findViewById(R.id.ALET_password);
        login = (Button) findViewById(R.id.ALBT_signin);
        login.setOnClickListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.ALBT_signin) {
            if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                email.setError(getString(R.string.err_email));
                password.setError(getString(R.string.err_password));
                email.requestFocus();
                password.requestFocus();
            } else if (email.getText().toString().isEmpty()) {
                email.setError(getString(R.string.err_email));
                email.requestFocus();
            } else if (!Util.isValidEmail(email.getText().toString())) {
                email.setError(getString(R.string.err_emailInvalid));
                email.requestFocus();
            } else if (password.getText().toString().isEmpty()) {
                password.setError(getString(R.string.err_password));
                password.requestFocus();
            } else if (password.getText().toString().length() < 5) {
                password.setError(getString(R.string.err_password_length));
                password.requestFocus();
            } else if (!Util.isNetworkAvailable(this)) {
                Toast.makeText(Login.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<LoginAPI.LoginResponse> loginCall = apiService.getLoginDetails(sendValueWithRetrofit());

                loginCall.enqueue(new Callback<LoginAPI.LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginAPI.LoginResponse> call, Response<LoginAPI.LoginResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            SessionHandler.setStringPref(Constants.USER_ID, response.body().getData().get(0).getId());
                            Snackbar.make(v, response.body().getMessage(), Snackbar.LENGTH_SHORT);
                            ActivityConstants.callPage(Login.this, AdminMenu.class);
                            finish();
                        } else {
                            Snackbar.make(v, response.body().getMessage(), Snackbar.LENGTH_SHORT);
                        }
                        mCustomProgressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginAPI.LoginResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                        mCustomProgressDialog.dismiss();
                    }
                });

            }

        }
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_EMAILID, email.getText().toString());
        params.put(Constants.PARAMS_PASSWORD, password.getText().toString());
        return params;
    }

}
