package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.DriverListsAPI;

import static co.in.dreamguys.cream.utils.Util.adapterPosition;

/**
 * Created by user5 on 14-02-2017.
 */
public class DriverListAdapter extends BaseAdapter {
    Context mContext;
    private List<DriverListsAPI.Datum> driverList;
    private List<DriverListsAPI.Datum> driverListFiltered = new ArrayList<DriverListsAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;
    private AlertDialog alert;
    private TextView value;

    public DriverListAdapter(Context mContext, List<DriverListsAPI.Datum> driverList, AlertDialog alert, TextView value) {
        this.mContext = mContext;
        this.driverList = driverList;
        this.alert = alert;
        this.value = value;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return driverListFiltered.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final DriverListsAPI.Datum temp = searchEnabled ? driverListFiltered.get(position) : driverList.get(position);

        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_driver_name, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ADN_TV_driver_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mDriverName.setText(temp.getFirst_name() + " " + temp.getLast_name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.setText(temp.getFirst_name() + " " + temp.getLast_name());
                adapterPosition = position;
                alert.dismiss();
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView mDriverName;
    }


    public void setSearchEnabled(boolean enabled, String text) {
        searchEnabled = enabled;
        if (!searchEnabled) {
            searchTerm = "";
            driverListFiltered.clear();
            notifyDataSetChanged();
            return;
        }
        searchTerm = text.toLowerCase();
        filter();
    }


    private void filter() {
        driverListFiltered.clear();
        if (searchTerm.length() == 0) {
            driverListFiltered.addAll(driverList);
        } else if (searchTerm.length() == 1) {
            for (DriverListsAPI.Datum row : driverList) {
                if (row.getFirst_name().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    driverListFiltered.add(row);
            }
        } else {
            for (DriverListsAPI.Datum row : driverList) {
                if (row.getFirst_name().toLowerCase().contains(searchTerm) ||
                        row.getLast_name().toLowerCase().contains(searchTerm)
                        ) {
                    driverListFiltered.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }


}
