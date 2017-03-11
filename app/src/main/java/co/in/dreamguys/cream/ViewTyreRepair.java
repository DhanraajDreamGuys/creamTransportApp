package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.in.dreamguys.cream.apis.TyreRepairAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 08-03-2017.
 */
public class ViewTyreRepair extends AppCompatActivity {
    Toolbar mToolbar;
    TyreRepairAPI.Datum mData;
    private static String TAG = ViewTyreRepair.class.getName();

    View mTitle, mTyres, mTrailerTyres;
    LinearLayout mParentLayout;
    TextView mTitleNo, mRegNo;

    int truckSize = 1, primeSize = 4, dollySize = 4, trailerSize = 6;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tyre_repair);
        initWidgets();

        mData = (TyreRepairAPI.Datum) getIntent().getSerializableExtra(Constants.TYRE_REPAIR_DATA);
        if (mData != null) {
            JsonObject truckbject = new Gson().fromJson(mData.getTruck(), JsonObject.class);
            JsonObject tralierbject = new Gson().fromJson(mData.getTrailer(), JsonObject.class);
            JsonObject dollybject = new Gson().fromJson(mData.getDolly(), JsonObject.class);

            final JsonObject issueObject = new Gson().fromJson(mData.getIssue(), JsonObject.class);


            if (truckbject.has("tno")) {
                mTitle = getLayoutInflater().inflate(R.layout.include_header, null);
                mTitleNo = (TextView) mTitle.findViewById(R.id.IH_TV_no);
                mRegNo = (TextView) mTitle.findViewById(R.id.IH_TV_reg_no);

                mTitleNo.setText("Front - Truck No: " + truckbject.get("tno").toString().replace("\"", ""));
                mRegNo.setText("Reg.No: " + truckbject.get("trno").toString().replace("\"", ""));

                mParentLayout.addView(mTitle);
                for (int truck = 0; truck < truckSize; truck++) {
                    mTyres = getLayoutInflater().inflate(R.layout.include_tralier, null);
                    mTyres.setId(truck);
                    mParentLayout.addView(mTyres);
                }
            }


            mTitle = getLayoutInflater().inflate(R.layout.include_header, null);
            mTitleNo = (TextView) mTitle.findViewById(R.id.IH_TV_no);
            mRegNo = (TextView) mTitle.findViewById(R.id.IH_TV_reg_no);
            mRegNo.setVisibility(View.GONE);
            mTitleNo.setText("Prime mover");

            mParentLayout.addView(mTitle);
            for (int truck = 0; truck < dollySize; truck++) {
                mTyres = getLayoutInflater().inflate(R.layout.include_tralier, null);
                final Button left = (Button) mTyres.findViewById(R.id.IT_BT_tltyre);
                final Button right = (Button) mTyres.findViewById(R.id.IT_BT_trtyre);
                mParentLayout.addView(mTyres);
            }

            if (dollybject.has("dno")) {
                mTitle = getLayoutInflater().inflate(R.layout.include_header, null);
                mTitleNo = (TextView) mTitle.findViewById(R.id.IH_TV_no);
                mRegNo = (TextView) mTitle.findViewById(R.id.IH_TV_reg_no);

                mTitleNo.setText("Dolly - No: " + dollybject.get("dno").toString().replace("\"", ""));
                mRegNo.setText("Reg.No: " + dollybject.get("drno").toString().replace("\"", ""));

                mParentLayout.addView(mTitle);
                for (int truck = 0; truck < dollySize; truck++) {
                    mTyres = getLayoutInflater().inflate(R.layout.include_tralier, null);
                    mTyres.setId(truck);

                    mParentLayout.addView(mTyres);
                }
            }


            if (tralierbject.has("atrlno")) {
                for (int trailerCount = 0; trailerCount < tralierbject.size() / 2; trailerCount++) {
                    mTitle = getLayoutInflater().inflate(R.layout.include_header, null);
                    mTitleNo = (TextView) mTitle.findViewById(R.id.IH_TV_no);
                    mRegNo = (TextView) mTitle.findViewById(R.id.IH_TV_reg_no);

                    mTitleNo.setText("A Trailer - No:" + tralierbject.get("atrlno").toString().replace("\"", ""));
                    mRegNo.setText("Reg.No:" + tralierbject.get("atrlrno").toString().replace("\"", ""));


                    mParentLayout.addView(mTitle);
                    for (int trailer = 0; trailer < trailerSize; trailer++) {
                        mTrailerTyres = getLayoutInflater().inflate(R.layout.include_tralier, null);
                        mTrailerTyres.setId(trailer);

                        mParentLayout.addView(mTrailerTyres);
                    }
                }
            }


        }


    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_view_tyre_repair_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mParentLayout = (LinearLayout) findViewById(R.id.AVTR_LL_repair_parent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
