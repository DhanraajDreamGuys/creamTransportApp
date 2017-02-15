package co.in.dreamguys.cream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by user5 on 15-02-2017.
 */

public class Dashboard extends AppCompatActivity {

    BarChart mBarChart;
    ArrayList<String> labels;
    ArrayList<BarEntry> entries;
    BarDataSet dataset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initWidgets();

        createDataSet();
        defineXaxislabels();
        createChart();


    }

    private void initWidgets() {
        mBarChart = (BarChart) findViewById(R.id.AD_BC_dashboard_data);
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
