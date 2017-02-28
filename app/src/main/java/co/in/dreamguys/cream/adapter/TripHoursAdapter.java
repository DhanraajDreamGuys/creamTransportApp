package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.EditTripHours;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.TripHoursAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 28-02-2017.
 */

public class TripHoursAdapter extends BaseAdapter {
    Context mContext;
    List<TripHoursAPI.Datum> data;
    LayoutInflater mInflater;

    public TripHoursAdapter(Context mContext, List<TripHoursAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TripHoursAPI.Datum getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_trip_hours, null);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.ATH_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.ATH_TV_to);
            mHolder.mHours = (TextView) convertView.findViewById(R.id.ATH_TV_hours);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.ATH_TV_edit);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final TripHoursAPI.Datum mData = data.get(position);
        mHolder.mFrom.setText(mData.getP1());
        mHolder.mTo.setText(mData.getP2());
        mHolder.mHours.setText(mData.getHours() + " hr");

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallEditTrip = new Intent(mContext, EditTripHours.class);
                mCallEditTrip.putExtra(Constants.EDIT_TRIP_HR_DATA, mData);
                mContext.startActivity(mCallEditTrip);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView mFrom, mTo, mHours, mEdit;
    }
}
