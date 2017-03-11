package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.StaffreportAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 23-02-2017.
 */
public class LocationAdapter extends BaseAdapter {
    Context mContext;
    private List<StaffreportAPI.Datum> templateLists;
    LayoutInflater mInflater;

    public LocationAdapter(Context mContext, List<StaffreportAPI.Datum> staffReportData) {
        this.mContext = mContext;
        templateLists = staffReportData;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return templateLists.size();
    }

    @Override
    public StaffreportAPI.Datum getItem(int position) {
        return templateLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        StaffreportAPI.Datum mData = templateLists.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.acdapter_settings, null);
            mHolder = new ViewHolder();
            mHolder.mSetttings = (TextView) convertView.findViewById(R.id.AS_TV_setings);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        for (BranchAPI.Datum branch : Constants.countries) {
            if (branch.getId().equalsIgnoreCase(mData.getLocation())) {
                mHolder.mSetttings.setText(branch.getName());
            }

        }

        return convertView;
    }

    class ViewHolder {
        TextView mSetttings;
    }
}
