package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.EditUsefulLinks;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.UsefulLinksAPI;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.DropListener;
import co.in.dreamguys.cream.utils.RemoveListener;

/**
 * Created by Dhanraaj on 3/2/2017.
 */
public class UseFulLinkAdapter extends BaseAdapter implements RemoveListener, DropListener {
    Context mContext;
    List<UsefulLinksAPI.Datum> data;
    LayoutInflater mInflater;

    public UseFulLinkAdapter(Context mContext, List<UsefulLinksAPI.Datum> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public UsefulLinksAPI.Datum getItem(int position) {
        return data.get(position);
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
            convertView = mInflater.inflate(R.layout.adapter_useful_links, null);
            mHolder.mName = (TextView) convertView.findViewById(R.id.AUL_TV_name);
            mHolder.mStatus = (TextView) convertView.findViewById(R.id.AUL_TV_status);
            mHolder.mLinks = (TextView) convertView.findViewById(R.id.AUL_TV_links);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.AUL_TV_edit);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final UsefulLinksAPI.Datum mData = data.get(position);

        mHolder.mName.setText(mData.getName());
        if (mData.getStatus().equalsIgnoreCase("0")) {
            mHolder.mStatus.setText(mContext.getString(R.string.active));
            mHolder.mStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.accept_color));
        } else {
            mHolder.mStatus.setText(mContext.getString(R.string.inactive));
            mHolder.mStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.inactive_color));
        }
        mHolder.mLinks.setText(mData.getLink());

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallEditUseFulink = new Intent(mContext, EditUsefulLinks.class);
                mCallEditUseFulink.putExtra(Constants.EDIT_USEFUL_LINKS, mData);
                mContext.startActivity(mCallEditUseFulink);
            }
        });

        return convertView;
    }

    @Override
    public void onDrop(int from, int to) {
        UsefulLinksAPI.Datum temp = data.get(from);
        data.remove(from);
        data.add(to, temp);
    }

    @Override
    public void onRemove(int which) {
        if (which < 0 || which > data.size()) return;
        data.remove(which);
    }

    class ViewHolder {
        TextView mName, mStatus, mLinks, mEdit;
    }
}
