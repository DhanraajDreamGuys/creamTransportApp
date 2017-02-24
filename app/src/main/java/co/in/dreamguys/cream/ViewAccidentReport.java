package co.in.dreamguys.cream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import co.in.dreamguys.cream.apis.AccidentReportAPI;

import static co.in.dreamguys.cream.utils.Constants.CURRENT_IMAGE;
import static co.in.dreamguys.cream.utils.Constants.EDIT_ACCIDENT_DATA;
import static co.in.dreamguys.cream.utils.Constants.PREVIEW_ACCIDENT_REPORT;
import static co.in.dreamguys.cream.utils.Util.getDateFormat;

/**
 * Created by user5 on 23-02-2017.
 */

public class ViewAccidentReport extends AppCompatActivity {
    Toolbar mToolbar;
    AccidentReportAPI.Datum mAccidentData;
    EditText mName, mLocation, mDate, mTimeAcc, mOName,
            mOPhoneNo, mORegNo, mOModel, mOAddress, mOLic,
            mOMake, mOInsurance, mVRegNo, mVModel, mVLic,
            mVPolicy, mVContactName, mVInsBroker, mVMake,
            mVChasisNo, mVInsCom, mVRenewDate, mVContactNo,
            mVEmail, mWName, mWPhoneNo;

    LinearLayout mImageParent;
    ImageView mImages;
    private static String domainURL = "http://creamtransporttechnologies.com/app/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accident_report);
        intiWidgets();

        mAccidentData = (AccidentReportAPI.Datum) getIntent().getSerializableExtra(EDIT_ACCIDENT_DATA);
        if (mAccidentData != null) {
            mName.setText(mAccidentData.getFirst_name() + " " + mAccidentData.getLast_name());
            mLocation.setText(mAccidentData.getAcc_loc());
            String acc_date = getDateFormat(mAccidentData.getAcc_date());
            mDate.setText(acc_date);
            mTimeAcc.setText(mAccidentData.getAcc_time());

            JsonObject otherVechicle = new Gson().fromJson(mAccidentData.getOthervehicles(), JsonObject.class);
            mOName.setText(otherVechicle.get("ov_name").toString().replace("\"", ""));
            mOPhoneNo.setText(otherVechicle.get("ov_phone").toString().replace("\"", ""));
            mOInsurance.setText(otherVechicle.get("ov_ins").toString().replace("\"", ""));
            mOModel.setText(otherVechicle.get("ov_vm").toString().replace("\"", ""));
            mOLic.setText(otherVechicle.get("ov_lno").toString().replace("\"", ""));
            mOAddress.setText(otherVechicle.get("ov_address").toString().replace("\"", ""));
            mOMake.setText(otherVechicle.get("ov_vmake").toString().replace("\"", ""));
            mORegNo.setText(otherVechicle.get("ov_vreg").toString().replace("\"", ""));

            JsonObject myVechicle = new Gson().fromJson(mAccidentData.getMytruck(), JsonObject.class);
            mVContactName.setText(myVechicle.get("contactName").toString().replace("\"", ""));
            mVInsBroker.setText(myVechicle.get("insuranceBroker").toString().replace("\"", ""));
            mVRenewDate.setText(myVechicle.get("renewalDate").toString().replace("\"", ""));
            mVLic.setText(myVechicle.get("licenceNo").toString().replace("\"", ""));
            mVContactNo.setText(myVechicle.get("contactNumber").toString().replace("\"", ""));
            mVModel.setText(myVechicle.get("model").toString().replace("\"", ""));
            mVInsCom.setText(myVechicle.get("insuranceCompany").toString().replace("\"", ""));
            mVEmail.setText(myVechicle.get("contactEmail").toString().replace("\"", ""));
            mVChasisNo.setText(myVechicle.get("chassisNo").toString().replace("\"", ""));
            mVPolicy.setText(myVechicle.get("policyNo").toString().replace("\"", ""));
            mVRegNo.setText(myVechicle.get("vehicleRego").toString().replace("\"", ""));
            mVMake.setText(myVechicle.get("make").toString().replace("\"", ""));

            JsonObject witness = new Gson().fromJson(mAccidentData.getWitness(), JsonObject.class);
            mWName.setText(witness.get("wit_name").toString().replace("\"", ""));
            mWPhoneNo.setText(witness.get("wit_phone").toString().replace("\"", ""));

            if (!mAccidentData.getImages().isEmpty()) {
                try {
                    final JSONArray jsonArray = new JSONArray(mAccidentData.getImages());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        mImages = new ImageView(this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
                        layoutParams.setMargins(5, 2, 2, 2);
                        mImages.setLayoutParams(layoutParams);
                        mImages.setAdjustViewBounds(true);
                        mImages.setScaleType(ImageView.ScaleType.FIT_XY);
                        mImages.setId(i);
                        mImages.requestLayout();
                        Picasso.with(ViewAccidentReport.this).load(domainURL + jsonArray.get(i)).into(mImages);
                        mImageParent.addView(mImages);

                        final int finalI = i;
                        mImages.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent mCallPreview = new Intent(ViewAccidentReport.this, PreviewAccidentReport.class);
                                mCallPreview.putExtra(PREVIEW_ACCIDENT_REPORT, mAccidentData.getImages());
                                mCallPreview.putExtra(CURRENT_IMAGE, finalI);
                                startActivity(mCallPreview);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_view_accident_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mName = (EditText) findViewById(R.id.AVAR_ET_name);
        mOName = (EditText) findViewById(R.id.AVAR_ET_od_name);
        mWName = (EditText) findViewById(R.id.AVAR_ET_w_name);
        mLocation = (EditText) findViewById(R.id.AVAR_ET_location);
        mDate = (EditText) findViewById(R.id.AVAR_ET_date);
        mTimeAcc = (EditText) findViewById(R.id.AVAR_ET_time_acc);

        mOPhoneNo = (EditText) findViewById(R.id.AVAR_ET_od_phoneno);
        mORegNo = (EditText) findViewById(R.id.AVAR_ET_od_regno);
        mOModel = (EditText) findViewById(R.id.AVAR_ET_od_model);
        mOAddress = (EditText) findViewById(R.id.AVAR_ET_od_address);
        mOInsurance = (EditText) findViewById(R.id.AVAR_ET_od_insurance);
        mOMake = (EditText) findViewById(R.id.AVAR_ET_od_make);
        mOLic = (EditText) findViewById(R.id.AVAR_ET_od_licenseno);

        mVRegNo = (EditText) findViewById(R.id.AVAR_ET_mv_regno);
        mVModel = (EditText) findViewById(R.id.AVAR_ET_mv_model);
        mVChasisNo = (EditText) findViewById(R.id.AVAR_ET_mv_chassis_no);
        mVLic = (EditText) findViewById(R.id.AVAR_ET_mv_licenseno);
        mVPolicy = (EditText) findViewById(R.id.AVAR_ET_mv_policy_no);
        mVContactName = (EditText) findViewById(R.id.AVAR_ET_mv_contactname);
        mVInsBroker = (EditText) findViewById(R.id.AVAR_ET_mv_Insurancebroker);
        mVMake = (EditText) findViewById(R.id.AVAR_ET_mv_make);
        mVInsCom = (EditText) findViewById(R.id.AVAR_ET_mv_insurance_company);
        mVRenewDate = (EditText) findViewById(R.id.AVAR_ET_mv_renewal_date);
        mVContactNo = (EditText) findViewById(R.id.AVAR_ET_mv_contact_no);
        mVEmail = (EditText) findViewById(R.id.AVAR_ET_mv_contact_email);

        mWName = (EditText) findViewById(R.id.AVAR_ET_w_name);
        mWPhoneNo = (EditText) findViewById(R.id.AVAR_ET_w_phone_no);

        mImageParent = (LinearLayout) findViewById(R.id.AVAR_LL_images);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
