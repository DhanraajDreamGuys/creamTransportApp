package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.RunsheetAPI;
import co.in.dreamguys.cream.utils.Constants;

import static co.in.dreamguys.cream.utils.Util.getDateFormat;


/**
 * Created by user5 on 20-02-2017.
 */

public class RunsheetAdapter extends BaseAdapter {
    Context mContext;
    List<RunsheetAPI.Datum> data;
    LayoutInflater mInflater;

    public RunsheetAdapter(Context mContext, List<RunsheetAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RunsheetAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_runhseet, null);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.ARS_TV_truck_no);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.ARS_TV_edit);
            mHolder.mDriverName = (TextView) convertView.findViewById(R.id.ARS_TV_driver_name);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.ARS_TV_date);
            mHolder.mFrom = (TextView) convertView.findViewById(R.id.ARS_TV_from);
            mHolder.mTo = (TextView) convertView.findViewById(R.id.ARS_TV_to);
            mHolder.mTrailerNo = (TextView) convertView.findViewById(R.id.ARS_TV_trailer_no);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        RunsheetAPI.Datum mData = data.get(position);
        mHolder.mDriverName.setText(mData.getFirst_name() + " " + mData.getLast_name());

        for (BranchAPI.Datum data : Constants.countries) {
            if (mData.getFrom().equalsIgnoreCase(data.getId())) {
                mHolder.mFrom.setText(data.getName());
            }
        }
        for (BranchAPI.Datum data : Constants.countries) {
            if (mData.getTo().equalsIgnoreCase(data.getId())) {
                mHolder.mTo.setText(data.getName());
            }
        }

        mHolder.mTrailerNo.setText(mData.getTrailers());
        mHolder.mTruckNo.setText(mData.getTruck());
        String date = getDateFormat(mData.getCreated_date());

        mHolder.mDate.setText(date);
        try {
            String data = mData.getTrailers();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(data);
            String appendValue = "";
            if (jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    appendValue += jsonArray.get(i) + ",";
                }
            }
            mHolder.mTrailerNo.setText(appendValue.replace("\"", ""));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName, mDate, mFrom, mTo, mTrailerNo, mView, mEdit, mTruckNo;
    }
}
