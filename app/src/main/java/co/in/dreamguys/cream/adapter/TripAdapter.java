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
import co.in.dreamguys.cream.Trips;
import co.in.dreamguys.cream.apis.CountriesAPI;
import co.in.dreamguys.cream.model.TripList;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.Util;

/**
 * Created by user5 on 20-02-2017.
 */

public class TripAdapter extends BaseAdapter {
    Context mContext;
    List<TripList> data;
    LayoutInflater mInflater;

    public TripAdapter(Context mContext, List<TripList> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TripList getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_trips, null);
            mHolder.mDelete = (TextView) convertView.findViewById(R.id.AT_TV_delete);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.AT_TV_edit);
            mHolder.mView = (TextView) convertView.findViewById(R.id.AT_TV_view);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.AT_TV_driver_name);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.AT_TV_status);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.AT_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.AT_TV_to);
            mHolder.mReason = (TextView) convertView.findViewById(R.id.AT_TV_reason);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        TripList mTripList = data.get(position);
        mHolder.mDriverName.setText(mTripList.getFirst_name() + " " + mTripList.getLast_name());
        if (mTripList.getStatus().equalsIgnoreCase("1")) {
            mHolder.mStatus.setBackgroundColor(Color.GREEN);
            mHolder.mStatus.setText(mContext.getString(R.string.str_accept));
        } else if (mTripList.getStatus().equalsIgnoreCase("2")) {
            mHolder.mStatus.setBackgroundColor(Color.RED);
            mHolder.mStatus.setText(mContext.getString(R.string.str_denied));
        } else {
            mHolder.mStatus.setBackgroundColor(Color.BLUE);
            mHolder.mStatus.setText(mContext.getString(R.string.str_wait));
        }

        for (CountriesAPI.Datum data : Constants.countries) {
            if (mTripList.getFrom().equalsIgnoreCase(data.getId())) {
                mHolder.mFrom.setText(data.getName());
            } else if (mTripList.getTo().equalsIgnoreCase(data.getId())) {
                mHolder.mTo.setText(data.getName());
            }
        }
        mHolder.mReason.setText(mTripList.getReason());


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Trips) mContext).view(data.get(position).getTid(), 1);
            }
        });

        mHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showDeleteAlert(mContext, data.get(position).getTid(), position ,Constants.TRIPS);
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Trips) mContext).view(data.get(position).getTid(), 0);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName, mStatus, mFrom, mTo, mReason, mView, mEdit, mDelete;
    }
}
