package co.in.dreamguys.cream;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.CustomFieldTypeAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 01-03-2017.
 */

public class AddCustomFields extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = AddCustomFields.class.getName();
    EditText mNewFields;
    TextView mType;
    Button mAdd;
    String mTypeId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_fields);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_add_custom_fields_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mNewFields = (EditText) findViewById(R.id.AACF_ET_new_fields);
        mType = (TextView) findViewById(R.id.AACF_ET_type);
        mAdd = (Button) findViewById(R.id.AACF_BT_add);
        mAdd.setOnClickListener(this);
        mType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mType.getId()) {
            alertCustomFieldType();
        } else if (v.getId() == mAdd.getId()) {
            if (mNewFields.getText().toString().isEmpty()) {
                mNewFields.setError(getString(R.string.err_field));
                mNewFields.requestFocus();
            } else if (mType.getText().toString().isEmpty()) {
                Toast.makeText(AddCustomFields.this, getString(R.string.custom_type), Toast.LENGTH_SHORT).show();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(AddCustomFields.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.updateUserStatus(sendValueWithRetrofit());
                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        mCustomProgressDialog.dismiss();
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(AddCustomFields.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Constants.CUSTOM_FIELD.refresh();
                            finish();
                        } else {
                            Toast.makeText(AddCustomFields.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure
                            (Call<UpdateUsersAPI.UpdateUsersResponse> call, Throwable t) {
                        Log.i(TAG, t.getMessage());
                        mCustomProgressDialog.dismiss();
                    }
                });
            }
        }
    }

    private void alertCustomFieldType() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddCustomFields.this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddCustomFields.this, android.R.layout.select_dialog_singlechoice);

        for (CustomFieldTypeAPI.Datum status : Constants.CUSTOM_FIELD_TYPE) {
            arrayAdapter.add(status.getName());
        }
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mType.setText(arrayAdapter.getItem(which));
                mTypeId = Constants.CUSTOM_FIELD_TYPE.get(which).getType();
                dialog.dismiss();
            }
        });
        builderSingle.show();

    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_CUSTOM_TYPE, mTypeId);
        params.put(Constants.PARAMS_CUSTOM_NAME, mNewFields.getText().toString());
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
