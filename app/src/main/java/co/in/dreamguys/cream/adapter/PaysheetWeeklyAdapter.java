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
import co.in.dreamguys.cream.ViewPaysheet;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;

/**
 * Created by user5 on 15-02-2017.
 */

public class PaysheetWeeklyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Data> data;
    private LayoutInflater mInflater;
    CustomProgressDialog mCustomProgressDialog;

    public PaysheetWeeklyAdapter(Context mContext, List<Data> data) {

        this.mContext = mContext;
        this.data = data;
        mCustomProgressDialog = new CustomProgressDialog(mContext);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Data getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_paysheet_weekly_report, null);
            mHolder.mMFNo = (TextView) convertView.findViewById(R.id.APWR_TV_mfno);
            mHolder.mName = (TextView) convertView.findViewById(R.id.APWR_TV_name);
            mHolder.mEmail = (TextView) convertView.findViewById(R.id.APWR_TV_email);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.APWR_TV_date);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.APWR_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.APWR_TV_to);
            mHolder.mEditPaySheet = (TextView) convertView.findViewById(R.id.APWR_TV_edit);
            mHolder.mViewPaysheet = (TextView) convertView.findViewById(R.id.APWR_TV_view);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        final Data mData = data.get(position);
        mHolder.mMFNo.setText(mData.getMf_no());
        mHolder.mName.setText(mData.getFirst_name() + " " + mData.getLast_name());
        mHolder.mEmail.setText(mData.getEmail());
        mHolder.mDate.setText(mData.getPdate());
        mHolder.mFrom.setText(mData.getFrom());
        mHolder.mTo.setText(mData.getTo());

        mHolder.mEditPaySheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewPaysheet = new Intent(mContext, ViewPaysheet.class);
                mCallViewPaysheet.putExtra(Constants.PAYSHEETDETAILS, mData);
                mCallViewPaysheet.putExtra(Constants.MODE, 0);  // Mode 0 means edit and 1 means view
                mContext.startActivity(mCallViewPaysheet);
            }
        });

        mHolder.mViewPaysheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewPaysheet = new Intent(mContext, ViewPaysheet.class);
                mCallViewPaysheet.putExtra(Constants.PAYSHEETDETAILS, mData);
                mCallViewPaysheet.putExtra(Constants.MODE, 1); // Mode 0 means edit and 1 means view
                mContext.startActivity(mCallViewPaysheet);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mMFNo, mName, mEmail, mFrom, mTo, mDate, mViewPaysheet, mEditPaySheet;
    }
}
