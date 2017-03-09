package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewTyreRepair;
import co.in.dreamguys.cream.apis.TyreRepairAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.Util;


/**
 * Created by user5 on 20-02-2017.
 */

public class TyreRepairAdapter extends BaseAdapter {
    Context mContext;
    List<TyreRepairAPI.Datum> data;
    LayoutInflater mInflater;

    public TyreRepairAdapter(Context mContext, List<TyreRepairAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TyreRepairAPI.Datum getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_tyre_repair, null);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.ATR_TV_edit);
            mHolder.mView = (TextView) convertView.findViewById(R.id.ATR_TV_view);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ATR_TV_driver_name);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.ATR_TV_status);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.ATR_TV_from);
            mHolder.mDollyNo = (TextView) convertView.findViewById(R.id.ATR_TV_dolly_no);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.ATR_TV_truck_no);
            mHolder.mTrailNo = (TextView) convertView.findViewById(R.id.ATR_TV_trailer_no);
            mHolder.mEmail = (TextView) convertView.findViewById(R.id.ATR_TV_email);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final TyreRepairAPI.Datum mTyreList = data.get(position);
        mHolder.mDriverName.setText(mTyreList.getFirst_name() + " " + mTyreList.getLast_name());
        if (mTyreList.getApproval().equalsIgnoreCase("2")) {
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.accept_color));
            mHolder.mStatus.setText(mContext.getString(R.string.str_approved));
        } else if (mTyreList.getApproval().equalsIgnoreCase("0")) {
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.app_color));
            mHolder.mStatus.setText(mContext.getString(R.string.str_new));
        } else if (mTyreList.getApproval().equalsIgnoreCase("1")) {
            mHolder.mStatus.setBackgroundColor(mContext.getColor(R.color.orange_color));
            mHolder.mStatus.setText(mContext.getString(R.string.str_head));
        } else if (mTyreList.getApproval().equalsIgnoreCase("3")) {
            mHolder.mStatus.setBackgroundColor(Color.YELLOW);
            mHolder.mStatus.setText(mContext.getString(R.string.str_on_hold));
        }
        mHolder.mTrailNo.setText(mTyreList.getTrailer());

        mHolder.mEmail.setText(mTyreList.getPname());

        String date = Util.getDateFormat(mTyreList.getCdate());
        mHolder.mFrom.setText(date);

        JsonObject truckObject = new Gson().fromJson(mTyreList.getTruck(), JsonObject.class);
        mHolder.mTruckNo.setText(truckObject.get("tno").toString().replace("\"", ""));
        mHolder.mTrailNo.setText(truckObject.get("trno").toString().replace("\"", ""));
        JsonObject dollyObject = new Gson().fromJson(mTyreList.getDolly(), JsonObject.class);
        mHolder.mDollyNo.setText(dollyObject.get("dno").toString().replace("\"", ""));

        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewTyreRepair = new Intent(mContext, ViewTyreRepair.class);
                mCallViewTyreRepair.putExtra(Constants.TYRE_REPAIR_DATA, mTyreList);
                mContext.startActivity(mCallViewTyreRepair);
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewTyreRepair = new Intent(mContext, ViewTyreRepair.class);
                mCallViewTyreRepair.putExtra(Constants.TYRE_REPAIR_DATA, mTyreList);
                mContext.startActivity(mCallViewTyreRepair);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName, mStatus, mFrom, mEmail, mView, mEdit, mTruckNo, mTrailNo, mDollyNo;
    }
}
