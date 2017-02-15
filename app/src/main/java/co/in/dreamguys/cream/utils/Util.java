package co.in.dreamguys.cream.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.adapter.DriverListAdapter;

/**
 * Created by user5 on 13-02-2017.
 */

public class Util {


    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public static boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static void searchPopUpWindow(final Context mContext, final PopupWindow popupSearch, final LayoutInflater layoutInflater) {
        popupSearch.setOutsideTouchable(false);
        View searchView = layoutInflater.inflate(R.layout.include_search, null);
        popupSearch.setContentView(searchView);
        popupSearch.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));
        popupSearch.setWindowLayoutMode(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupSearch.setHeight(1);
        popupSearch.setWidth(1);
        Button fireSearch = (Button) searchView.findViewById(R.id.IS_BT_search);
        Button firecancel = (Button) searchView.findViewById(R.id.IS_BT_cancel);
        final TextView fireDriverlist = (TextView) searchView.findViewById(R.id.IS_TV_choose_drive);
        final TextView mFromDate = (TextView) searchView.findViewById(R.id.IS_TV_date_from);
        final TextView mFromTo = (TextView) searchView.findViewById(R.id.IS_TV_date_to);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar newCalendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
        newCalendar.setTimeZone(timeZone);

        mFromDate.setText(dateFormatter.format(newCalendar.getTime()));
        mFromTo.setText(dateFormatter.format(newCalendar.getTime()));
        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                Calendar newCalendar = new GregorianCalendar();
                TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
                newCalendar.setTimeZone(timeZone);
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        mFromDate.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.show();
            }
        });

        mFromTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                Calendar newCalendar = new GregorianCalendar();
                TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
                newCalendar.setTimeZone(timeZone);
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        mFromTo.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.show();
            }
        });


        fireDriverlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View driverlayout = layoutInflater.inflate(R.layout.dialog_sub_items_list, null);
                builder.setView(driverlayout);

                ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DSIL_LV_sub_lists);


                DriverListAdapter aDriverListAdapter = new DriverListAdapter(mContext, Constants.driverList);
                mDriverViews.setAdapter(aDriverListAdapter);
                final AlertDialog alert = builder.create();
                alert.show();

                mDriverViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        fireDriverlist.setText(Constants.driverList.get(position).getFirst_name() + " " + Constants.driverList.get(position).getLast_name());
                        alert.dismiss();
                    }
                });
            }
        });


        fireSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        firecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSearch.dismiss();
            }
        });

        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
            popupSearch.showAtLocation(searchView, Gravity.TOP, 0, actionBarHeight + 60);
        }


    }


}
