package co.in.dreamguys.cream;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import co.in.dreamguys.cream.utils.ActivityConstants;

/**
 * Created by user5 on 06-03-2017.
 */

public class PhoneBook extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mPhonebookWidgets;
    String[] phonebooklist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        phonebooklist = new String[]{
                getString(R.string.str_truck),
                getString(R.string.str_workshop),
                getString(R.string.str_management),
                getString(R.string.str_customers),
        };
        initWidgets();

        PhoneBookAdapter aPhoneBookAdapter = new PhoneBookAdapter(PhoneBook.this, phonebooklist);
        mPhonebookWidgets.setAdapter(aPhoneBookAdapter);
    }

    private void initWidgets() {

        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitle(getString(R.string.tool_phonebook_title));
        mToolbar.setTitleTextColor(Color.WHITE);
        mPhonebookWidgets = (ListView) findViewById(R.id.APB_LV_phonebook);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class PhoneBookAdapter extends BaseAdapter {
        Context mContext;
        String[] phonebooklist;
        LayoutInflater mInflater;

        PhoneBookAdapter(Context mContext, String[] phonebooklist) {
            this.mContext = mContext;
            this.phonebooklist = phonebooklist;
            mInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return phonebooklist.length;
        }

        @Override
        public String getItem(int position) {
            return phonebooklist[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;

            if (convertView == null) {
                mHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.adapter_phonebook, null);
                mHolder.mPhonebooks = (TextView) convertView.findViewById(R.id.APB_TV_phonebooks);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            String mPhoneBooks = getItem(position);
            mHolder.mPhonebooks.setText(mPhoneBooks);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            ActivityConstants.callPage(mContext, PBTruck.class);
                            break;
                        case 1:
                            ActivityConstants.callPage(mContext, PBWorkShop.class);
                            break;
                        case 2:
                            ActivityConstants.callPage(mContext, PBManagement.class);
                            break;
                        case 3:
                            ActivityConstants.callPage(mContext, PBCustomers.class);
                            break;
                    }

                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView mPhonebooks;
        }
    }
}
