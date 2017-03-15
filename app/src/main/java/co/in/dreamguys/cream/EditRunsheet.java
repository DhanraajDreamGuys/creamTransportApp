package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DriverListsAPI;
import co.in.dreamguys.cream.apis.RunsheetAPI;
import co.in.dreamguys.cream.apis.UpdateSheetAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Util.getDateFormat;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;

/**
 * Created by user5 on 20-02-2017.
 */

public class EditRunsheet extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Toolbar mToolbar;
    TextView mEditChooseDriver, mEditFrom, mEditTo, mEditDate, mEditSetDate;
    EditText mEditTruckNo, mEditTrailers, mEditManifestNo, mEditDollyNo, mEditLoadavailable, mEditFromWho, mEditLoadDue, mEditBy, mEditChangeOver, mEditDriver, mEditComments, mEditDynamicET;
    LinearLayout mEditAddNewTrailerLayout;
    CheckBox mEditExpress, mEditNoDanger, mEditHasDanger, mEditProductMarket;
    Button mEditSend, mEditCancel, mEditAddTrail, mEditRemoveTrail;
    String checkedExpress = "", checkedNodanger = "", checkedHasDanger = "", checkedProduct = "", checkedItems = "", trailers = "";

    int count = 0;
    List<EditText> mAllEditText = new ArrayList<EditText>();

    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = EditRunsheet.class.getName();
    RunsheetAPI.Datum mEditRunsheet;
    View mTripView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomProgressDialog = new CustomProgressDialog(this);
        mTripView = getLayoutInflater().inflate(R.layout.activity_edit_runsheet, null);
        setContentView(mTripView);
        intiEditWidgets();
        mEditRunsheet = (RunsheetAPI.Datum) getIntent().getSerializableExtra(Constants.E_RUNSHEET_DATA);
        upateViewTrip();

    }

    private void intiEditWidgets() {
        mToolbar = (Toolbar) mTripView.findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_edit_trip_title));

        mToolbar.setTitleTextColor(Color.WHITE);


        mEditChooseDriver = (TextView) mTripView.findViewById(R.id.AERS_TV_choose_driver);
        mEditChooseDriver.setOnClickListener(this);
        mEditFrom = (TextView) mTripView.findViewById(R.id.AERS_TV_from);
        mEditFrom.setOnClickListener(this);
        mEditTo = (TextView) mTripView.findViewById(R.id.AERS_TV_to);
        mEditTo.setOnClickListener(this);
        mEditDate = (TextView) mTripView.findViewById(R.id.AERS_TV_date);
        mEditDate.setOnClickListener(this);
        mEditSetDate = (TextView) mTripView.findViewById(R.id.AERS_TV_set_date);
        mEditSetDate.setOnClickListener(this);
        mEditTruckNo = (EditText) mTripView.findViewById(R.id.AERS_ET_truck);
        mEditTrailers = (EditText) mTripView.findViewById(R.id.AERS_ET_traliers);
        mEditManifestNo = (EditText) mTripView.findViewById(R.id.AERS_ET_manifest);
        mEditDollyNo = (EditText) mTripView.findViewById(R.id.AERS_ET_dolly);
        mEditLoadavailable = (EditText) mTripView.findViewById(R.id.AERS_ET_load_availble_after);
        mEditLoadDue = (EditText) mTripView.findViewById(R.id.AERS_ET_load_due_in);
        mEditFromWho = (EditText) mTripView.findViewById(R.id.AERS_ET_from);
        mEditBy = (EditText) mTripView.findViewById(R.id.AERS_ET_by);
        mEditChangeOver = (EditText) mTripView.findViewById(R.id.AERS_ET_change_over);
        mEditDriver = (EditText) mTripView.findViewById(R.id.AERS_ET_driver);
        mEditComments = (EditText) mTripView.findViewById(R.id.AERS_ET_comment);
        mEditSend = (Button) mTripView.findViewById(R.id.AERS_BT_send);
        mEditCancel = (Button) mTripView.findViewById(R.id.AERS_BT_cancel);
        mEditAddTrail = (Button) mTripView.findViewById(R.id.AERS_BT_add);
        mEditRemoveTrail = (Button) mTripView.findViewById(R.id.AERS_BT_remove);
        mEditAddNewTrailerLayout = (LinearLayout) mTripView.findViewById(R.id.AERS_LL_add_new_trailers);
        mEditExpress = (CheckBox) mTripView.findViewById(R.id.AERS_CB_exp_general);
        mEditNoDanger = (CheckBox) mTripView.findViewById(R.id.AERS_CB_no_danger_goods);
        mEditHasDanger = (CheckBox) mTripView.findViewById(R.id.AERS_CB_has_danger_goods);
        mEditProductMarket = (CheckBox) mTripView.findViewById(R.id.AERS_CB_product_market);

        mEditSend.setOnClickListener(this);
        mEditCancel.setOnClickListener(this);
        mEditAddTrail.setOnClickListener(this);
        mEditRemoveTrail.setOnClickListener(this);
        mEditExpress.setOnCheckedChangeListener(this);
        mEditNoDanger.setOnCheckedChangeListener(this);
        mEditHasDanger.setOnCheckedChangeListener(this);
        mEditProductMarket.setOnCheckedChangeListener(this);


    }

    private void upateViewTrip() {
        mEditChooseDriver.setText(mEditRunsheet.getFirst_name() + " " + mEditRunsheet.getLast_name());
        String cdate = getDateFormat(mEditRunsheet.getCreated_date());
        mEditDate.setText(cdate);
        mEditTruckNo.setText(mEditRunsheet.getTruck());
        for (int i = 0; i < Constants.countries.size(); i++) {
            if (Constants.countries.get(i).getId().equalsIgnoreCase(mEditRunsheet.getFrom())) {
                mEditFrom.setText(Constants.countries.get(i).getName());
            } else if (Constants.countries.get(i).getId().equalsIgnoreCase(mEditRunsheet.getTo())) {
                mEditTo.setText(Constants.countries.get(i).getName());
            }
        }
        mEditManifestNo.setText(mEditRunsheet.getManifest_no());
        mEditDollyNo.setText(mEditRunsheet.getDolly());

        try {
            String data = mEditRunsheet.getTrailers();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(data);
            if (jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (i == 0) {
                        mEditTrailers.setText(jsonArray.get(0).toString().replace("\"", ""));
                    } else {
                        mEditRemoveTrail.setVisibility(View.VISIBLE);
                        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        mEditDynamicET = new EditText(this);
                        mAllEditText.add(mEditDynamicET);
                        mEditDynamicET.setLayoutParams(lparams);
                        mEditDynamicET.setBackgroundResource(R.drawable.border_gray_box);
                        mEditDynamicET.setMaxLines(1);
                        mEditDynamicET.setTextSize(12f);
                        mEditDynamicET.setPadding(10, 10, 10, 10);
                        mEditDynamicET.setInputType(InputType.TYPE_CLASS_NUMBER);
                        mEditDynamicET.setId(i);
                        mEditAddNewTrailerLayout.addView(mEditDynamicET);
                        mEditDynamicET.setText(jsonArray.get(i).toString().replace("\"", ""));
                        count = i;
                    }
                }
            }

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        JsonObject loadFrom = new Gson().fromJson(mEditRunsheet.getLoad_from(), JsonObject.class);
        mEditLoadavailable.setText(loadFrom.get("ltime").toString().replace("\"", ""));
        mEditFromWho.setText(loadFrom.get("lfrom").toString().replace("\"", ""));

        JsonObject loadDue = new Gson().fromJson(mEditRunsheet.getLoad_due(), JsonObject.class);
        mEditLoadDue.setText(loadDue.get("ldfrom").toString().replace("\"", ""));
        mEditBy.setText(loadDue.get("ldue").toString().replace("\"", ""));
        mEditSetDate.setText(loadDue.get("ldate").toString().replace("\"", ""));

        JsonObject covertedObject = new Gson().fromJson(mEditRunsheet.getChangeover(), JsonObject.class);
        mEditChangeOver.setText(covertedObject.get("ctruck").toString().replace("\"", ""));
        mEditDriver.setText(covertedObject.get("cdriver").toString().replace("\"", ""));

        String[] mLoadType = mEditRunsheet.getLoad_type().split(",");
        for (String aMLoadType : mLoadType) {
            if (aMLoadType.equalsIgnoreCase(getString(R.string.express))) {
                mEditExpress.setChecked(true);
                checkedExpress = aMLoadType;
            }
            if (aMLoadType.equalsIgnoreCase(getString(R.string.no_danger))) {
                mEditNoDanger.setChecked(true);
                checkedNodanger = aMLoadType;
            }
            if (aMLoadType.equalsIgnoreCase(getString(R.string.product))) {
                mEditProductMarket.setChecked(true);
                checkedProduct = aMLoadType;
            }
            if (aMLoadType.equalsIgnoreCase(getString(R.string.has_danger))) {
                mEditHasDanger.setChecked(true);
                checkedHasDanger = aMLoadType;
            }
            checkedItems = checkedExpress + checkedNodanger + checkedHasDanger + checkedProduct;
            Log.i(TAG, checkedItems.trim());
        }

        mEditComments.setText(mEditRunsheet.getAdmin_cmt());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.AERS_CB_exp_general) {
            if (isChecked) {
                checkedExpress = buttonView.getText().toString() + ",";
            } else {
                checkedExpress = "";
            }
        } else if (buttonView.getId() == R.id.AERS_CB_has_danger_goods) {
            if (isChecked) {
                checkedHasDanger = buttonView.getText().toString() + ",";
            } else {
                checkedHasDanger = "";
            }
        } else if (buttonView.getId() == R.id.AERS_CB_no_danger_goods) {
            if (isChecked) {
                checkedNodanger = buttonView.getText().toString() + ",";
            } else {
                checkedNodanger = "";
            }
        } else if (buttonView.getId() == R.id.AERS_CB_product_market) {
            if (isChecked) {
                checkedProduct = buttonView.getText().toString() + ",";
            } else {
                checkedProduct = "";
            }
        }
        checkedItems = checkedExpress + checkedNodanger + checkedHasDanger + checkedProduct;
        Log.i(TAG, checkedItems.trim());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AERS_TV_choose_driver) {
            Constants.AdminMenu.getDrivers(EditRunsheet.this, mEditChooseDriver);
        } else if (v.getId() == R.id.AERS_TV_from) {
            Constants.AdminMenu.getLocations(EditRunsheet.this, mEditFrom, Constants.FromString);
        } else if (v.getId() == R.id.AERS_TV_to) {
            Constants.AdminMenu.getLocations(EditRunsheet.this, mEditTo, Constants.ToString);
        } else if (v.getId() == R.id.AERS_TV_date) {
            Constants.AdminMenu.getFromDate(EditRunsheet.this, mEditDate);
        } else if (v.getId() == R.id.AERS_TV_set_date) {
            Constants.AdminMenu.getFromDate(EditRunsheet.this, mEditSetDate);
        } else if (v.getId() == R.id.AERS_BT_send) {
            upadteTrip();
        } else if (v.getId() == R.id.AERS_BT_cancel) {
            finish();
        } else if (v.getId() == R.id.AERS_BT_add) {
            count++;
            addNewDynamicTextView();
        } else if (v.getId() == R.id.AERS_BT_remove) {
            removeDynamicTextView();
        }
    }

    private void upadteTrip() {
        if (mEditChooseDriver.getText().toString().isEmpty()) {
            mEditChooseDriver.setError(getString(R.string.err_driver_name));
            mEditChooseDriver.requestFocus();
        } else if (mEditDate.getText().toString().isEmpty()) {
            mEditDate.setError(getString(R.string.err_start_date));
            mEditDate.requestFocus();
        } else if (mEditTruckNo.getText().toString().isEmpty()) {
            mEditTruckNo.setError(getString(R.string.err_truck_no));
            mEditTruckNo.requestFocus();
        } else if (mEditTrailers.getText().toString().isEmpty()) {
            mEditTrailers.setError(getString(R.string.err_trailers));
            mEditTrailers.requestFocus();
        } else if (mEditTrailers.getText().toString().isEmpty()) {
            mEditTrailers.setError(getString(R.string.err_trailers));
            mEditTrailers.requestFocus();
        } else if (mEditManifestNo.getText().toString().isEmpty()) {
            mEditManifestNo.setError(getString(R.string.err_manifest_no));
            mEditManifestNo.requestFocus();
        } else if (mEditDollyNo.getText().toString().isEmpty()) {
            mEditDollyNo.setError(getString(R.string.err_dolly_no));
            mEditDollyNo.requestFocus();
        } else if (mEditLoadavailable.getText().toString().isEmpty()) {
            mEditLoadavailable.setError(getString(R.string.err_load_available));
            mEditLoadavailable.requestFocus();
        } else if (mEditFromWho.getText().toString().isEmpty()) {
            mEditFromWho.setError(getString(R.string.err_from_who));
            mEditFromWho.requestFocus();
        } else if (mEditLoadDue.getText().toString().isEmpty()) {
            mEditLoadDue.setError(getString(R.string.err_load_due));
            mEditLoadDue.requestFocus();
        } else if (mEditBy.getText().toString().isEmpty()) {
            mEditBy.setError(getString(R.string.err_by));
            mEditBy.requestFocus();
        } else if (mEditSetDate.getText().toString().isEmpty()) {
            mEditSetDate.setError(getString(R.string.err_date_set));
            mEditSetDate.requestFocus();
        } else if (mEditChangeOver.getText().toString().isEmpty()) {
            mEditChangeOver.setError(getString(R.string.err_change_over));
            mEditChangeOver.requestFocus();
        } else if (mEditDriver.getText().toString().isEmpty()) {
            mEditDriver.setError(getString(R.string.err_driver));
            mEditDriver.requestFocus();
        } else if (mEditTo.getText().toString().isEmpty()) {
            mEditTo.setError(getString(R.string.err_to));
            mEditTo.requestFocus();
        } else if (!isNetworkAvailable(this)) {
            Toast.makeText(EditRunsheet.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateSheetAPI.UpdatePaysheetResponse> loginCall = apiService.getUpdateRunsheet(sendValueWithRetrofit());
            loginCall.enqueue(new Callback<UpdateSheetAPI.UpdatePaysheetResponse>() {
                @Override
                public void onResponse(Call<UpdateSheetAPI.UpdatePaysheetResponse> call, Response<UpdateSheetAPI.UpdatePaysheetResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        Toast.makeText(EditRunsheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Constants.eRUNSHEET.searchNotify();
                        finish();
                    } else {
                        Toast.makeText(EditRunsheet.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateSheetAPI.UpdatePaysheetResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });
        }
    }


    private void removeDynamicTextView() {
        EditText mRemoveID;
        if (count == 1) {
            mEditRemoveTrail.setVisibility(View.GONE);
            mRemoveID = (EditText) mEditAddNewTrailerLayout.findViewById(count);
            mEditAddNewTrailerLayout.removeView(mRemoveID);
            mAllEditText.clear();
            count = 0;
        } else {
            mRemoveID = (EditText) mEditAddNewTrailerLayout.findViewById(count);
            mEditAddNewTrailerLayout.removeView(mRemoveID);
            mAllEditText.remove((count - 1));
            count = count - 1;
        }
    }

    private void addNewDynamicTextView() {
        mEditRemoveTrail.setVisibility(View.VISIBLE);
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mEditDynamicET = new EditText(this);
        mAllEditText.add(mEditDynamicET);
        mEditDynamicET.setLayoutParams(lparams);
        mEditDynamicET.setBackgroundResource(R.drawable.border_gray_box);
        mEditDynamicET.setMaxLines(1);
        mEditDynamicET.setPadding(10, 10, 10, 10);
        mEditDynamicET.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditDynamicET.setId(count);
        mEditAddNewTrailerLayout.addView(mEditDynamicET);
    }

    private HashMap<String, String> sendValueWithRetrofit() {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAMS_TID, mEditRunsheet.getTrip_id());
        for (DriverListsAPI.Datum data : Constants.driverList) {
            String appendValues = data.getFirst_name() + " " + data.getLast_name();
            if (appendValues.trim().equalsIgnoreCase(mEditChooseDriver.getText().toString().trim())) {
                params.put(Constants.PARAMS_USER_ID, data.getId());
                Log.i(TAG, data.getId());
            }
        }
        params.put(Constants.PARAMS_TRUCK_NO, mEditTruckNo.getText().toString());
        if (mEditDynamicET != null && mAllEditText.size() > 0) {
            String appendValues = "";
            for (int i = 0; i < mAllEditText.size(); i++) {
                appendValues += mAllEditText.get(i).getText().toString() + ", ";
            }
            trailers = mEditTrailers.getText().toString() + "," + appendValues;
            params.put(Constants.PARAMS_TRAILERS, trailers);
        } else {
            trailers = mEditTrailers.getText().toString();
            params.put(Constants.PARAMS_TRAILERS, trailers);
        }
        params.put(Constants.PARAMS_MNO, mEditManifestNo.getText().toString());
        params.put(Constants.PARAMS_DOLLYNO, mEditDollyNo.getText().toString());
        params.put(Constants.PARAMS_LTIME, mEditLoadavailable.getText().toString());
        params.put(Constants.PARAMS_LFROM, mEditFromWho.getText().toString());
        params.put(Constants.PARAMS_LDUE, mEditLoadDue.getText().toString());
        params.put(Constants.PARAMS_IDFROM, mEditBy.getText().toString());
        params.put(Constants.PARAMS_CTRUCK, mEditChangeOver.getText().toString());
        params.put(Constants.PARAMS_CDRIVER, mEditDriver.getText().toString());
        params.put(Constants.PARAMS_ITYPE, checkedItems);
        params.put(Constants.PARAMS_SDATE, mEditDate.getText().toString());
        params.put(Constants.PARAMS_LDATE, mEditSetDate.getText().toString());
        params.put(Constants.PARAMS_FROM, Constants.countries.get(Constants.From).getId());
        params.put(Constants.PARAMS_TRIP_TO, Constants.countries.get(Constants.To).getId());
        params.put(Constants.PARAMS_ADMIN_COMMENT, mEditComments.getText().toString());
        params.put(Constants.PARAMS_DID, mEditRunsheet.getTrip_id());
        return params;
    }
}
