package co.in.dreamguys.cream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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

    GridView mDashBoardWidget;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initWidgets();
        mCustomProgressDialog = new CustomProgressDialog(this);

        getDashboardData();
/*
        createDataSet();
        defineXaxislabels();
        createChart();*/


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

    private void fillDashboardData(DashboardAPI.Data data) {

    }

    private void initWidgets() {
//        mBarChart = (BarChart) findViewById(R.id.AD_BC_dashboard_data);
        mDashBoardWidget = (GridView) findViewById(R.id.AD_GV_dashboard_list);
    }

    private void createChart() {
        BarData data = new BarData(dataset);
        mBarChart.setData(data);
        mBarChart.animateY(5000);
    }

    private void defineXaxislabels() {
        labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
    }

    private void createDataSet() {
        entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        /*for (String label : labels)
            dataset.setLabel(label);*/
    }
}
