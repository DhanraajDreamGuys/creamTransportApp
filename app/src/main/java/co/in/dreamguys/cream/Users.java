package co.in.dreamguys.cream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.adapter.UsersAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.apis.UsersAPI;
import co.in.dreamguys.cream.interfaces.TripsheetInterface;
import co.in.dreamguys.cream.model.UsersData;
import co.in.dreamguys.cream.model.UsersModel;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 14-02-2017.
 */

public class Users extends AppCompatActivity implements TripsheetInterface {

    Toolbar mToolbar;
    ListView mUserWidget;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = Users.class.getName();
    UsersAdapter aUsersAdapter;
    UsersData mUsersData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Constants.USERSCLASS = Users.this;
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();
        getUserLists();


    }

    public void getUserLists() {

        if (isNetworkAvailable(Users.this)) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UsersAPI.UsersResponse> loginCall = apiService.getUsers();
            mCustomProgressDialog.showDialog();
            loginCall.enqueue(new Callback<UsersAPI.UsersResponse>() {
                @Override
                public void onResponse(Call<UsersAPI.UsersResponse> call, Response<UsersAPI.UsersResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        fillUserLists(response.body().getData());
                    } else {
                        Toast.makeText(Users.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsersAPI.UsersResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillUserLists(List<UsersAPI.Datum> data) {
        UsersModel mUsersModel;
        mUsersData = new UsersData();
        ArrayList<UsersModel> mData = new ArrayList<UsersModel>();

        for (UsersAPI.Datum user : data) {
            mUsersModel = new UsersModel();
            mUsersModel.setStatus(user.getStatus());
            mUsersModel.setReason(user.getReason());
            mUsersModel.setAddress(user.getAddress());
            mUsersModel.setBranch_id(user.getBranch_id());
            mUsersModel.setCompany(user.getCompany());
            mUsersModel.setDate_created(user.getDate_created());
            mUsersModel.setDate_deleted(user.getDate_deleted());
            mUsersModel.setDelete_sts(user.getDelete_sts());
            mUsersModel.setEmail(user.getEmail());
            mUsersModel.setFirst_name(user.getFirst_name());
            mUsersModel.setLast_name(user.getLast_name());
            mUsersModel.setId(user.getId());
            mUsersModel.setImage(user.getImage());
            mUsersModel.setPassword(user.getPassword());
            mUsersModel.setPhone(user.getPhone());
            mUsersModel.setProfile_img(user.getProfile_img());
            mUsersModel.setPure_password(user.getPure_password());
            mUsersModel.setRef_str(user.getRef_str());
            mUsersModel.setUser_type(user.getUser_type());
            mUsersModel.setWorking_sts(user.getWorking_sts());
            mData.add(mUsersModel);
            mUsersData.setData(mData);
        }

        if (mUsersData.getData().size() > 0) {
            aUsersAdapter = new UsersAdapter(Users.this, mUsersData.getData());
            mUserWidget.setAdapter(aUsersAdapter);
        } else {
            Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
        }

    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_user_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mUserWidget = (ListView) findViewById(R.id.AU_LV_userlist);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.MAU_add_new_users) {
            startActivity(new Intent(this, AddUsers.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void view(String id, int viewType) {

    }

    @Override
    public void delete(String delete_id, final int position) {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UpdateUsersAPI.UpdateUsersResponse> repairsheet = apiService.getDeleteUser(delete_id);
        repairsheet.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
            @Override
            public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    Toast.makeText(Users.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    mUsersData.getData().remove(position);
                    aUsersAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Users.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
