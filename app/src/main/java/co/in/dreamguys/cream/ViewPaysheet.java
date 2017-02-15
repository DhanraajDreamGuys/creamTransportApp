package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.adapter.ViewpaysheetAdapter;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 13-02-2017.
 */

public class ViewPaysheet extends AppCompatActivity {

    ListView mPaysheetViews;
    View mHeaderViewPaysheet;
    List<Data> mData;
    Toolbar mToolbar;
    ViewpaysheetAdapter aViewpaysheetAdapter;
    TextView mName, mSignature, mComments;
    EditText mOfficalUse;
    Button mSaveChanges;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paysheet);

        mData = (List<Data>) getIntent().getSerializableExtra(Constants.PAYSHEETDETAILS);

        initWidgets();

        mName.setText(mData.get(0).getFirst_name() + " " + mData.get(0).getLast_name());
        mComments.setText(mData.get(0).getComment());

        mHeaderViewPaysheet = getLayoutInflater().inflate(R.layout.include_header_view_paysheet, null);
        mPaysheetViews.addHeaderView(mHeaderViewPaysheet);

        aViewpaysheetAdapter = new ViewpaysheetAdapter(ViewPaysheet.this, mData);
        mPaysheetViews.setAdapter(aViewpaysheetAdapter);
        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mOfficalUse.setFocusable(true);
            mOfficalUse.setClickable(true);
            mOfficalUse.setFocusableInTouchMode(true);
            mSaveChanges.setVisibility(View.VISIBLE);
        } else {
            mOfficalUse.setFocusable(false);
            mOfficalUse.setClickable(false);
            mOfficalUse.setFocusableInTouchMode(false);
            mSaveChanges.setVisibility(View.GONE);
        }

    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getIntExtra(Constants.MODE, -1) == 0) {
            mToolbar.setTitle(getString(R.string.tool_edit_paysheet_title));
        } else {
            mToolbar.setTitle(getString(R.string.tool_view_paysheet_title));
        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mPaysheetViews = (ListView) findViewById(R.id.AVP_LV_view_paysheet);
        mName = (TextView) findViewById(R.id.AVP_TV_name);
        mSignature = (TextView) findViewById(R.id.AVP_TV_signature);
        mComments = (TextView) findViewById(R.id.AVP_TV_comments);
        mOfficalUse = (EditText) findViewById(R.id.AVP_TV_offical_use);
        mSaveChanges = (Button) findViewById(R.id.AVP_BT_save);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
