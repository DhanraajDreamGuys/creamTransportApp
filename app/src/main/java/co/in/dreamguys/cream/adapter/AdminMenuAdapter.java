package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.in.dreamguys.cream.Accidentreport;
import co.in.dreamguys.cream.Alerts;
import co.in.dreamguys.cream.Company;
import co.in.dreamguys.cream.Dashboard;
import co.in.dreamguys.cream.DriversLog;
import co.in.dreamguys.cream.EngineCodec;
import co.in.dreamguys.cream.FridgeCodec;
import co.in.dreamguys.cream.Fuelsheet;
import co.in.dreamguys.cream.Leave;
import co.in.dreamguys.cream.MLI;
import co.in.dreamguys.cream.Paysheet;
import co.in.dreamguys.cream.PhoneBook;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.Runsheet;
import co.in.dreamguys.cream.Settings;
import co.in.dreamguys.cream.Staffreport;
import co.in.dreamguys.cream.TripHours;
import co.in.dreamguys.cream.Trips;
import co.in.dreamguys.cream.Tyrerepair;
import co.in.dreamguys.cream.Users;
import co.in.dreamguys.cream.model.ExpandedMenuModel;
import co.in.dreamguys.cream.utils.ActivityConstants;


/**
 * Created by user5 on 10-02-2017.
 */
public class AdminMenuAdapter extends RecyclerView.Adapter<AdminMenuAdapter.AdminViewHolder> {
    private List<ExpandedMenuModel> listDataHeader;
    Context mContext;


    public AdminMenuAdapter(Context mContext, List<ExpandedMenuModel> listDataHeader) {
        this.mContext = mContext;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public AdminMenuAdapter.AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menus, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminMenuAdapter.AdminViewHolder holder, int position) {
        holder.mMenus.setText(listDataHeader.get(position).getIconName());
        holder.mMenuIcons.setImageResource(listDataHeader.get(position).getIconImg());
    }

    @Override
    public int getItemCount() {
        return listDataHeader.size();
    }

    class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mMenus;
        ImageView mMenuIcons;

        AdminViewHolder(View itemView) {
            super(itemView);
            mMenus = (TextView) itemView.findViewById(R.id.tv_menus);
            mMenuIcons = (ImageView) itemView.findViewById(R.id.AM_IV_menu_icon);
            mMenus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (getPosition()) {
                case 0:
                    ActivityConstants.callPage(mContext, Dashboard.class);
                    break;
                case 1:
                    ActivityConstants.callPage(mContext, Company.class);
                    break;
                case 2:
                    ActivityConstants.callPage(mContext, Users.class);
                    break;
                case 3:
                    ActivityConstants.callPage(mContext, Trips.class);
                    break;
                case 4:
                    ActivityConstants.callPage(mContext, Runsheet.class);
                    break;
                case 5:
                    ActivityConstants.callPage(mContext, Alerts.class);
                    break;
                case 6:
                    ActivityConstants.callPage(mContext, Tyrerepair.class);
                    break;
                case 7:
                    ActivityConstants.callPage(mContext, DriversLog.class);
                    break;
                case 8:
                    ActivityConstants.callPage(mContext, Paysheet.class);
                    break;
                case 9:
                    ActivityConstants.callPage(mContext, MLI.class);
                    break;
                case 10:
                    ActivityConstants.callPage(mContext, RepairSheet.class);
                    break;
                case 11:
                    ActivityConstants.callPage(mContext, Staffreport.class);
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
                case 17:
                    ActivityConstants.callPage(mContext, PhoneBook.class);
                    break;
                case 18:
                    ActivityConstants.callPage(mContext, TripHours.class);
                    break;
                case 19:
                    ActivityConstants.callPage(mContext, Settings.class);
                    break;
            }
        }
    }
}
