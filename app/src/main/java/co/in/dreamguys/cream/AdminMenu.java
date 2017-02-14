package co.in.dreamguys.cream;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.in.dreamguys.cream.adapter.AdminMenuAdapter;
import co.in.dreamguys.cream.model.ExpandedMenuModel;
import co.in.dreamguys.cream.utils.ActivityConstants;


/**
 * Created by user5 on 10-02-2017.
 */

public class AdminMenu extends AppCompatActivity {
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    RecyclerView mMenus;
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        intiWidgets();

        prepareListData();
        mMenus = (RecyclerView) findViewById(R.id.rv_admin_menu);
        mMenus.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mMenus.setHasFixedSize(true);

        AdminMenuAdapter aAdminMenuAdapter = new AdminMenuAdapter(AdminMenu.this, listDataHeader, listDataChild);
        mMenus.setAdapter(aAdminMenuAdapter);

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
        listDataHeader.add(item8);

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

        }

        return super.onOptionsItemSelected(item);
    }
}
