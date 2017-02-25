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
import co.in.dreamguys.cream.apis.ListCountriesAPI;

/**
 * Created by user5 on 22-02-2017.
 */
public class CountryListAdapter extends BaseAdapter {
    Context mContext;
    private List<ListCountriesAPI.Datum> countries;
    private List<ListCountriesAPI.Datum> countriesFiltered = new ArrayList<ListCountriesAPI.Datum>();
    private LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;
    private AlertDialog alert;
    private TextView mCountry;

    public CountryListAdapter(Context mContext, List<ListCountriesAPI.Datum> countries, AlertDialog alert, TextView mCountry) {
        this.mContext = mContext;
        this.countries = countries;
        this.alert = alert;
        this.mCountry = mCountry;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return countriesFiltered.size();
        return countries.size();
    }

    @Override
    public ListCountriesAPI.Datum getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListCountriesAPI.Datum countryname = searchEnabled ? countriesFiltered.get(position) : countries.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_driver_name, null);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ADN_TV_driver_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mDriverName.setText(countryname.getCountry());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountry.setText(countryname.getCountry());
                alert.dismiss();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName;
    }

    public void setSearchEnabled(boolean enabled, String text) {
        searchEnabled = enabled;
        if (!searchEnabled) {
            searchTerm = "";
            countriesFiltered.clear();
            notifyDataSetChanged();
            return;
        }
        searchTerm = text.toLowerCase();
        filter();
    }


    private void filter() {
        countriesFiltered.clear();
        if (searchTerm.length() == 0) {
            countriesFiltered.addAll(countries);
        } else if (searchTerm.length() == 1) {
            for (ListCountriesAPI.Datum row : countries) {
                if (row.getCountry().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    countriesFiltered.add(row);
            }
        } else {
            for (ListCountriesAPI.Datum row : countries) {
                if (row.getCountry().toLowerCase().contains(searchTerm)) {
                    countriesFiltered.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }
}
