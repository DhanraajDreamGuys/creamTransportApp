package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.apis.DriverListsAPI;
import co.in.dreamguys.cream.utils.Constants;

/**
 * Created by user5 on 09-03-2017.
 */

public class AlertAllAdapter extends BaseAdapter {
    Context mContext;
    private List<DriverListsAPI.Datum> driverList;
    LayoutInflater mInflater;
    private int mCheckedItem = -1;
    private StringBuilder mIds = new StringBuilder();
    private StringBuilder mEmailIds = new StringBuilder();
    private String selectedIds = "", selectedEmailIds = "";
    ArrayList<String> mselectedEmails = new ArrayList<String>();
    ArrayList<String> mselectedIds = new ArrayList<String>();

    public AlertAllAdapter(Context mContext, List<DriverListsAPI.Datum> driverList) {
        this.mContext = mContext;
        this.driverList = driverList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return driverList.size();
    }

    @Override
    public DriverListsAPI.Datum getItem(int position) {
        return driverList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DriverListsAPI.Datum mDriver = driverList.get(position);
        final ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_alert_all, null);
            mHolder.mUserSelected = (CheckedTextView) convertView.findViewById(R.id.AAA_CTV_user_lists);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mUserSelected.setText(mDriver.getFirst_name() + " " + mDriver.getLast_name());

        mHolder.mUserSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHolder.mUserSelected.isChecked()) {
                    for (String email: mselectedEmails){
                        if (email.equalsIgnoreCase(mDriver.getEmail())){
                            mselectedEmails.remove(position);
                        }
                    }

                    for (String id: mselectedIds){
                        if (id.equalsIgnoreCase(mDriver.getId())){
                            mselectedIds.remove(position);
                        }
                    }
                    Constants.ALERTSHEET.templateID(mselectedIds, mselectedEmails);
                    Log.i("Checked Email ----> ", mselectedEmails.size() + "\n" + " Checked id ----->" + mselectedIds.size());
                    mHolder.mUserSelected.setChecked(false);
                } else {
                    Toast.makeText(mContext, mDriver.getFirst_name(), Toast.LENGTH_SHORT).show();
                    mselectedEmails.add(mDriver.getEmail());
                    mselectedIds.add(mDriver.getId());
                    Log.i("Checked Email ----> ", mselectedEmails.size() + "\n" + " Checked id ----->" + mselectedIds.size());
                    Constants.ALERTSHEET.templateID(mselectedIds, mselectedEmails);
                    mHolder.mUserSelected.setChecked(true);
                }
            }
        });


        return convertView;
    }


    class ViewHolder {
        CheckedTextView mUserSelected;
    }

}
