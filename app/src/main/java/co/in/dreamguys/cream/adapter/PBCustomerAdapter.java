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

import co.in.dreamguys.cream.EditPBCustomers;
import co.in.dreamguys.cream.PBCustomers;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewPBCustomers;
import co.in.dreamguys.cream.apis.PBCustomerAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 06-03-2017.
 */

public class PBCustomerAdapter extends BaseAdapter {
    Context mContext;
    List<PBCustomerAPI.Datum> data;
    private List<PBCustomerAPI.Datum> dataDupliate = new ArrayList<PBCustomerAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;

    public PBCustomerAdapter(PBCustomers mContext, List<PBCustomerAPI.Datum> data) {
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
    public PBCustomerAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PBCustomerAPI.Datum mData = searchEnabled ? dataDupliate.get(position) : data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_pb_customer, null);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.APBC_TV_truck_no);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.APBC_TV_status);
            mHolder.mPhone = (TextView) convertView.findViewById(R.id.APBC_TV_phone_no);
            mHolder.mIncharge = (TextView) convertView.findViewById(R.id.APBC_TV_incharge);
            mHolder.mView = (TextView) convertView.findViewById(R.id.APBC_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.APBC_TV_edit);

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
        mHolder.mIncharge.setText(mData.getCity());


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallView = new Intent(mContext, ViewPBCustomers.class);
                mCallView.putExtra(Constants.PB_CUSTOMER_DATA, mData);
                mContext.startActivity(mCallView);
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallView = new Intent(mContext, EditPBCustomers.class);
                mCallView.putExtra(Constants.PB_CUSTOMER_DATA, mData);
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
            for (PBCustomerAPI.Datum row : data) {
                if (row.getName().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    dataDupliate.add(row);
            }
        } else {
            for (PBCustomerAPI.Datum row : data) {
                if (row.getName().toLowerCase().contains(searchTerm)) {
                    dataDupliate.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }
}
