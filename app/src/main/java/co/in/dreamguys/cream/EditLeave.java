package co.in.dreamguys.cream;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.LeaveAPI;
import co.in.dreamguys.cream.apis.PrintURLAPI;
import co.in.dreamguys.cream.apis.UpdateUsersAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.Download;
import co.in.dreamguys.cream.utils.DownloadService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.getDateFormat;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;


/**
 * Created by user5 on 23-02-2017.
 */

public class EditLeave extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Toolbar mToolbar;
    TextView mName, mLocation, mDate, mLeave_From, mLeave_To, mFirstWorkDay, mLastWorkDay, mChooseDate, mOthers, mNoofDays;
    CheckBox mAnnualIncom, mLongService, mUnPaid, mSick, mBereavement, mMaternity;
    RadioGroup mStatus;
    EditText mPrintedName, mPosition, mSignature;
    ImageView mSignatureImage;
    Button mSave, mCancel;
    LeaveAPI.Datum mLeavedata;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = EditLeave.class.getName();
    private String status = "";
    private static final int PERMISSION_REQUEST_CODE = 1;
    String URL = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_leave_form);
        mCustomProgressDialog = new CustomProgressDialog(this);
        initWidgets();
        registerReceiver();
        mLeavedata = (LeaveAPI.Datum) getIntent().getSerializableExtra(Constants.EDIT_LEAVE_DATA);

        if (mLeavedata != null) {
            mName.setText(mLeavedata.getFirst_name() + " " + mLeavedata.getLast_name());
            mLocation.setText(mLeavedata.getLocation());
            String ldate = getDateFormat(mLeavedata.getLdate());
            String fromdate = getDateFormat(mLeavedata.getFromdate());
            String todate = getDateFormat(mLeavedata.getTodate());
            mDate.setText(ldate);
            mLeave_From.setText(fromdate);
            mLeave_To.setText(todate);
            mFirstWorkDay.setText(mLeavedata.getFromwork());
            mLastWorkDay.setText(mLeavedata.getLastwork());
            Picasso.with(EditLeave.this).load(mLeavedata.getImage()).into(mSignatureImage);

            if (mLeavedata.getLeavetype().equalsIgnoreCase(getString(R.string.check_annual_leave))) {
                mAnnualIncom.setChecked(true);
            } else if (mLeavedata.getLeavetype().equalsIgnoreCase(getString(R.string.long_service_leave))) {
                mLongService.setChecked(true);
            } else if (mLeavedata.getLeavetype().equalsIgnoreCase(getString(R.string.check_unpaid_leave))) {
                mUnPaid.setChecked(true);
            } else if (mLeavedata.getLeavetype().equalsIgnoreCase(getString(R.string.sick_leave))) {
                mSick.setChecked(true);
            } else if (mLeavedata.getLeavetype().equalsIgnoreCase(getString(R.string.check_bereavement_leave))) {
                mBereavement.setChecked(true);
            } else if (mLeavedata.getLeavetype().equalsIgnoreCase(getString(R.string.maternity_leave))) {
                mMaternity.setChecked(true);
            }
            mOthers.setText(mLeavedata.getOthers());
            mNoofDays.setText(mLeavedata.getNo_days());

        }


    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_edit_leave_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (TextView) findViewById(R.id.AELF_TV_name);
        mLocation = (TextView) findViewById(R.id.AELF_TV_location);
        mDate = (TextView) findViewById(R.id.AELF_TV_date);
        mFirstWorkDay = (TextView) findViewById(R.id.AELF_TV_from_date);
        mLeave_To = (TextView) findViewById(R.id.AELF_TV_date_to);
        mLeave_From = (TextView) findViewById(R.id.AELF_TV_date_from);
        mLastWorkDay = (TextView) findViewById(R.id.AELF_TV_to_last_date);
        mOthers = (TextView) findViewById(R.id.AELF_TV_others);
        mNoofDays = (TextView) findViewById(R.id.AELF_TV_no_days);
        mChooseDate = (TextView) findViewById(R.id.AELF_ET_date);
        mChooseDate.setOnClickListener(this);
        mPrintedName = (EditText) findViewById(R.id.AELF_ET_printed_name);
        mPosition = (EditText) findViewById(R.id.AELF_ET_position);
        mSignature = (EditText) findViewById(R.id.AELF_ET_signature);

        mStatus = (RadioGroup) findViewById(R.id.AELF_RG_group);
        mStatus.setOnCheckedChangeListener(this);
        mSignatureImage = (ImageView) findViewById(R.id.AELF_IV_signature);

        mAnnualIncom = (CheckBox) findViewById(R.id.AELF_CB_annual_leave);
        mLongService = (CheckBox) findViewById(R.id.AELF_CB_annual_lsl);
        mUnPaid = (CheckBox) findViewById(R.id.AELF_CB_unpaid_leave);
        mSick = (CheckBox) findViewById(R.id.AELF_CB_sick_leave);
        mBereavement = (CheckBox) findViewById(R.id.AELF_CB_bereavement_leave);
        mMaternity = (CheckBox) findViewById(R.id.AELF_CB_maternity_leave);

        mCancel = (Button) findViewById(R.id.AELF_ET_cancel);
        mSave = (Button) findViewById(R.id.AELF_ET_save);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_leave, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.MEL_print) {
            getPrintUrl();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPrintUrl() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<PrintURLAPI.PrintURLResponse> loginCall = apiService.getPrintURL(mLeavedata.getLid(), "LEAVE FORM");

            loginCall.enqueue(new Callback<PrintURLAPI.PrintURLResponse>() {
                @Override
                public void onResponse(Call<PrintURLAPI.PrintURLResponse> call, Response<PrintURLAPI.PrintURLResponse> response) {
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        URL = response.body().getData().getLink();
                        if (checkPermission()) {
                            startDownload(URL, Constants.LEAVEFORMSTRING);
                        } else {
                            requestPermission();
                        }
                    } else {
                        Toast.makeText(EditLeave.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    mCustomProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<PrintURLAPI.PrintURLResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AELF_ET_save) {
            if (mPrintedName.getText().toString().isEmpty()) {
                mPrintedName.setError(getString(R.string.err_print_name));
                mPrintedName.requestFocus();
            } else if (mSignature.getText().toString().isEmpty()) {
                mSignature.setError(getString(R.string.err_signature));
                mSignature.requestFocus();
            } else if (mChooseDate.getText().toString().isEmpty()) {
                mChooseDate.setError(getString(R.string.err_start_date));
                mChooseDate.requestFocus();
            } else if (status.isEmpty()) {
                Toast.makeText(this, getString(R.string.err_approved_status), Toast.LENGTH_SHORT).show();
            } else if (!isNetworkAvailable(this)) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                mCustomProgressDialog.showDialog();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<UpdateUsersAPI.UpdateUsersResponse> loginCall = apiService.saveLeaveForm(sendValueWithRetrofit());

                loginCall.enqueue(new Callback<UpdateUsersAPI.UpdateUsersResponse>() {
                    @Override
                    public void onResponse(Call<UpdateUsersAPI.UpdateUsersResponse> call, Response<UpdateUsersAPI.UpdateUsersResponse> response) {
                        if (response.body().getMeta().equals(Constants.SUCCESS)) {
                            Toast.makeText(EditLeave.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Constants.LEAVEFORM.notifyLeaveList();
                            finish();
                        } else {
                            Toast.makeText(EditLeave.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

        } else if (v.getId() == R.id.AELF_ET_cancel) {
            finish();
        } else if (v.getId() == R.id.AELF_ET_date) {
            Constants.AdminMenu.getFromDate(EditLeave.this, mChooseDate);
        }
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_ID, mLeavedata.getLid());
        params.put(Constants.PARAMS_APP_DATE, mChooseDate.getText().toString());
        params.put(Constants.PARAMS_SIGNATURE, mSignature.getText().toString());
        params.put(Constants.PARAMS_PRINTED_NAME, mPrintedName.getText().toString());
        params.put(Constants.PARAMS_APPROVE, status);
        return params;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.AELF_RG_group) {
            if (checkedId == R.id.AELF_RB_approve) {  // 0 means New
                status = "2";   // 2 means Approved
            } else {
                status = "3";  // 3 means Not Approved
            }
        }
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Message_Progress");
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("Message_Progress")) {
                Download download = intent.getParcelableExtra("download");
                if (download.getProgress() == 100) {
                    Snackbar.make(findViewById(R.id.AELF_LL_parent), "Download Completed...", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(URL, Constants.LEAVEFORMSTRING);
                } else {
                    Snackbar.make(findViewById(R.id.AELF_LL_parent), "Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void startDownload(String link, String LEAVEFORMSTRING) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(Constants.URL_LINK, link);
        intent.putExtra(Constants.TYPE, LEAVEFORMSTRING);
        startService(intent);
    }

}
