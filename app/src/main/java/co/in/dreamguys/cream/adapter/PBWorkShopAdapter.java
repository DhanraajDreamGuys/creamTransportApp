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

import co.in.dreamguys.cream.EditPBWorkshop;
import co.in.dreamguys.cream.PBWorkShop;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewPBWorkshop;
import co.in.dreamguys.cream.apis.PBWorkShopAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 06-03-2017.
 */

public class PBWorkShopAdapter extends BaseAdapter {
    Context mContext;
    List<PBWorkShopAPI.Datum> data;
    private List<PBWorkShopAPI.Datum> dataDupliate = new ArrayList<PBWorkShopAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;

    public PBWorkShopAdapter(PBWorkShop mContext, List<PBWorkShopAPI.Datum> data) {
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
    public PBWorkShopAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PBWorkShopAPI.Datum mData = searchEnabled ? dataDupliate.get(position) : data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_pb_workshop, null);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.APBWS_TV_truck_no);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.APBWS_TV_status);
            mHolder.mPhone = (TextView) convertView.findViewById(R.id.APBWS_TV_phone_no);
            mHolder.mIncharge = (TextView) convertView.findViewById(R.id.APBWS_TV_incharge);
            mHolder.mView = (TextView) convertView.findViewById(R.id.APBWS_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.APBWS_TV_edit);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTruckNo.setText(mData.getName());
        mHolder.mPhone.setText(mData.getPhone_no());
        if (mData.getStatus().equalsIgnoreCase("0")) {
            mHolder.mStatus.setText(mContext.getString(R.string.active));
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.accept_color));
        } else {
            mHolder.mStatus.setText(mContext.getString(R.string.inactive));
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.inactive_color));
        }
        mHolder.mIncharge.setText(mData.getManager());


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallView = new Intent(mContext, ViewPBWorkshop.class);
                mCallView.putExtra(Constants.PB_WORKSHOP_DATA, mData);
                mContext.startActivity(mCallView);
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallView = new Intent(mContext, EditPBWorkshop.class);
                mCallView.putExtra(Constants.PB_WORKSHOP_DATA, mData);
                mContext.startActivity(mCallView);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mTruckNo, mStatus, mPhone, mView, mEdit, mIncharge;
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
            for (PBWorkShopAPI.Datum row : data) {
                if (row.getName().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    dataDupliate.add(row);
            }
        } else {
            for (PBWorkShopAPI.Datum row : data) {
                if (row.getName().toLowerCase().contains(searchTerm)) {
                    dataDupliate.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }
}
