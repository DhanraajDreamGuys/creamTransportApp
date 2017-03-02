package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import co.in.dreamguys.cream.CustomFields;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.UsefulLinks;
import co.in.dreamguys.cream.UserStatus;
import co.in.dreamguys.cream.utils.ActivityConstants;

/**
 * Created by user5 on 23-02-2017.
 */
public class SettingsAdapter extends BaseAdapter {
    Context mContext;
    String[] settings;
    LayoutInflater mInflater;

    public SettingsAdapter(Context mContext, String[] settings) {
        this.mContext = mContext;
        this.settings = settings;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return settings.length;
    }

    @Override
    public String getItem(int position) {
        return settings[position];
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

        mHolder.mSetttings.setText(settings[position]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        ActivityConstants.callPage(mContext, UserStatus.class);
                        break;
                    case 1:
                        ActivityConstants.callPage(mContext, CustomFields.class);
                        break;
                    case 2:
                        ActivityConstants.callPage(mContext, UsefulLinks.class);
                        break;
                }
            }
        });


        return convertView;
    }

    class ViewHolder {
        TextView mSetttings;
    }
}
