package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import co.in.dreamguys.cream.apis.UserstatuslistAPI;

/**
 * Created by user5 on 28-02-2017.
 */

public class UserStatusAdapter extends BaseAdapter {
    Context mContext;
    List<UserstatuslistAPI.User_list> data;
    LayoutInflater mInflater;

    public UserStatusAdapter(Context mContext, List<UserstatuslistAPI.User_list> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public UserstatuslistAPI.User_list getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

        } else {

        }
        return convertView;
    }
}
