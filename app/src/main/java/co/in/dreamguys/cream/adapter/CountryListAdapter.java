package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.CountriesAPI;

/**
 * Created by user5 on 17-02-2017.
 */
public class CountryListAdapter extends BaseAdapter {

    Context mContext;
    List<CountriesAPI.Datum> countries;
    LayoutInflater mInflater;


    public CountryListAdapter(Context mContext, List<CountriesAPI.Datum> countries) {
        this.mContext = mContext;
        this.countries = countries;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public CountriesAPI.Datum getItem(int position) {
        return countries.get(position);
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

        mHolder.mDriverName.setText(countries.get(position).getName());

        return convertView;
    }

    public class ViewHolder {
        TextView mDriverName;
    }

}
