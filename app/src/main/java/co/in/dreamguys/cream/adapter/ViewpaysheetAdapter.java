package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.model.Data;

/**
 * Created by user5 on 13-02-2017.
 */

public class ViewpaysheetAdapter extends BaseAdapter {
    Context mContext;
    Data mData;
    LayoutInflater mInflater;

    public ViewpaysheetAdapter(Context mContext, Data mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;
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
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_view_paysheet, null);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.AVP_TV_date);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.AVP_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.AVP_TV_to);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.AVP_TV_truck_no);
            mHolder.mTrno1 = (TextView) convertView.findViewById(R.id.AVP_TV_tr_no_1);
            mHolder.mTrno2 = (TextView) convertView.findViewById(R.id.AVP_TV_tr_no_2);
            mHolder.mTrno3 = (TextView) convertView.findViewById(R.id.AVP_TV_tr_no_3);
            mHolder.mDolly = (TextView) convertView.findViewById(R.id.AVP_TV_dolly_no);
            mHolder.mMfNo = (TextView) convertView.findViewById(R.id.AVP_TV_mf_no);
            mHolder.mRTBD = (TextView) convertView.findViewById(R.id.AVP_TV_rt_bd);
            mHolder.mFitDuty = (TextView) convertView.findViewById(R.id.AVP_TV_fit_duty);
            mHolder.mSpeedoStart = (TextView) convertView.findViewById(R.id.AVP_TV_speedo_start);
            mHolder.mSpeedoFinish = (TextView) convertView.findViewById(R.id.AVP_TV_speedo_end);
            mHolder.mDailyIns = (TextView) convertView.findViewById(R.id.AVP_TV_daily);
            mHolder.mUnload = (TextView) convertView.findViewById(R.id.AVP_TV_unload);
            mHolder.mOfficeUse = (TextView) convertView.findViewById(R.id.AVP_TV_office_use);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mDate.setText(mData.getPdate());
        mHolder.mFrom.setText(mData.getFrom());
        mHolder.mTo.setText(mData.getTo());
        mHolder.mTruckNo.setText(mData.getTruck_no());
        mHolder.mTrno1.setText(mData.getTr1_no());
        mHolder.mTrno2.setText(mData.getTr2_no());
        mHolder.mTrno3.setText(mData.getTr3_no());
        mHolder.mDolly.setText(mData.getDolly_no());
        mHolder.mMfNo.setText(mData.getMf_no());
        mHolder.mRTBD.setText(mData.getRt_bd());
        mHolder.mFitDuty.setText(mData.getDuty());
        mHolder.mSpeedoStart.setText(mData.getStart_km());
        mHolder.mSpeedoFinish.setText(mData.getEnd_km());
        mHolder.mDailyIns.setText(mData.getInspection());
        mHolder.mUnload.setText(mData.getUnloading_time());
        mHolder.mOfficeUse.setText(mData.getOffice_use());

        return convertView;
    }

    private class ViewHolder {
        TextView mDate, mFrom, mTo, mTruckNo, mTrno1, mTrno2, mTrno3, mDolly, mMfNo, mRTBD, mFitDuty, mSpeedoStart, mSpeedoFinish, mDailyIns, mUnload, mOfficeUse;
    }
}
