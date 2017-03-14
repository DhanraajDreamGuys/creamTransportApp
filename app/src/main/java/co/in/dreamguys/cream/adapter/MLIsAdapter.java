package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.MLIAPI;
import co.in.dreamguys.cream.utils.Constants;


/**
 * Created by user5 on 20-02-2017.
 */

public class MLIsAdapter extends BaseAdapter {
    Context mContext;
    List<MLIAPI.Datum> data;
    LayoutInflater mInflater;

    public MLIsAdapter(Context mContext, List<MLIAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MLIAPI.Datum getItem(int position) {
        return data.get(position);
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
            convertView = mInflater.inflate(R.layout.adapter_mlis, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.AMLIs_TV_driver_name);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.AMLIs_TV_status);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.AMLIs_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.AMLIs_TV_to);
            mHolder.mReason = (TextView) convertView.findViewById(R.id.AMLIs_TV_reason);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MLIAPI.Datum mData = data.get(position);
        mHolder.mDriverName.setText(mData.getFirst_name() + " " + mData.getLast_name());
        if (mData.getStatus().equalsIgnoreCase("1")) {
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.accept_color));
            mHolder.mStatus.setText(mContext.getString(R.string.str_accept));
        } else if (mData.getStatus().equalsIgnoreCase("2")) {
            mHolder.mStatus.setBackgroundColor(Color.RED);
            mHolder.mStatus.setText(mContext.getString(R.string.str_denied));
        } else {
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.app_color));
            mHolder.mStatus.setText(mContext.getString(R.string.str_wait));
        }

        for (BranchAPI.Datum data : Constants.countries) {
            if (mData.getFrom().equalsIgnoreCase(data.getId())) {
                mHolder.mFrom.setText(data.getName());
            }
        }

        for (BranchAPI.Datum data : Constants.countries) {
            if (mData.getTo().equalsIgnoreCase(data.getId())) {
                mHolder.mTo.setText(data.getName());
            }
        }

        if (mData.getReason().isEmpty())
            mHolder.mReason.setVisibility(View.GONE);
        else
            mHolder.mReason.setText(mData.getReason());

        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName, mStatus, mFrom, mTo, mReason;
    }
}
