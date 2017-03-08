package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.EditPBTruck;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewPBTruck;
import co.in.dreamguys.cream.apis.PBTruckAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 06-03-2017.
 */

public class PBTruckAdapter extends BaseAdapter {
    Context mContext;
    List<PBTruckAPI.Datum> data;
    private List<PBTruckAPI.Datum> dataDupliate = new ArrayList<PBTruckAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;

    public PBTruckAdapter(Context mContext, List<PBTruckAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return dataDupliate.size();
        return data.size();
    }

    @Override
    public PBTruckAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PBTruckAPI.Datum mData = searchEnabled ? dataDupliate.get(position) : data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_pb_truck, null);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.APBT_TV_truck_no);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.APBT_TV_status);
            mHolder.mPhone = (TextView) convertView.findViewById(R.id.APBT_TV_phone_no);
            mHolder.mView = (TextView) convertView.findViewById(R.id.APBT_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.APBT_TV_edit);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTruckNo.setText(mData.getTruck_no());
        mHolder.mPhone.setText(mData.getPhone_no());
        if (mData.getStatus().equalsIgnoreCase("0")) {
            mHolder.mStatus.setText(mContext.getString(R.string.active));
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.accept_color));
        } else {
            mHolder.mStatus.setText(mContext.getString(R.string.inactive));
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.inactive_color));
        }


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallView = new Intent(mContext, ViewPBTruck.class);
                mCallView.putExtra(Constants.PB_TRUCK_DATA, mData);
                mContext.startActivity(mCallView);
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallView = new Intent(mContext, EditPBTruck.class);
                mCallView.putExtra(Constants.PB_TRUCK_DATA, mData);
                mContext.startActivity(mCallView);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView mTruckNo, mStatus, mPhone, mView, mEdit;
    }


    public void setSearchEnabled(boolean enabled, String text) {
        searchEnabled = enabled;
        if (!searchEnabled) {
            searchTerm = "";
            dataDupliate.clear();
            notifyDataSetChanged();
            return;
        }
        searchTerm = text.toLowerCase();
        filter();
    }


    private void filter() {
        dataDupliate.clear();
        if (searchTerm.length() == 0) {
            dataDupliate.addAll(data);
        } else if (searchTerm.length() == 1) {
            for (PBTruckAPI.Datum row : data) {
                if (row.getTruck_no().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    dataDupliate.add(row);
            }
        } else {
            for (PBTruckAPI.Datum row : data) {
                if (row.getTruck_no().toLowerCase().contains(searchTerm)) {
                    dataDupliate.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }
}
