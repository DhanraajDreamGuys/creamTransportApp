package co.in.dreamguys.cream;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DashboardAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 15-02-2017.
 */

public class Dashboard extends AppCompatActivity {

    BarChart mBarChart;
    ArrayList<String> labels;
    ArrayList<BarEntry> entries;
    BarDataSet dataset;
    CustomProgressDialog mCustomProgressDialog;
    private static String TAG = Dashboard.class.getSimpleName();
    Toolbar mToolbar;
    ListView mDashBoardWidget;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initWidgets();
        mCustomProgressDialog = new CustomProgressDialog(this);

        getDashboardData();

    }

    private void getDashboardData() {
        mCustomProgressDialog.showDialog();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<DashboardAPI.DashboardResponse> loginCall = apiService.getDashboard();
        loginCall.enqueue(new Callback<DashboardAPI.DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardAPI.DashboardResponse> call, Response<DashboardAPI.DashboardResponse> response) {
                mCustomProgressDialog.dismiss();
                if (response.body().getMeta().equals(Constants.SUCCESS)) {
                    fillDashboardData(response.body().getData());
                } else {
                    Toast.makeText(Dashboard.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashboardAPI.DashboardResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                mCustomProgressDialog.dismiss();
            }
        });
    }

    private void fillDashboardData(List<DashboardAPI.Datum> data) {
        DashboardAdpater aDashboardAdpater = new DashboardAdpater(Dashboard.this, data);
        mDashBoardWidget.setAdapter(aDashboardAdpater);
        aDashboardAdpater.notifyDataSetChanged();
    }

    private void initWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.toolbar_dashboard_title));
        mToolbar.setTitleTextColor(Color.WHITE);

        mDashBoardWidget = (ListView) findViewById(R.id.AD_GV_dashboard_list);
    }

    private class DashboardAdpater extends BaseAdapter {
        Context mContext;
        List<DashboardAPI.Datum> data;
        LayoutInflater mInflater;

        DashboardAdpater(Context mContext, List<DashboardAPI.Datum> data) {
            this.mContext = mContext;
            this.data = data;
            mInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public DashboardAPI.Datum getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;
            if (convertView == null) {
                mHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.adapter_dashboard, null);
                mHolder.mCount = (TextView) convertView.findViewById(R.id.AD_TV_count);
                mHolder.mNames = (TextView) convertView.findViewById(R.id.AD_TV_name);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            mHolder.mCount.setText("" + data.get(position).getCount());
            mHolder.mNames.setText(data.get(position).getName());

            return convertView;
        }

        class ViewHolder {
            TextView mCount, mNames;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
