package co.in.dreamguys.cream.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import co.in.dreamguys.cream.Paysheet;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.adapter.DriverListAdapter;
import co.in.dreamguys.cream.adapter.PaysheetWeeklyAdapter;
import co.in.dreamguys.cream.adapter.RepairsheetAdapter;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.PaysheetLastWeekAPI;
import co.in.dreamguys.cream.apis.RepairsheetCurrentDayAPI;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.model.PaysheetReport;
import co.in.dreamguys.cream.model.RepairSheetData;
import co.in.dreamguys.cream.model.RepairSheetReport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user5 on 13-02-2017.
 */

public class Util {

    static CustomProgressDialog mCustomProgressDialog;
    static int adapterPosition;

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public static boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


    public static HashMap<String, String> sendValueWithRetrofit(TextView mFromDate, TextView mFromTo) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.USER_ID, Constants.driverList.get(adapterPosition).getId());
        params.put(Constants.PARAMS_START_DATE, mFromDate.getText().toString());
        params.put(Constants.PARAMS_END_DATE, mFromTo.getText().toString());
        return params;
    }


    public static void searchPopUpWindow(final Context mContext, final PopupWindow popupSearch, final String PAGE, final LayoutInflater layoutInflater, final ListView mPaysheetView) {
        mCustomProgressDialog = new CustomProgressDialog(mContext);
        popupSearch.setAnimationStyle(android.R.style.Animation_Dialog);
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
        final Button firecancel = (Button) searchView.findViewById(R.id.IS_BT_cancel);
        final TextView fireDriverlist = (TextView) searchView.findViewById(R.id.IS_TV_choose_drive);
        final TextView mFromDate = (TextView) searchView.findViewById(R.id.IS_TV_date_from);
        final TextView mFromTo = (TextView) searchView.findViewById(R.id.IS_TV_date_to);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar newCalendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
        newCalendar.setTimeZone(timeZone);

        mFromDate.setText(dateFormatter.format(newCalendar.getTime()));
        mFromTo.setText(dateFormatter.format(newCalendar.getTime()));
        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
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
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
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
                        adapterPosition = position;
                        alert.dismiss();
                    }
                });
            }
        });


        fireSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fireDriverlist.getText().toString().isEmpty()) {
                    fireDriverlist.setError(mContext.getString(R.string.err_driver_name));
                    fireDriverlist.requestFocus();
                } else if (mFromDate.getText().toString().isEmpty()) {
                    mFromDate.setError(mContext.getString(R.string.err_start_date));
                    mFromDate.requestFocus();
                } else if (mFromTo.getText().toString().isEmpty()) {
                    mFromTo.setError(mContext.getString(R.string.err_end_date));
                    mFromTo.requestFocus();
                } else if (!Util.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else {
                    mCustomProgressDialog.showDialog();
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    switch (PAGE) {
                        case "PAYSHEET":
                            Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> loginCall = apiService.getSearchPaysheetReport(sendValueWithRetrofit(mFromDate, mFromTo));
                            loginCall.enqueue(new Callback<PaysheetLastWeekAPI.PaysheetLastWeekResponse>() {
                                @Override
                                public void onResponse(Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> call, Response<PaysheetLastWeekAPI.PaysheetLastWeekResponse> response) {
                                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                                        filldataDetails(mContext, response.body().getData(), mPaysheetView);
                                    } else {
                                        mPaysheetView.setAdapter(null);
                                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mCustomProgressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> call, Throwable t) {
                                    Log.i(((Paysheet) mContext).getPackageName(), t.getMessage());
                                    mCustomProgressDialog.dismiss();
                                }
                            });

                            break;

                        case "REPAIR":
                            Call<RepairsheetCurrentDayAPI.RepairsheetResponse> repairsheet = apiService.getSearchRepairsheetReport(sendValueWithRetrofit(mFromDate, mFromTo));
                            repairsheet.enqueue(new Callback<RepairsheetCurrentDayAPI.RepairsheetResponse>() {
                                @Override
                                public void onResponse(Call<RepairsheetCurrentDayAPI.RepairsheetResponse> call, Response<RepairsheetCurrentDayAPI.RepairsheetResponse> response) {
                                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                                        fillRepairSheetData(mContext, response.body().getData(), mPaysheetView);
                                    } else {
                                        mPaysheetView.setAdapter(null);
                                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mCustomProgressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<RepairsheetCurrentDayAPI.RepairsheetResponse> call, Throwable t) {
                                    Log.i(((RepairSheet) mContext).getPackageName(), t.getMessage());
                                    mCustomProgressDialog.dismiss();
                                }
                            });

                            break;

                    }


                }


            }
        });

        firecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (PAGE) {
                    case "PAYSHEET":
                        ((Paysheet) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;
                    case "REPAIR":
                        ((RepairSheet) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;
                }


            }
        });

        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
            popupSearch.showAtLocation(searchView, Gravity.TOP, 0, actionBarHeight + 60);
        }


    }

    private static void listAdjustableMethod(PopupWindow popupSearch, ListView mPaysheetView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT
        );
        popupSearch.dismiss();
        layoutParams.setMargins(0, 0, 0, 0);
        mPaysheetView.setLayoutParams(layoutParams);
    }

    private static void filldataDetails(Context mContext, List<PaysheetLastWeekAPI.Datum> data, ListView mPaysheetView) {
        Data mData;
        PaysheetReport mPaysheetReport = new PaysheetReport();
        List<Data> mPaysheetData;
        mPaysheetData = new ArrayList<Data>();
        for (int i = 0; i < data.size(); i++) {
            PaysheetLastWeekAPI.Datum model = data.get(i);
            mData = new Data();
            mData.setComment(model.getComment());
            mData.setDolly_no(model.getDolly_no());
            mData.setDuty(model.getDuty());
            mData.setEmail(model.getEmail());
            mData.setEnd_km(model.getEnd_km());
            mData.setFirst_name(model.getFirst_name());
            mData.setFrom(model.getFrom());
            mData.setInspection(model.getInspection());
            mData.setMf_no(model.getMf_no());
            mData.setLast_name(model.getLast_name());
            mData.setOffice_use(model.getOffice_use());
            mData.setPdate(model.getPdate());
            mData.setPid(model.getPid());
            mData.setRt_bd(model.getRt_bd());
            mData.setStart_km(model.getStart_km());
            mData.setTo(model.getTo());
            mData.setTr1_no(model.getTr1_no());
            mData.setTr2_no(model.getTr2_no());
            mData.setTr3_no(model.getTr3_no());
            mData.setTruck_no(model.getTruck_no());
            mData.setUid(model.getUid());
            mData.setUnloading_time(model.getUnloading_time());
            mPaysheetData.add(mData);
            mPaysheetReport.setData(mPaysheetData);
        }

        if (mPaysheetReport.getData().size() > 0) {
            mPaysheetView.setAdapter(null);
            PaysheetWeeklyAdapter aPaysheetWeeklyAdapter = new PaysheetWeeklyAdapter(mContext, mPaysheetReport.getData());
            mPaysheetView.setAdapter(aPaysheetWeeklyAdapter);
            aPaysheetWeeklyAdapter.notifyDataSetChanged();
        } else {
            mPaysheetView.setAdapter(null);
            Toast.makeText(mContext, mContext.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
        }

    }


    private static void fillRepairSheetData(Context mContext, List<RepairsheetCurrentDayAPI.Datum> data, ListView mPaysheetView) {
        RepairsheetAdapter aRepairsheetAdapter;
        RepairSheetData mRepairSheetData;
        RepairSheetReport mRepairSheetReport = new RepairSheetReport();
        List<RepairSheetData> mRepairSheetDataList;

        mRepairSheetDataList = new ArrayList<RepairSheetData>();

        for (int i = 0; i < data.size(); i++) {
            RepairsheetCurrentDayAPI.Datum mData = data.get(i);
            mRepairSheetData = new RepairSheetData();
            mRepairSheetData.setUid(mData.getUid());
            mRepairSheetData.setComments(mData.getComments());
            mRepairSheetData.setTruck_no(mData.getTruck_no());
            mRepairSheetData.setDolly_no(mData.getDolly_no());
            mRepairSheetData.setEmail(mData.getEmail());
            mRepairSheetData.setFaults(mData.getFaults());
            mRepairSheetData.setFirst_name(mData.getFirst_name());
            mRepairSheetData.setLast_name(mData.getLast_name());
            mRepairSheetData.setRdate(mData.getRdate());
            mRepairSheetData.setImage_one(mData.getImage_one());
            mRepairSheetData.setImage_two(mData.getImage_two());
            mRepairSheetData.setRegn1_no(mData.getRegn1_no());
            mRepairSheetData.setRegn_no(mData.getRegn_no());
            mRepairSheetData.setRid(mData.getRid());
            mRepairSheetDataList.add(mRepairSheetData);
            mRepairSheetReport.setData(mRepairSheetDataList);
        }

        if (mRepairSheetReport.getData().size() > 0) {
            mPaysheetView.setAdapter(null);
            aRepairsheetAdapter = new RepairsheetAdapter(mContext, mRepairSheetReport.getData());
            mPaysheetView.setAdapter(aRepairsheetAdapter);
            aRepairsheetAdapter.notifyDataSetChanged();
        } else {
            mPaysheetView.setAdapter(null);
            Toast.makeText(mContext, mContext.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
