package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.EditUserStatus;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.UserstatuslistAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 28-02-2017.
 */

public class UserStatusAdapter extends BaseAdapter {
    Context mContext;
    List<UserstatuslistAPI.User_list> data;
    LayoutInflater mInflater;

    public UserStatusAdapter(Context mContext, List<UserstatuslistAPI.User_list> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public UserstatuslistAPI.User_list getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_user_status, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.AUS_TV_driver_name);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.AUS_TV_status);
            mHolder.mReason = (TextView) convertView.findViewById(R.id.AUS_TV_reason);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.AUS_TV_edit);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final UserstatuslistAPI.User_list mData = data.get(position);
        mHolder.mDriverName.setText(mData.getFirst_name() + " " + mData.getLast_name());
        if (mData.getWorking_sts().equalsIgnoreCase("on")) {
            mHolder.mStatus.setText(mContext.getString(R.string.active));
            mHolder.mStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.accept_color));
        } else {
            mHolder.mStatus.setText(mContext.getString(R.string.inactive));
            mHolder.mStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.inactive_color));
        }

        mHolder.mReason.setText(mData.getReason());

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallEditUserStatus = new Intent(mContext, EditUserStatus.class);
                mCallEditUserStatus.putExtra(Constants.USER_STATUS_DATA, mData);
                mContext.startActivity(mCallEditUserStatus);
            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView mDriverName, mStatus, mReason, mEdit;
    }
}
