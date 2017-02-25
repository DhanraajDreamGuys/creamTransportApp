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
import co.in.dreamguys.cream.apis.BranchAPI;

import static co.in.dreamguys.cream.utils.Constants.From;
import static co.in.dreamguys.cream.utils.Constants.To;

/**
 * Created by user5 on 17-02-2017.
 */
public class BranchListAdapter extends BaseAdapter {

    Context mContext;
    private List<BranchAPI.Datum> countries;
    private List<BranchAPI.Datum> countriesFiltered = new ArrayList<BranchAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;
    private TextView value;
    private AlertDialog alert;
    String mFromorTo;

    public BranchListAdapter(Context mContext, List<BranchAPI.Datum> countries, TextView value, AlertDialog alert, String mFromorTo) {
        this.mContext = mContext;
        this.countries = countries;
        this.alert = alert;
        this.value = value;
        this.mFromorTo = mFromorTo;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return countriesFiltered.size();
        return countries.size();

    }

    @Override
    public BranchAPI.Datum getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BranchAPI.Datum countryname = searchEnabled ? countriesFiltered.get(position) : countries.get(position);
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.setText(countryname.getName());
                if (mFromorTo.equalsIgnoreCase("From")) {
                    From = position;
                } else {
                    To = position;
                }
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
            for (BranchAPI.Datum row : countries) {
                if (row.getName().toLowerCase().charAt(0) == searchTerm.toLowerCase().charAt(0))
                    countriesFiltered.add(row);
            }
        } else {
            for (BranchAPI.Datum row : countries) {
                if (row.getName().toLowerCase().contains(searchTerm) ||
                        row.getName().toLowerCase().contains(searchTerm)
                        ) {
                    countriesFiltered.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }

}
