package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.Runsheet;

/**
 * Created by user5 on 22-02-2017.
 */
public class DaysAdapter extends BaseAdapter {
    Context mContext;
    Runsheet.Day[] values;
    private LayoutInflater mInflater;

    public DaysAdapter(Context mContext, Runsheet.Day[] values) {
        this.mContext = mContext;
        this.values = values;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Runsheet.Day getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Runsheet.Day days = values[position];
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_driver_name, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ADN_TV_driver_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mDriverName.setText(days.toString());
        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName;
    }

}
