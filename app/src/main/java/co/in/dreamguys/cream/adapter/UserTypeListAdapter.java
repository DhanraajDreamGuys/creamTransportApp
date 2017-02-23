package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.UserTypeAPI;

/**
 * Created by user5 on 22-02-2017.
 */
public class UserTypeListAdapter extends BaseAdapter{
    Context mContext;
    private List<UserTypeAPI.Datum> usertype;
    private LayoutInflater mInflater;

    public UserTypeListAdapter(Context mContext, List<UserTypeAPI.Datum> usertype) {
        this.mContext = mContext;
        this.usertype = usertype;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return usertype.size();
    }

    @Override
    public UserTypeAPI.Datum getItem(int position) {
        return usertype.get(position);
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

        mHolder.mDriverName.setText(usertype.get(position).getName());

        return convertView;
    }

    private class ViewHolder {
        TextView mDriverName;
    }
}
