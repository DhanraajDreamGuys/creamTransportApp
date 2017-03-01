package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import co.in.dreamguys.cream.apis.FridgeCodeAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 27-02-2017.
 */

public class ViewFridgeCodec extends AppCompatActivity {
    Toolbar mToolbar;
    EditText mCodeNo, mCodeType, mCodeDesc, mOperation;
    RadioGroup mCodeColor;
    FridgeCodeAPI.Datum mData;
    RadioButton mGreen, mRed, mYellow;
    ToggleButton mStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fridge_codec);
        intiWidgets();
        mData = (FridgeCodeAPI.Datum) getIntent().getSerializableExtra(Constants.FRIDGE_CODE_DATA);
        if (mData != null) {
            mCodeNo.setText(mData.getCode());
            mCodeDesc.setText(mData.getDescription());
            mOperation.setText(mData.getOperation());
            if (mData.getType().equalsIgnoreCase("carrier")) {
                mCodeType.setText(getString(R.string.str_thermal));
            } else {
                mCodeType.setText(getString(R.string.str_carrier));
            }

            if (mData.getColor().equalsIgnoreCase(getString(R.string.color_green))) {
                mGreen.setChecked(true);
            } else if (mData.getColor().equalsIgnoreCase(getString(R.string.color_red))) {
                mRed.setChecked(true);
            } else if (mData.getColor().equalsIgnoreCase(getString(R.string.color_yellow))) {
                mYellow.setChecked(true);
            }
            if (mData.getStatus().equalsIgnoreCase("0")) {
                mStatus.setChecked(true);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(ViewFridgeCodec.this, R.drawable.button_background_green));
            } else {
                mStatus.setChecked(false);
                mStatus.setBackgroundDrawable(ContextCompat.getDrawable(ViewFridgeCodec.this, R.drawable.button_background_red));
            }
        }

    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_view_fridge_codec_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mCodeNo = (EditText) findViewById(R.id.AVFC_ET_code_no);
        mCodeType = (EditText) findViewById(R.id.AVFC_ET_code_type);
        mCodeDesc = (EditText) findViewById(R.id.AVFC_ET_desc);
        mOperation = (EditText) findViewById(R.id.AVFC_ET_operation);
        mCodeColor = (RadioGroup) findViewById(R.id.AVFC_RG_code_colors);

        mGreen = (RadioButton) findViewById(R.id.AVFC_RB_green);
        mRed = (RadioButton) findViewById(R.id.AVFC_RB_red);
        mYellow = (RadioButton) findViewById(R.id.AVFC_RB_yellow);
        mStatus = (ToggleButton) findViewById(R.id.AVFC_TB_status_update);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
