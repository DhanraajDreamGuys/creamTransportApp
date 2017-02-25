package co.in.dreamguys.cream;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import co.in.dreamguys.cream.adapter.AdminMenuAdapter;
import co.in.dreamguys.cream.adapter.BranchListAdapter;
import co.in.dreamguys.cream.adapter.DriverListAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.DriverListsAPI;
import co.in.dreamguys.cream.interfaces.ChooseDateListener;
import co.in.dreamguys.cream.interfaces.ConstantListItem;
import co.in.dreamguys.cream.interfaces.LoactionInterface;
import co.in.dreamguys.cream.model.ExpandedMenuModel;
import co.in.dreamguys.cream.utils.ActivityConstants;
import co.in.dreamguys.cream.utils.Constants;
import co.in.dreamguys.cream.utils.CustomProgressDialog;
import co.in.dreamguys.cream.utils.SessionHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Constants.USER_ID;
import static co.in.dreamguys.cream.utils.Constants.countries;
import static co.in.dreamguys.cream.utils.Constants.driverList;
import static co.in.dreamguys.cream.utils.Util.isNetworkAvailable;


/**
 * Created by user5 on 10-02-2017.
 */

public class AdminMenu extends AppCompatActivity implements ConstantListItem, LoactionInterface, ChooseDateListener {
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    RecyclerView mMenus;
    Toolbar mToolbar;
    CustomProgressDialog mCustomProgressDialog;
    public static String TAG = AdminMenu.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        Constants.AdminMenu = AdminMenu.this;

        mCustomProgressDialog = new CustomProgressDialog(AdminMenu.this);
        intiWidgets();
        getDriverLists();

        prepareListData();
        mMenus = (RecyclerView) findViewById(R.id.rv_admin_menu);
        mMenus.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mMenus.setHasFixedSize(true);

        AdminMenuAdapter aAdminMenuAdapter = new AdminMenuAdapter(AdminMenu.this, listDataHeader, listDataChild);
        mMenus.setAdapter(aAdminMenuAdapter);

    }


    private void getDriverLists() {

        if (!isNetworkAvailable(this)) {
            Toast.makeText(AdminMenu.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mCustomProgressDialog.showDialog();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<DriverListsAPI.DriverResponse> loginCall = apiService.getDriverLists();

            loginCall.enqueue(new Callback<DriverListsAPI.DriverResponse>() {
                @Override
                public void onResponse(Call<DriverListsAPI.DriverResponse> call, Response<DriverListsAPI.DriverResponse> response) {
                    mCustomProgressDialog.dismiss();
                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                        driverList = response.body().getData();
                    } else {
                        Toast.makeText(AdminMenu.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DriverListsAPI.DriverResponse> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    mCustomProgressDialog.dismiss();
                }
            });

        }
    }

    private void intiWidgets() {
        mToolbar = (Toolbar) findViewById(R.id.ATTB_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_menu_overflow));
        mToolbar.setTitle(getString(R.string.admin_menu_title));
        mToolbar.setTitleTextColor(Color.WHITE);
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Dashboard");
        item1.setIconImg(android.R.drawable.ic_menu_camera);
        item1.setGroupPos(false);
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Company");
        item2.setIconImg(android.R.drawable.ic_menu_camera);
        item2.setGroupPos(false);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Users");
        item3.setIconImg(android.R.drawable.ic_menu_camera);
        item3.setGroupPos(false);
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Trips");
        item4.setIconImg(android.R.drawable.ic_menu_camera);
        item4.setGroupPos(true);
        listDataHeader.add(item4);

        ExpandedMenuModel item5 = new ExpandedMenuModel();
        item5.setIconName("Run sheets");
        item5.setIconImg(android.R.drawable.ic_menu_camera);
        item5.setGroupPos(true);
        listDataHeader.add(item5);

        ExpandedMenuModel item6 = new ExpandedMenuModel();
        item6.setIconName("Alerts");
        item6.setIconImg(android.R.drawable.ic_menu_camera);
        item6.setGroupPos(true);
        listDataHeader.add(item6);

        ExpandedMenuModel item7 = new ExpandedMenuModel();
        item7.setIconName("Tyre repair");
        item7.setIconImg(android.R.drawable.ic_menu_camera);
        item7.setGroupPos(true);
        listDataHeader.add(item7);

        ExpandedMenuModel item8 = new ExpandedMenuModel();
        item8.setIconName("Driver hours");
        item8.setIconImg(android.R.drawable.ic_menu_camera);
        item8.setGroupPos(true);
        listDataHeader.add(item8);

        ExpandedMenuModel item9 = new ExpandedMenuModel();
        item9.setIconName("Paysheet");
        item9.setIconImg(android.R.drawable.ic_menu_camera);
        item9.setGroupPos(true);
        listDataHeader.add(item9);

        ExpandedMenuModel item10 = new ExpandedMenuModel();
        item10.setIconName("MLIS");
        item10.setIconImg(android.R.drawable.ic_menu_camera);
        item10.setGroupPos(true);
        listDataHeader.add(item10);

        ExpandedMenuModel item11 = new ExpandedMenuModel();
        item11.setIconName("Repair sheets");
        item11.setIconImg(android.R.drawable.ic_menu_camera);
        item11.setGroupPos(false);
        listDataHeader.add(item11);

        ExpandedMenuModel item12 = new ExpandedMenuModel();
        item12.setIconName("Staff report");
        item12.setIconImg(android.R.drawable.ic_menu_camera);
        item12.setGroupPos(false);
        listDataHeader.add(item12);

        ExpandedMenuModel item13 = new ExpandedMenuModel();
        item13.setIconName("Leave form");
        item13.setIconImg(android.R.drawable.ic_menu_camera);
        item13.setGroupPos(false);
        listDataHeader.add(item13);

        ExpandedMenuModel item14 = new ExpandedMenuModel();
        item14.setIconName("Accident report");
        item14.setIconImg(android.R.drawable.ic_menu_camera);
        item14.setGroupPos(false);
        listDataHeader.add(item14);

        ExpandedMenuModel item15 = new ExpandedMenuModel();
        item15.setIconName("Fridge codes");
        item15.setIconImg(android.R.drawable.ic_menu_camera);
        item15.setGroupPos(true);
        listDataHeader.add(item15);

        ExpandedMenuModel item16 = new ExpandedMenuModel();
        item16.setIconName("Engine code");
        item16.setIconImg(android.R.drawable.ic_menu_camera);
        item16.setGroupPos(true);
        listDataHeader.add(item16);

        ExpandedMenuModel item17 = new ExpandedMenuModel();
        item17.setIconName("Fuelsheet");
        item17.setIconImg(android.R.drawable.ic_menu_camera);
        item17.setGroupPos(true);
        listDataHeader.add(item17);

        ExpandedMenuModel item18 = new ExpandedMenuModel();
        item18.setIconName("Phone book");
        item18.setIconImg(android.R.drawable.ic_menu_camera);
        item18.setGroupPos(true);
        listDataHeader.add(item18);

        ExpandedMenuModel item19 = new ExpandedMenuModel();
        item19.setIconName("Trip hours");
        item19.setIconImg(android.R.drawable.ic_menu_camera);
        item19.setGroupPos(true);
        listDataHeader.add(item19);

        ExpandedMenuModel item20 = new ExpandedMenuModel();
        item20.setIconName("Settings");
        item20.setIconImg(android.R.drawable.ic_menu_camera);
        item20.setGroupPos(true);
        listDataHeader.add(item20);
        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Submenu of item 1");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Submenu of item 1");
        heading2.add("Submenu of item 2");
        heading2.add("Submenu of item 3");

        listDataChild.put(listDataHeader.get(3), heading1); // Header, Child data
        listDataChild.put(listDataHeader.get(4), heading2);
        listDataChild.put(listDataHeader.get(6), heading1);
        listDataChild.put(listDataHeader.get(5), heading2);
        listDataChild.put(listDataHeader.get(7), heading1);
        listDataChild.put(listDataHeader.get(8), heading2);
        listDataChild.put(listDataHeader.get(9), heading1);
        listDataChild.put(listDataHeader.get(14), heading2);
        listDataChild.put(listDataHeader.get(15), heading1);
        listDataChild.put(listDataHeader.get(16), heading2);
        listDataChild.put(listDataHeader.get(17), heading1);
        listDataChild.put(listDataHeader.get(18), heading2);
        listDataChild.put(listDataHeader.get(19), heading1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_menus, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_account) {
            ActivityConstants.callPage(AdminMenu.this, Account.class);
        } else if (item.getItemId() == R.id.action_logout) {
            if (!SessionHandler.getStringPref(USER_ID).isEmpty()) {
                SessionHandler.clearPrefs();
                Intent mCallLogin = new Intent(this, Login.class);
                mCallLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mCallLogin);
                finish();
            }
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getDrivers(Context mContext, final TextView value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View driverlayout = getLayoutInflater().inflate(R.layout.dialog_sub_items_list, null);
        builder.setView(driverlayout);
        final AlertDialog alert = builder.create();
        ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DSIL_LV_sub_lists);
        final EditText mSearchWord = (EditText) driverlayout.findViewById(R.id.DSIL_ET_search_word);

        final DriverListAdapter aDriverListAdapter = new DriverListAdapter(mContext, driverList, alert, value);
        mDriverViews.setAdapter(aDriverListAdapter);
        mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aDriverListAdapter.setSearchEnabled(true, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        alert.show();


    }

    @Override
    public void getLocations(Context mContext, final TextView value, final String mFromorTo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View driverlayout = getLayoutInflater().inflate(R.layout.dialog_sub_items_list, null);
        builder.setView(driverlayout);

        ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DSIL_LV_sub_lists);
        final EditText mSearchWord = (EditText) driverlayout.findViewById(R.id.DSIL_ET_search_word);
        final AlertDialog alert = builder.create();

        final BranchListAdapter aDriverListAdapter = new BranchListAdapter(mContext, countries, value, alert, mFromorTo);
        mDriverViews.setAdapter(aDriverListAdapter);

        alert.show();

        mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aDriverListAdapter.setSearchEnabled(true, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void getFromDate(Context mContext, final TextView value) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar newCalendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
        newCalendar.setTimeZone(timeZone);
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                value.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.show();
        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }


}
