package co.in.dreamguys.cream;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import static co.in.dreamguys.cream.utils.Constants.CURRENT_IMAGE;
import static co.in.dreamguys.cream.utils.Constants.PREVIEW_ACCIDENT_REPORT;

/**
 * Created by user5 on 23-02-2017.
 */

public class PreviewAccidentReport extends AppCompatActivity {

    Toolbar mToolbar;
    ViewPager mViewPager;
    CustomPagerAdapter aCustomPagerAdapter;
    String mImage = "";
    int currentimage = -1;
    private static String domainURL = "http://creamtransporttechnologies.com/app/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_accident_report);

        intiWidgets();
        mImage = getIntent().getStringExtra(PREVIEW_ACCIDENT_REPORT);
        currentimage = getIntent().getIntExtra(CURRENT_IMAGE, -1);
        if (!mImage.isEmpty() && currentimage != -1) {
            try {
                final JSONArray jsonArray = new JSONArray(mImage);
                aCustomPagerAdapter = new CustomPagerAdapter(PreviewAccidentReport.this, jsonArray);
                mViewPager.setAdapter(aCustomPagerAdapter);
                mViewPager.setCurrentItem(currentimage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_preview_accident_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mViewPager = (ViewPager) findViewById(R.id.APAR_VP_images);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        JSONArray jsonArray;

        public CustomPagerAdapter(Context context, JSONArray jsonArray) {
            mContext = context;
            this.jsonArray = jsonArray;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.PI_IV_images);
            try {
                Picasso.with(mContext).load(domainURL + jsonArray.get(position)).into(imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
