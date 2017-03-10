package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import co.in.dreamguys.cream.AlertSheet;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 23-02-2017.
 */
public class AlertAdapter extends BaseAdapter {
    Context mContext;
    private String[] alerts;
    LayoutInflater mInflater;

    public AlertAdapter(Context mContext, String[] alerts) {
        this.mContext = mContext;
        this.alerts = alerts;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alerts.length;
    }

    @Override
    public String getItem(int position) {
        return alerts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.acdapter_settings, null);
            mHolder = new ViewHolder();
            mHolder.mSetttings = (TextView) convertView.findViewById(R.id.AS_TV_setings);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mSetttings.setText(alerts[position]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        callAlertSheets(0);   // Alert all
                        break;
                    case 1:
                        callAlertSheets(1);   //  Alert paysheet
                        break;
                    case 2:
                        callAlertSheets(2);   // Alert Templates
                        break;
                }
            }
        });


        return convertView;
    }

    class ViewHolder {
        TextView mSetttings;
    }


    private void callAlertSheets(int page) {
        Intent mCallAlerts = new Intent(mContext, AlertSheet.class);
        mCallAlerts.putExtra(Constants.ALERT_PAGE_INDICATE, page);
        mContext.startActivity(mCallAlerts);
    }
}
