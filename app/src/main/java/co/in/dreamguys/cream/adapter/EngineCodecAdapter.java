package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.EditEngineCodec;
import co.in.dreamguys.cream.EngineCodec;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewEngineCodec;
import co.in.dreamguys.cream.apis.FridgeCodeAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 27-02-2017.
 */
public class EngineCodecAdapter extends BaseAdapter {
    Context mContext;
    List<FridgeCodeAPI.Datum> data;
    List<FridgeCodeAPI.Datum> dataDupliate = new ArrayList<FridgeCodeAPI.Datum>();
    LayoutInflater mInflater;
    private boolean searchEnabled = false;
    private String searchTerm;


    public EngineCodecAdapter(Context mContext, List<FridgeCodeAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (searchEnabled)
            return dataDupliate.size();
        return data.size();
    }

    @Override
    public FridgeCodeAPI.Datum getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FridgeCodeAPI.Datum mData = searchEnabled ? dataDupliate.get(position) : data.get(position);
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_fridge_codec, null);
            mHolder.mCodeNo = (TextView) convertView.findViewById(R.id.AFC_TV_code_no);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.AFC_TV_status);
            mHolder.mDesc = (TextView) convertView.findViewById(R.id.AFC_TV_desc);
            mHolder.mView = (TextView) convertView.findViewById(R.id.AFC_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.AFC_TV_edit);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mCodeNo.setText(mData.getCode());
        if (mData.getStatus().equalsIgnoreCase("0")) {
            mHolder.mStatus.setText(mContext.getString(R.string.active));
            mHolder.mStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.accept_color));
        } else {
            mHolder.mStatus.setText(mContext.getString(R.string.inactive));
            mHolder.mStatus.setBackgroundColor(Color.RED);
        }

        mHolder.mDesc.setText(mData.getDescription());


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallFridgeCodec = new Intent(mContext, ViewEngineCodec.class);
                mCallFridgeCodec.putExtra(Constants.ENGINE_CODE_DATA, mData);
                mContext.startActivity(mCallFridgeCodec);
                if (EngineCodec.searchView != null)
                    EngineCodec.searchView.onActionViewCollapsed();
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallFridgeCodec = new Intent(mContext, EditEngineCodec.class);
                mCallFridgeCodec.putExtra(Constants.ENGINE_CODE_DATA, mData);
                mContext.startActivity(mCallFridgeCodec);
                if (EngineCodec.searchView != null)
                    EngineCodec.searchView.onActionViewCollapsed();
            }
        });


        return convertView;
    }

    class ViewHolder {
        TextView mCodeNo, mStatus, mDesc, mView, mEdit;
    }

    public void setSearchEnabled(boolean enabled, String text) {
        searchEnabled = enabled;
        if (!searchEnabled) {
            searchTerm = "";
            dataDupliate.clear();
            notifyDataSetChanged();
            return;
        }
        searchTerm = text;
        filter();
    }


    private void filter() {
        dataDupliate.clear();
        if (searchTerm.length() == 0) {
            dataDupliate.addAll(data);
        } else if (searchTerm.length() == 1) {
            for (FridgeCodeAPI.Datum row : data) {
                if (row.getCode().charAt(0) == searchTerm.charAt(0))
                    dataDupliate.add(row);
            }
        } else {
            for (FridgeCodeAPI.Datum row : data) {
                if (row.getCode().contains(searchTerm)) {
                    dataDupliate.add(row);
                }
            }
        }
        notifyDataSetChanged();
    }
}
