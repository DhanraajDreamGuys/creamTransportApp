package co.in.dreamguys.cream.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.ViewUsers;
import co.in.dreamguys.cream.apis.UserTypeAPI;
import co.in.dreamguys.cream.model.UsersModel;
import co.in.dreamguys.cream.utils.Constants;

import static co.in.dreamguys.cream.utils.Util.showDeleteAlert;


/**
 * Created by user5 on 22-02-2017.
 */
public class UsersAdapter extends BaseAdapter {
    private Context mContext;
    private List<UsersModel> data;
    private LayoutInflater mInflater;

    public UsersAdapter(Context mContext, List<UsersModel> data) {
        this.mContext = mContext;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public UsersModel getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.adapter_users, null);
            mHolder.mStatus = (ImageView) convertView.findViewById(R.id.AU_IV_status);
            mHolder.mName = (TextView) convertView.findViewById(R.id.AU_TV_name);
            mHolder.mEmail = (TextView) convertView.findViewById(R.id.AU_TV_email);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.AU_TV_date);
            mHolder.mUserType = (TextView) convertView.findViewById(R.id.AU_TV_user_type);
            mHolder.mView = (TextView) convertView.findViewById(R.id.AU_TV_view);
            mHolder.mEdit = (TextView) convertView.findViewById(R.id.AU_TV_edit);
            mHolder.mDelete = (TextView) convertView.findViewById(R.id.AU_TV_delete);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        final UsersModel mUsersModel = data.get(position);
        mHolder.mName.setText(mUsersModel.getFirst_name() + " " + mUsersModel.getLast_name());
        mHolder.mEmail.setText(mUsersModel.getEmail());
        SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d1 = null;
        try {
            d1 = dateFormatOfStringInDB.parse(mUsersModel.getDate_created());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("dd/MM/yyyy");
        String sCertDate = dateFormatYouWant.format(d1);
        mHolder.mDate.setText(sCertDate);


        for (UserTypeAPI.Datum usertype: Constants.usertype){
            if (usertype.getId().equalsIgnoreCase(mUsersModel.getUser_type())){
                mHolder.mUserType.setText(usertype.getName());
            }
        }

        if (mUsersModel.getStatus().equalsIgnoreCase("0")) {
            mHolder.mStatus.setImageResource(R.drawable.ic_user_online);
        } else {
            mHolder.mStatus.setImageResource(R.drawable.ic_user_offline);
        }

        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewUsers = new Intent(mContext, ViewUsers.class);
                mCallViewUsers.putExtra(Constants.USERS_DATA, (Serializable) mUsersModel);
                mCallViewUsers.putExtra(Constants.VIEW_TYPE, 1);
                mContext.startActivity(mCallViewUsers);
            }
        });

        mHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCallViewUsers = new Intent(mContext, ViewUsers.class);
                mCallViewUsers.putExtra(Constants.USERS_DATA, (Serializable) mUsersModel);
                mCallViewUsers.putExtra(Constants.VIEW_TYPE, 0);
                mContext.startActivity(mCallViewUsers);
            }
        });

        mHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlert(mContext, data.get(position).getId(), position ,Constants.USERS);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mName, mEmail, mDate, mUserType, mView, mEdit, mDelete;
        ImageView mStatus;
    }
}
