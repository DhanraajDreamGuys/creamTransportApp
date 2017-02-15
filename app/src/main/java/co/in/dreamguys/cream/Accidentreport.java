package co.in.dreamguys.cream;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * Created by user5 on 11-02-2017.
 */

public class Accidentreport extends AppCompatActivity {
    CustomAdapter mCustomAdapter;
    ListView mListView;
    Button mSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_search);

        mListView = (ListView) findViewById(R.id.lv_search_items);
        mSearch = (Button) findViewById(R.id.bt_search);
        mCustomAdapter = new CustomAdapter();
        mListView.setAdapter(mCustomAdapter);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accidentreport.this, Company.class));
            }
        });

    }

    private class CustomAdapter extends BaseAdapter {


        LayoutInflater mInflater;

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.adapter_accident_report, null);
            }
            return convertView;
        }
    }
}
