package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.in.dreamguys.cream.apis.TripListAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

/**
 * Created by user5 on 20-02-2017.
 */

public class ViewTripsheet extends AppCompatActivity {
    Toolbar mToolbar;
    TextView mChooseDriver, mFrom, mTo, mDate, mSetDate;
    EditText mTruckNo, mTrailers, mManifestNo, mDollyNo, mLoadavailable, mFromWho, mLoadDue, mBy, mChangeOver, mDriver, mComments, mDynamicET;
    LinearLayout mAddNewTrailerLayout;
    CheckBox mExpress, mNoDanger, mHasDanger, mProductMarket;
    Button mSend, mCancel, mAddTrail, mRemoveTrail;
    String checkedExpress = "", checkedNodanger = "", checkedHasDanger = "", checkedProduct = "", checkedItems = "", trailers = "";
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = ViewTripsheet.class.getName();
    TripListAPI.Datum mTripsheet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        mCustomProgressDialog = new CustomProgressDialog(this);
        intiWidgets();

        mTripsheet = (TripListAPI.Datum) getIntent().getSerializableExtra(Constants.TRIPSHEETDETAILS);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {

        } else {
            fillViewTrip();
        }


    }

    private void fillViewTrip() {
        mChooseDriver.setText(mTripsheet.getFirst_name() + " " + mTripsheet.getLast_name());
        mDate.setText(mTripsheet.getCreated_date());
        mTruckNo.setText(mTripsheet.getTruck());
        for (int i = 0; i < Constants.countries.size(); i++) {
            if (Constants.countries.get(i).getId().equalsIgnoreCase(mTripsheet.getFrom())) {
                mFrom.setText(Constants.countries.get(i).getName());
            } else if (Constants.countries.get(i).getId().equalsIgnoreCase(mTripsheet.getTo())) {
                mTo.setText(Constants.countries.get(i).getName());
            }
        }
        mManifestNo.setText(mTripsheet.getManifest_no());
        mDollyNo.setText(mTripsheet.getDolly());
        mAddTrail.setVisibility(View.GONE);

        String data = mTripsheet.getTrailers();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(data);
        String appendValue = "";
        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                appendValue += jsonArray.get(i) + ",";
            }
        }
        mTrailers.setText(appendValue.replace("\"", ""));
        JsonObject loadFrom = new Gson().fromJson(mTripsheet.getLoad_from(), JsonObject.class);
        mLoadavailable.setText(loadFrom.get("ltime").toString().replace("\"", ""));
        mFromWho.setText(loadFrom.get("lfrom").toString().replace("\"", ""));

        JsonObject loadDue = new Gson().fromJson(mTripsheet.getLoad_due(), JsonObject.class);
        mLoadDue.setText(loadDue.get("ldfrom").toString().replace("\"", ""));
        mBy.setText(loadDue.get("ldue").toString().replace("\"", ""));
        mSetDate.setText(loadDue.get("ldate").toString().replace("\"", ""));

        JsonObject covertedObject = new Gson().fromJson(mTripsheet.getChangeover(), JsonObject.class);
        mChangeOver.setText(covertedObject.get("ctruck").toString().replace("\"", ""));
        mDriver.setText(covertedObject.get("cdriver").toString().replace("\"", ""));


        String[] mLoadType = mTripsheet.getLoad_type().split(",");
        for (String aMLoadType : mLoadType) {

            if (aMLoadType.equalsIgnoreCase("Express and General")) {
                mExpress.setChecked(true);
            }
            if (aMLoadType.equalsIgnoreCase(getString(R.string.no_danger))) {
                mNoDanger.setChecked(true);
            }
            if (aMLoadType.equalsIgnoreCase(getString(R.string.product))) {
                mProductMarket.setChecked(true);
            }
            if (aMLoadType.equalsIgnoreCase(getString(R.string.has_danger))) {
                mHasDanger.setChecked(true);
            }

        }

        mComments.setText(mTripsheet.getAdmin_cmt());
        mSend.setVisibility(View.GONE);
        mCancel.setVisibility(View.GONE);
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mToolbar.setTitle(getString(R.string.tool_edit_trip_title));
        } else {
            mToolbar.setTitle(getString(R.string.tool_view_trips_title));
        }

        mToolbar.setTitleTextColor(Color.WHITE);


        mChooseDriver = (TextView) findViewById(R.id.AVT_TV_choose_driver);
        mFrom = (TextView) findViewById(R.id.AVT_TV_from);
        mTo = (TextView) findViewById(R.id.AVT_TV_to);
        mDate = (TextView) findViewById(R.id.AVT_TV_date);
        mSetDate = (TextView) findViewById(R.id.AVT_TV_set_date);
        mTruckNo = (EditText) findViewById(R.id.AVT_ET_truck);
        mTrailers = (EditText) findViewById(R.id.AVT_ET_traliers);
        mManifestNo = (EditText) findViewById(R.id.AVT_ET_manifest);
        mDollyNo = (EditText) findViewById(R.id.AVT_ET_dolly);
        mLoadavailable = (EditText) findViewById(R.id.AVT_ET_load_availble_after);
        mLoadDue = (EditText) findViewById(R.id.AVT_ET_load_due_in);
        mFromWho = (EditText) findViewById(R.id.AVT_ET_from);
        mBy = (EditText) findViewById(R.id.AVT_ET_by);
        mChangeOver = (EditText) findViewById(R.id.AVT_ET_change_over);
        mDriver = (EditText) findViewById(R.id.AVT_ET_driver);
        mComments = (EditText) findViewById(R.id.AVT_ET_comment);
        mSend = (Button) findViewById(R.id.AVT_BT_send);
        mCancel = (Button) findViewById(R.id.AVT_BT_cancel);
        mAddTrail = (Button) findViewById(R.id.AVT_BT_add);
        mRemoveTrail = (Button) findViewById(R.id.AVT_BT_remove);
        mAddNewTrailerLayout = (LinearLayout) findViewById(R.id.AVT_LL_add_new_trailers);
        mExpress = (CheckBox) findViewById(R.id.AVT_CB_exp_general);
        mNoDanger = (CheckBox) findViewById(R.id.AVT_CB_no_danger_goods);
        mHasDanger = (CheckBox) findViewById(R.id.AVT_CB_has_danger_goods);
        mProductMarket = (CheckBox) findViewById(R.id.AVT_CB_product_market);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
