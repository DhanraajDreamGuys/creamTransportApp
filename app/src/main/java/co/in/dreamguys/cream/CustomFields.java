package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.CustomFieldsAPI;
import co.in.dreamguys.cream.interfaces.FridgeCodeTypeInterface;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 01-03-2017.
 */

public class CustomFields extends AppCompatActivity implements FridgeCodeTypeInterface {
    Toolbar mToolbar;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = CustomFields.class.getName();
    LinearLayout mDynamicView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_fields);
        Constants.CUSTOM_FIELD = this;
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        getCustomFields();
    }

    private void getCustomFields() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(CustomFields.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CustomFieldsAPI.CustomFieldResponse> loginCall = apiService.getCustomFields();

            loginCall.enqueue(new Callback<CustomFieldsAPI.CustomFieldResponse>() {
                @Override
                public void onResponse(Call<CustomFieldsAPI.CustomFieldResponse> call, Response<CustomFieldsAPI.CustomFieldResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        dynamicScrolledCustomFieldsViews(response.body().getData());
                    } else {
                        Toast.makeText(CustomFields.this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CustomFieldsAPI.CustomFieldResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        }
    }

    private void dynamicScrolledCustomFieldsViews(List<CustomFieldsAPI.Datum> data) {
        if (mDynamicView != null)
            mDynamicView.removeAllViews();
        for (CustomFieldsAPI.Datum mData : data) {
            View sectionLayout = getLayoutInflater().inflate(R.layout.adapter_category_name, null);
            TextView mCategoryName = (TextView) sectionLayout.findViewById(R.id.ACN_TV_category_name);
            mCategoryName.setText(mData.getCategory());
            mDynamicView.addView(sectionLayout);
            for (CustomFieldsAPI.SubCategory subCategory : mData.getSubCategory()) {
                View contentLayout = getLayoutInflater().inflate(R.layout.adapter_sub_category, null);
                TextView mSubCategoryName = (TextView) contentLayout.findViewById(R.id.ASC_TV_subcategory);
                TextView mStatus = (TextView) contentLayout.findViewById(R.id.ASC_TV_status);
                TextView mEdit = (TextView) contentLayout.findViewById(R.id.ASC_TV_edit);
                mSubCategoryName.setText(subCategory.getName());
                if (subCategory.getStatus().equalsIgnoreCase("0")) {
                    mStatus.setText(getString(R.string.active));
                    mStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background_green));
                } else {
                    mStatus.setText(getString(R.string.inactive));
                    mStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background_red));
                }

                mEdit.setOnClickListener(new editCustomFields(subCategory));


                mDynamicView.addView(contentLayout);
            }
        }
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_custom_fields_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mDynamicView = (LinearLayout) findViewById(R.id.ACF_LL_dynamic_custom_fields);
    }

    @Override
    public void typeSearch(String type) {

    }

    @Override
    public void refresh() {
        getCustomFields();
    }

    private class editCustomFields implements View.OnClickListener {
        CustomFieldsAPI.SubCategory subCategory;

        editCustomFields(CustomFieldsAPI.SubCategory subCategory) {
            this.subCategory = subCategory;
        }

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_trips_hours, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_add_trip_hours) {
            ActivityConstants.callPage(CustomFields.this, AddCustomFields.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
