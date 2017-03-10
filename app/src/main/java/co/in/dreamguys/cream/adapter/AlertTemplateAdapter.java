package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.AlertemplateAPI;

/**
 * Created by user5 on 23-02-2017.
 */
public class AlertTemplateAdapter extends BaseAdapter {
    Context mContext;
    private List<AlertemplateAPI.Datum> templateLists;
    LayoutInflater mInflater;

    public AlertTemplateAdapter(Context mContext, List<AlertemplateAPI.Datum> templateLists) {
        this.mContext = mContext;
        this.templateLists = templateLists;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return templateLists.size();
    }

    @Override
    public AlertemplateAPI.Datum getItem(int position) {
        return templateLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.acdapter_settings, null);
            mHolder = new ViewHolder();
            mHolder.mSetttings = (TextView) convertView.findViewById(R.id.AS_TV_setings);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mSetttings.setText(templateLists.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView mSetttings;
    }
}
