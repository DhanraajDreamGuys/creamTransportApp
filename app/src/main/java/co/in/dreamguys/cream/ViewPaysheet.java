package co.in.dreamguys.cream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

/**
 * Created by user5 on 13-02-2017.
 */

public class ViewPaysheet extends AppCompatActivity {

    ListView mPaysheetViews;
    View mHeaderViewPaysheet, mFooterViewPaysheet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paysheet);

        initWidgets();

        mHeaderViewPaysheet = getLayoutInflater().inflate(R.layout.include_header_view_paysheet, null);
        mFooterViewPaysheet = getLayoutInflater().inflate(R.layout.include_footer_view_paysheet, null);

        mPaysheetViews.addHeaderView(mHeaderViewPaysheet);
        mPaysheetViews.addFooterView(mFooterViewPaysheet);
    }

    private void initWidgets() {
        mPaysheetViews = (ListView) findViewById(R.id.AVP_LV_view_paysheet);
    }
}
