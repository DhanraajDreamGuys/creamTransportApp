package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import co.in.dreamguys.cream.adapter.AlertAllAdapter;
import co.in.dreamguys.cream.adapter.AlertTemplateAdapter;
import co.in.dreamguys.cream.apis.AlertemplateAPI;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.interfaces.TemplateInterface;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;
import static co.in.dreamguys.cream.utils.Util.setListViewHeightBasedOnChildren;

/**
 * Created by user5 on 09-03-2017.
 */

public class AlertSheet extends AppCompatActivity implements View.OnClickListener, TemplateInterface {
    Toolbar mToolbar;
    ListView mAlertsWidgets;
    AlertAllAdapter aAlertAllAdapter;
    AlertTemplateAdapter aAlertTemplateAdapter;
    TextView mSelectTemplates;
    View vAlertLayout;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = AlertSheet.class.getName();
    AlertDialog aAlertDialog;
    ArrayList<String> selectedIds = new ArrayList<String>();
    ArrayList<String> selectedEmails = new ArrayList<String>();
    Button mSend;
    String templateId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_all);

        Constants.ALERTSHEET = this;

        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        getTemplateLists();

        aAlertAllAdapter = new AlertAllAdapter(AlertSheet.this, Constants.driverList);
        mAlertsWidgets.setAdapter(aAlertAllAdapter);
        aAlertAllAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mAlertsWidgets);


    }

    private void getTemplateLists() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(AlertSheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<AlertemplateAPI.AlertTemplateResponse> loginCall = apiService.getTemplates();

            loginCall.enqueue(new Callback<AlertemplateAPI.AlertTemplateResponse>() {
                @Override
                public void onResponse(Call<AlertemplateAPI.AlertTemplateResponse> call, Response<AlertemplateAPI.AlertTemplateResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Constants.TemplateLists = response.body().getData();
                    } else {
                        Toast.makeText(AlertSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AlertemplateAPI.AlertTemplateResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }


    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        int pageIndicate = getIntent().getIntExtra(Constants.ALERT_PAGE_INDICATE, -1);
        switch (pageIndicate) {
            case 0:
                mToolbar.setTitle(getString(R.string.tool_alert_all_title));
                break;
            case 1:
                mToolbar.setTitle(getString(R.string.tool_alert_paysheet_title));
                break;
            case 2:
                mToolbar.setTitle(getString(R.string.tool_alert_templayte_title));
                break;
        }

        mToolbar.setTitleTextColor(Color.WHITE);
        mAlertsWidgets = (ListView) findViewById(R.id.AAA_LV_alerts_all);
        mSelectTemplates = (TextView) findViewById(R.id.AAA_TV_template);
        mSelectTemplates.setOnClickListener(this);

        mSend = (Button) findViewById(R.id.AAA_BT_send);
        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mSelectTemplates.getId()) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            vAlertLayout = getLayoutInflater().inflate(R.layout.dialog_template, null);
            mBuilder.setView(vAlertLayout);
            aAlertDialog = mBuilder.create();
            ListView mTemplateWidgets = (ListView) vAlertLayout.findViewById(R.id.DT_LV_templates);

            if (Constants.TemplateLists.size() > 0) {
                aAlertTemplateAdapter = new AlertTemplateAdapter(this, Constants.TemplateLists);
                mTemplateWidgets.setAdapter(aAlertTemplateAdapter);
                mTemplateWidgets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSelectTemplates.setText(Constants.TemplateLists.get(position).getName());
                        templateId = Constants.TemplateLists.get(position).getId();
                        aAlertDialog.dismiss();
                    }
                });
            }
            aAlertDialog.show();
        } else if (v.getId() == mSend.getId()) {
            if (mSelectTemplates.getText().toString().isEmpty()) {
                Snackbar.make(findViewById(R.id.AAA_LL_parent), getString(R.string.err_select_templates), Snackbar.LENGTH_LONG).show();
            } else if (!isNetworkAvailable(this)) {
                Snackbar.make(findViewById(R.id.AAA_LL_parent), getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.sendAlerts(sendValueWithRetrofit());

                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(AlertSheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Snackbar.make(findViewById(R.id.AAA_LL_parent), response.body().getMessage(), Snackbar.LENGTH_LONG).show();
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

    @Override
    public void templateID(ArrayList<String> id, ArrayList<String> email) {
        selectedIds = id;
        selectedEmails = email;
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();


        StringBuilder ids = new StringBuilder();
        String prefix = "";

        for (String userId : selectedIds) {
            ids.append(prefix);
            prefix = ",";
            ids.append(userId);
        }

        StringBuilder emails = new StringBuilder();
        for (String email : selectedEmails) {
            emails.append(prefix);
            prefix = ",";
            emails.append(email);
        }

        params.put(Constants.PARAMS_ID_USER, ids.toString());
        params.put(Constants.PARAMS_MAIL_USER, emails.toString());
        params.put(Constants.PARAMS_TEMP_ID, templateId);
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
