package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import co.in.dreamguys.cream.R;

/**
 * Created by user5 on 27-02-2017.
 */
public class TypeListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    private ArrayList mData;

    public TypeListAdapter(Context mContext, Map<String, String> map) {
        this.mContext = mContext;
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry<String, String>) mData.get(position);
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
            convertView = mInflater.inflate(R.layout.adapter_type, null);
            mHolder.mType = (TextView) convertView.findViewById(R.id.AT_TV_type);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        Map.Entry<String, String> type = getItem(position);

        mHolder.mType.setText(type.getValue());

        return convertView;
    }

    class ViewHolder {
        TextView mType;
    }
}
