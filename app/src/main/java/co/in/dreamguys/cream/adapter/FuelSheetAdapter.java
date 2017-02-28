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

import co.in.dreamguys.cream.Fuelsheet;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewFuelsheet;
import co.in.dreamguys.cream.model.FuelSheetModel;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.Util;

/**
 * Created by user5 on 25-02-2017.
 */

public class FuelSheetAdapter extends BaseAdapter {
    Context mContext;
    private List<FuelSheetModel> data;
    private List<FuelSheetModel> dataDupliate = new ArrayList<FuelSheetModel>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;

    public FuelSheetAdapter(Context mContext, List<FuelSheetModel> data) {
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
    public FuelSheetModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FuelSheetModel mData = searchEnabled ? dataDupliate.get(position) : data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_fuel_sheet, null);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.AFS_TV_date);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.AFS_TV_truck_no);
            mHolder.mName = (TextView) convertView.findViewById(R.id.AFS_TV_name);
            mHolder.mEmail = (TextView) convertView.findViewById(R.id.AFS_TV_email);
            mHolder.mLocation = (TextView) convertView.findViewById(R.id.AFS_TV_location);
            mHolder.mDiseal = (TextView) convertView.findViewById(R.id.AFS_TV_diseal);
            mHolder.mView = (TextView) convertView.findViewById(R.id.AFS_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.AFS_TV_edit);
            mHolder.mDelete = (TextView) convertView.findViewById(R.id.AFS_TV_delete);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTruckNo.setText(mData.getTruckNo());
        mHolder.mName.setText(mData.getFirstName() + " " + mData.getLastName());
        mHolder.mEmail.setText(mData.getEmail());
        mHolder.mLocation.setText(mData.getPlace());
        mHolder.mDiseal.setText(mData.getDiesel());
        String mFDate = Util.getDateFormat(mData.getFdate());
        mHolder.mDate.setText(mFDate);


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallFuelSheet = new Intent(mContext, ViewFuelsheet.class);
                mCallFuelSheet.putExtra(Constants.FUEL_SHEET, 1);
                mCallFuelSheet.putExtra(Constants.FUEL_SHEET_DATA, mData);
                mContext.startActivity(mCallFuelSheet);
                if (Fuelsheet.searchView != null)
                    Fuelsheet.searchView.onActionViewCollapsed();
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallFuelSheet = new Intent(mContext, ViewFuelsheet.class);
                mCallFuelSheet.putExtra(Constants.FUEL_SHEET, 0);
                mCallFuelSheet.putExtra(Constants.FUEL_SHEET_DATA, mData);
                mContext.startActivity(mCallFuelSheet);
                if (Fuelsheet.searchView != null)
                    Fuelsheet.searchView.onActionViewCollapsed();
                            }
        });


        mHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showDeleteAlert(mContext, mData.getFid(), position, Constants.FUELSHEET);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView mTruckNo, mDate, mName, mEmail, mLocation, mDiseal, mView, mEdit, mDelete;
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
            for (FuelSheetModel row : data) {
                if (row.getTruckNo().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    dataDupliate.add(row);
            }
        } else {
            for (FuelSheetModel row : data) {
                if (row.getTruckNo().toLowerCase().contains(searchTerm)) {
                    dataDupliate.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }
}
