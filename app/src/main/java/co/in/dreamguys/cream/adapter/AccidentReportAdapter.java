package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewAccidentReport;
import co.in.dreamguys.cream.apis.AccidentReportAPI;

import static co.in.dreamguys.cream.utils.Constants.EDIT_ACCIDENT_DATA;
import static co.in.dreamguys.cream.utils.Util.getDateFormat;

/**
 * Created by user5 on 23-02-2017.
 */

public class AccidentReportAdapter extends BaseAdapter {
    Context mContext;
    private List<AccidentReportAPI.Datum> data;
    LayoutInflater mInflater;

    public AccidentReportAdapter(Context mContext, List<AccidentReportAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public AccidentReportAPI.Datum getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_accident_report, null);
            mHolder.mName = (TextView) convertView.findViewById(R.id.AAR_TV_driver);
            mHolder.mFromDate = (TextView) convertView.findViewById(R.id.AAR_TV_date);
            mHolder.mTime = (TextView) convertView.findViewById(R.id.AAR_TV_time);
            mHolder.mLocation = (TextView) convertView.findViewById(R.id.AAR_TV_location);
            mHolder.mView = (TextView) convertView.findViewById(R.id.AAR_TV_view);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final AccidentReportAPI.Datum mData = data.get(position);
        mHolder.mName.setText(mData.getFirst_name() + " " + mData.getLast_name());
        String accDate = getDateFormat(mData.getAcc_date());
        mHolder.mFromDate.setText(accDate);
        mHolder.mTime.setText(mData.getAcc_time());
        mHolder.mLocation.setText(mData.getAcc_loc());

        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewAccidentReport = new Intent(mContext, ViewAccidentReport.class);
                mCallViewAccidentReport.putExtra(EDIT_ACCIDENT_DATA, mData);
                mContext.startActivity(mCallViewAccidentReport);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView mName, mFromDate, mTime, mLocation, mView;
    }
}
