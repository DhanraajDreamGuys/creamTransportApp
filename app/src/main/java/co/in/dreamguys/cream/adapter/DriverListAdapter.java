package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.DriverListsAPI;

/**
 * Created by user5 on 14-02-2017.
 */
public class DriverListAdapter extends BaseAdapter {
    Context mContext;
    List<DriverListsAPI.Datum> driverList;
    LayoutInflater mInflater;

    public DriverListAdapter(Context mContext, List<DriverListsAPI.Datum> driverList) {
        this.mContext = mContext;
        this.driverList = driverList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return driverList.size();
    }

    @Override
    public DriverListsAPI.Datum getItem(int position) {
        return driverList.get(position);
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
            convertView = mInflater.inflate(R.layout.adapter_driver_name, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ADN_TV_driver_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mDriverName.setText(driverList.get(position).getFirst_name() + " " + driverList.get(position).getLast_name());

        return convertView;
    }

    public class ViewHolder {
        TextView mDriverName;
    }
}
