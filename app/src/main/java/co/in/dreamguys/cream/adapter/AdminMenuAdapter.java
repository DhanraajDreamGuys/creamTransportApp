package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import co.in.dreamguys.cream.Accidentreport;
import co.in.dreamguys.cream.Dashboard;
import co.in.dreamguys.cream.EngineCodec;
import co.in.dreamguys.cream.FridgeCodec;
import co.in.dreamguys.cream.Fuelsheet;
import co.in.dreamguys.cream.Leave;
import co.in.dreamguys.cream.Paysheet;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.Settings;
import co.in.dreamguys.cream.Trips;
import co.in.dreamguys.cream.Users;
import co.in.dreamguys.cream.model.ExpandedMenuModel;
import co.in.dreamguys.cream.utils.ActivityConstants;


/**
 * Created by user5 on 10-02-2017.
 */
public class AdminMenuAdapter extends RecyclerView.Adapter<AdminMenuAdapter.AdminViewHolder> {
    List<ExpandedMenuModel> listDataHeader;
    Context mContext;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;

    public AdminMenuAdapter(Context mContext, List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<String>> listDataChild) {
        this.mContext = mContext;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public AdminMenuAdapter.AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menus, parent, false);
            return new AdminViewHolder(view, viewType);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menus_dropdown, parent, false);
            return new AdminViewHolder(view, viewType);
        }
    }

    @Override
    public void onBindViewHolder(AdminMenuAdapter.AdminViewHolder holder, int position) {
        int viewtype = holder.getItemViewType();
        if (viewtype == 0) {
            holder.mMenus.setText(listDataHeader.get(position).getIconName());
        } else {
            holder.mMenusDrop.setText(listDataHeader.get(position).getIconName());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (listDataHeader.get(position).getGroupPos()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return listDataHeader.size();
    }

    class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mMenus, mMenusDrop;

        public AdminViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == 0) {
                mMenus = (TextView) itemView.findViewById(R.id.tv_menus);
                mMenus.setOnClickListener(this);
            } else {
                mMenusDrop = (TextView) itemView.findViewById(R.id.tv_menus_drop);
                mMenusDrop.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            switch (getPosition()) {
                case 0:
                    ActivityConstants.callPage(mContext, Dashboard.class);
                    break;
                case 2:
                    ActivityConstants.callPage(mContext, Users.class);
                    break;
                case 3:
                    ActivityConstants.callPage(mContext, Trips.class);
                    break;
                case 8:
                    ActivityConstants.callPage(mContext, Paysheet.class);
                    break;
                case 10:
                    ActivityConstants.callPage(mContext, RepairSheet.class);
                    break;
                case 12:
                    ActivityConstants.callPage(mContext, Leave.class);
                    break;
                case 13:
                    ActivityConstants.callPage(mContext, Accidentreport.class);
                    break;
                case 14:
                    ActivityConstants.callPage(mContext, FridgeCodec.class);
                    break;
                case 15:
                    ActivityConstants.callPage(mContext, EngineCodec.class);
                    break;
                case 16:
                    ActivityConstants.callPage(mContext, Fuelsheet.class);
                    break;
                case 19:
                    ActivityConstants.callPage(mContext, Settings.class);
                    break;
            }
        }
    }

    private class openSubItems implements View.OnClickListener {
        int position;
        HashMap<ExpandedMenuModel, List<String>> listDataChild;

        public openSubItems(HashMap<ExpandedMenuModel, List<String>> listDataChild, int position) {
            this.position = position;
            this.listDataChild = listDataChild;
        }

        @Override
        public void onClick(View v) {
            ArrayList<String> msubitems;
            if (listDataHeader.get(position).getGroupPos()) {
                msubitems = getSubMenu(listDataChild, listDataHeader.get(position));
                if (msubitems != null) {
                    if (msubitems.size() > 0) {
                        AlertDialog mAlertDialog;
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                        View subView = LayoutInflater.from(mContext).inflate(R.layout.dialog_sub_items_list, null);
                        mBuilder.setCancelable(true);
                        mBuilder.setView(subView);
                        ListView msubItemsList = (ListView) subView.findViewById(R.id.DSIL_LV_sub_lists);
                        mAlertDialog = mBuilder.create();
                        SubItemAdapter aSubItemAdapter = new SubItemAdapter(mContext, msubitems, mAlertDialog);
                        msubItemsList.setAdapter(aSubItemAdapter);
                        mAlertDialog.show();
                    } else {
                        Toast.makeText(mContext, "No sub menu...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private ArrayList<String> getSubMenu(HashMap<ExpandedMenuModel, List<String>> listDataChild, ExpandedMenuModel expandedMenuModel) {
        String[] marray;
        ArrayList<String> mSubMn = new ArrayList<String>();
        ExpandedMenuModel modelClass;
        Iterator it = listDataChild.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            marray = pair.getValue().toString().split(",");
            modelClass = (ExpandedMenuModel) pair.getKey();
            if (expandedMenuModel.getIconName().equalsIgnoreCase(modelClass.getIconName()))
                for (int i = 0; i < marray.length; i++) {
                    mSubMn.add(marray[i]);
                }
//            it.remove(); // avoids a ConcurrentModificationException
        }
        return mSubMn;
    }

    private class SubItemAdapter extends BaseAdapter {
        Context mContext;
        List<String> subItems;
        AlertDialog mAlertDialog;
        LayoutInflater mInflater;

        public SubItemAdapter(Context mContext, List<String> subItems, AlertDialog mAlertDialog) {
            this.mContext = mContext;
            this.subItems = subItems;
            this.mAlertDialog = mAlertDialog;

            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return subItems.size();
        }

        @Override
        public String getItem(int position) {
            return subItems.get(position);
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
                convertView = mInflater.inflate(R.layout.adapter_submenu, null);
                mHolder.msubitem = (TextView) convertView.findViewById(R.id.tv_sub_menus);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            mHolder.msubitem.setText(subItems.get(position));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView msubitem;
        }
    }
}
