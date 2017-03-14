package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.DetailDriverLog;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.DriverHoursAPI;
import co.in.dreamguys.cream.apis.DriverListsAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 15-02-2017.
 */

public class DriverLogAdapter extends BaseAdapter {
    private Context mContext;
    List<DriverHoursAPI.Datum> data;
    private LayoutInflater mInflater;

    public DriverLogAdapter(Context mContext, List<DriverHoursAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DriverHoursAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DriverHoursAPI.Datum mData = data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_driver_log, null);
            mHolder.mName = (TextView) convertView.findViewById(R.id.ADL_TV_driver_name);
            mHolder.mAvailableHrs = (TextView) convertView.findViewById(R.id.ADL_TV_av_hrs);
            mHolder.mNightHrs = (TextView) convertView.findViewById(R.id.ADL_TV_avil_nit_hrs);
            mHolder.mCompletedHrs = (TextView) convertView.findViewById(R.id.ADL_TV_cmpltd_hrs);
            mHolder.mCompletedNitHrs = (TextView) convertView.findViewById(R.id.ADL_TV_nit_hrs_usd);
            mHolder.mViewDrLog = (TextView) convertView.findViewById(R.id.ADL_TV_view);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        for (DriverListsAPI.Datum drivername : Constants.driverList) {
            if (drivername.getId().equalsIgnoreCase(mData.getUid()))
                mHolder.mName.setText(drivername.getFirst_name() + " " + drivername.getLast_name());
        }


        mHolder.mCompletedNitHrs.setText("" + mData.getNighthrs());
        mHolder.mAvailableHrs.setText("" + mData.getAvailhrs());
        mHolder.mNightHrs.setText("" + mData.getNighthrs());
        mHolder.mCompletedHrs.setText("" + mData.getCompletenightbreak());


        mHolder.mViewDrLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallDriverLog = new Intent(mContext, DetailDriverLog.class);
                mCallDriverLog.putExtra(Constants.ID, mData.getUid());
                mContext.startActivity(mCallDriverLog);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mName, mAvailableHrs, mCompletedHrs, mNightHrs, mCompletedNitHrs, mViewDrLog;
    }
}
