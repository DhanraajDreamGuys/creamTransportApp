package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.StaffreportAPI;
import co.in.dreamguys.cream.utils.Constants;

import static co.in.dreamguys.cream.utils.Util.getDateFormat;

/**
 * Created by user5 on 15-02-2017.
 */

public class StaffReportAdapter extends BaseAdapter {
    private Context mContext;
    private List<StaffreportAPI.Datum> data;
    private LayoutInflater mInflater;


    public StaffReportAdapter(Context mContext, List<StaffreportAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public StaffreportAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final StaffreportAPI.Datum mData = data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_staff_report, null);
            mHolder.mTruck = (TextView) convertView.findViewById(R.id.ASR_TV_truck_no);
            mHolder.mName = (TextView) convertView.findViewById(R.id.ASR_TV_name);
            mHolder.mTrailer = (TextView) convertView.findViewById(R.id.ASR_TV_trailer_no);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.ASR_TV_date);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.ASR_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.ASR_TV_to);
            mHolder.mEditPaySheet = (TextView) convertView.findViewById(R.id.ASR_TV_edit);
            mHolder.mViewPaysheet = (TextView) convertView.findViewById(R.id.ASR_TV_view);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mTrailer.setText(mData.getTrailers());
        mHolder.mName.setText(mData.getFirst_name() + " " + mData.getLast_name());
        mHolder.mTruck.setText(mData.getTruck());
        String date = getDateFormat(mData.getCreated_date());
        mHolder.mDate.setText(date);
        for (BranchAPI.Datum branch : Constants.countries) {
            if (branch.getId().equalsIgnoreCase(mData.getFrom()))
                mHolder.mFrom.setText(branch.getName());
        }

        for (BranchAPI.Datum branch : Constants.countries) {
            if (branch.getId().equalsIgnoreCase(mData.getTo()))
                mHolder.mTo.setText(branch.getName());
        }

        return convertView;
    }

    private class ViewHolder {
        TextView mName, mFrom, mTo, mDate, mTruck, mTrailer, mViewPaysheet, mEditPaySheet;
    }
}
