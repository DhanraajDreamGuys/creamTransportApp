package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.model.RepairSheetData;
import co.in.dreamguys.cream.utils.Constants;

import static co.in.dreamguys.cream.utils.Util.showDeleteAlert;


/**
 * Created by user5 on 15-02-2017.
 */

public class RepairsheetAdapter extends BaseAdapter {

    private Context mContext;
    private List<RepairSheetData> data;
    private LayoutInflater mInflater;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public RepairsheetAdapter(Context mContext, List<RepairSheetData> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RepairSheetData getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_repair_sheet, null);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.ARS_TV_date);
            mHolder.mName = (TextView) convertView.findViewById(R.id.ARS_TV_name);
            mHolder.mEmail = (TextView) convertView.findViewById(R.id.ARS_TV_email);
            mHolder.mTrollNo = (TextView) convertView.findViewById(R.id.ARS_TV_troll_no);
            mHolder.mTruckNo = (TextView) convertView.findViewById(R.id.ARS_TV_truck_no);
            mHolder.mDollyNo = (TextView) convertView.findViewById(R.id.ARS_TV_dolly_no);
            mHolder.mView = (TextView) convertView.findViewById(R.id.ARS_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.ARS_TV_edit);
            mHolder.mDelete = (TextView) convertView.findViewById(R.id.ARS_TV_delete);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        RepairSheetData mRepairSheetData = data.get(position);
        mHolder.mName.setText(mRepairSheetData.getFirst_name() + " " + mRepairSheetData.getLast_name());
        mHolder.mEmail.setText(mRepairSheetData.getEmail());
        mHolder.mDate.setText(mRepairSheetData.getRdate());
        mHolder.mTruckNo.setText(mRepairSheetData.getTruck_no());
        mHolder.mTrollNo.setText(mRepairSheetData.getTrl_no());
        mHolder.mDollyNo.setText(mRepairSheetData.getDolly_no());


        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RepairSheet) mContext).viewRepairSheet(data.get(position).getRid(), 1); // Mode 0 means edit and 1 means view
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RepairSheet) mContext).viewRepairSheet(data.get(position).getRid(), 0); // Mode 0 means edit and 1 means view
            }
        });

        mHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlert(mContext, data.get(position).getRid(), position, Constants.REPAIR);

            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mName, mEmail, mDate, mDollyNo, mTrollNo, mTruckNo, mView, mEdit, mDelete;
    }
}
