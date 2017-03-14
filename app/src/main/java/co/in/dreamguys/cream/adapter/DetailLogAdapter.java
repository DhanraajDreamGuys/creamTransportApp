package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.DetailLogAPI;

/**
 * Created by user5 on 15-02-2017.
 */

public class DetailLogAdapter extends BaseAdapter {
    private Context mContext;
    List<DetailLogAPI.Data> data;
    private LayoutInflater mInflater;

    public DetailLogAdapter(Context mContext, List<DetailLogAPI.Data> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DetailLogAPI.Data getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailLogAPI.Data mData = data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_detail_log, null);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.ADDL_TV_date);
            mHolder.mAvailableHrs = (TextView) convertView.findViewById(R.id.ADDL_TV_avil_hrs);
            mHolder.mTotalHrs = (TextView) convertView.findViewById(R.id.ADDL_TV_total_hrs);
            mHolder.mWorkHours = (TextView) convertView.findViewById(R.id.ADDL_TV_wrk_hrs);
            mHolder.mComments = (TextView) convertView.findViewById(R.id.ADDL_TV_comments);
            mHolder.mViewDrLog = (TextView) convertView.findViewById(R.id.ADDL_TV_view);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }


        mHolder.mTotalHrs.setText("" + mData.getTotalhrs());
        mHolder.mWorkHours.setText("" + mData.getWorkhrs());
        mHolder.mAvailableHrs.setText("" + mData.getAvailhrs());
        if (!mData.getCmt().isEmpty())
            mHolder.mComments.setText(mData.getCmt());
        else
            mHolder.mComments.setVisibility(View.GONE);
        mHolder.mDate.setText(mData.getDate());


        return convertView;
    }

    private class ViewHolder {
        TextView mDate, mAvailableHrs, mTotalHrs, mWorkHours, mComments, mViewDrLog;
    }
}
