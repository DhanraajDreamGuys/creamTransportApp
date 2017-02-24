package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.EditLeave;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.LeaveAPI;
import co.in.dreamguys.cream.utils.Constants;

import static co.in.dreamguys.cream.utils.Util.getDateFormat;


/**
 * Created by user5 on 23-02-2017.
 */
public class LeaveListAdapter extends BaseAdapter {

    Context mContext;
    List<LeaveAPI.Datum> data;
    LayoutInflater mInflater;

    public LeaveListAdapter(Context mContext, List<LeaveAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public LeaveAPI.Datum getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_leave_list, null);
            mHolder.mName = (TextView) convertView.findViewById(R.id.ALL_TV_name);
            mHolder.mType = (TextView) convertView.findViewById(R.id.ALL_TV_type);
            mHolder.mFromDate = (TextView) convertView.findViewById(R.id.ALL_TV_from_date);
            mHolder.mToDate = (TextView) convertView.findViewById(R.id.ALL_TV_to_date);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.ALL_TV_edit);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final LeaveAPI.Datum mLeaveData = data.get(position);
        mHolder.mName.setText(mLeaveData.getFirst_name() + " " + mLeaveData.getLast_name());
        mHolder.mType.setText(mLeaveData.getLeavetype());
        String mFromdate = getDateFormat(mLeaveData.getFromdate());
        String mTodate = getDateFormat(mLeaveData.getTodate());
        mHolder.mFromDate.setText(mFromdate);
        mHolder.mToDate.setText(mTodate);


        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallEdit = new Intent(mContext, EditLeave.class);
                mCallEdit.putExtra(Constants.EDIT_LEAVE_DATA, mLeaveData);
                mContext.startActivity(mCallEdit);
            }
        });

        return convertView;
    }



    class ViewHolder {
        TextView mName, mType, mFromDate, mToDate, mEdit;
    }
}
