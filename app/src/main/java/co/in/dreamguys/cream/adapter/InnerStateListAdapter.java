package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.InnerStateAPI;

/**
 * Created by user5 on 17-02-2017.
 */
public class InnerStateListAdapter extends BaseAdapter {

    Context mContext;
    private List<InnerStateAPI.Datum> innerstate;
    private List<InnerStateAPI.Datum> innerstateFiltered = new ArrayList<InnerStateAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;

    public InnerStateListAdapter(Context mContext, List<InnerStateAPI.Datum> innerstate) {
        this.mContext = mContext;
        this.innerstate = innerstate;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return innerstateFiltered.size();
        return innerstate.size();

    }

    @Override
    public InnerStateAPI.Datum getItem(int position) {
        if (searchEnabled)
            return innerstateFiltered.get(position);
        return innerstate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final InnerStateAPI.Datum countryname = searchEnabled ? innerstateFiltered.get(position) : innerstate.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_driver_name, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ADN_TV_driver_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mDriverName.setText(countryname.getName());


        return convertView;
    }

    public class ViewHolder {
        TextView mDriverName;
    }

    public void setSearchEnabled(boolean enabled, String text) {
        searchEnabled = enabled;
        if (!searchEnabled) {
            searchTerm = "";
            innerstateFiltered.clear();
            notifyDataSetChanged();
            return;
        }
        searchTerm = text.toLowerCase();
        filter();
    }


    private void filter() {
        innerstateFiltered.clear();
        if (searchTerm.length() == 0) {
            innerstateFiltered.addAll(innerstate);
        } else if (searchTerm.length() == 1) {
            for (InnerStateAPI.Datum row : innerstate) {
                if (row.getName().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    innerstateFiltered.add(row);
            }
        } else {
            for (InnerStateAPI.Datum row : innerstate) {
                if (row.getName().toLowerCase().contains(searchTerm) ||
                        row.getName().toLowerCase().contains(searchTerm)
                        ) {
                    innerstateFiltered.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }

}
