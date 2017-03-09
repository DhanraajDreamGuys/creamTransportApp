package co.in.dreamguys.cream.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import co.in.dreamguys.cream.Accidentreport;
import co.in.dreamguys.cream.EngineCodec;
import co.in.dreamguys.cream.FridgeCodec;
import co.in.dreamguys.cream.Fuelsheet;
import co.in.dreamguys.cream.Paysheet;
import co.in.dreamguys.cream.R;
import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.Trips;
import co.in.dreamguys.cream.Tyrerepair;
import co.in.dreamguys.cream.Users;
import co.in.dreamguys.cream.adapter.AccidentReportAdapter;
import co.in.dreamguys.cream.adapter.CountryListAdapter;
import co.in.dreamguys.cream.adapter.FuelSheetAdapter;
import co.in.dreamguys.cream.adapter.PaysheetWeeklyAdapter;
import co.in.dreamguys.cream.adapter.RepairsheetAdapter;
import co.in.dreamguys.cream.adapter.TripAdapter;
import co.in.dreamguys.cream.adapter.TypeListAdapter;
import co.in.dreamguys.cream.adapter.TyreRepairAdapter;
import co.in.dreamguys.cream.adapter.UserTypeListAdapter;
import co.in.dreamguys.cream.apis.AccidentReportAPI;
import co.in.dreamguys.cream.apis.ApiClient;
import co.in.dreamguys.cream.apis.ApiInterface;
import co.in.dreamguys.cream.apis.FuelsheetAPI;
import co.in.dreamguys.cream.apis.PaysheetLastWeekAPI;
import co.in.dreamguys.cream.apis.RepairsheetCurrentDayAPI;
import co.in.dreamguys.cream.apis.TripListAPI;
import co.in.dreamguys.cream.apis.TyreRepairAPI;
import co.in.dreamguys.cream.model.Data;
import co.in.dreamguys.cream.model.FuelSheetData;
import co.in.dreamguys.cream.model.FuelSheetModel;
import co.in.dreamguys.cream.model.PaysheetReport;
import co.in.dreamguys.cream.model.RepairSheetData;
import co.in.dreamguys.cream.model.RepairSheetReport;
import co.in.dreamguys.cream.model.TripList;
import co.in.dreamguys.cream.model.TripListReport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.in.dreamguys.cream.utils.Constants.usertype;

/**
 * Created by user5 on 13-02-2017.
 */

public class Util {

    private static CustomProgressDialog mCustomProgressDialog;
    public static int adapterPosition;
    public static TripListReport mTripReport = new TripListReport();
    public static FuelSheetData mFuelSheetData = new FuelSheetData();
    public static TripAdapter aTripAdapter;
    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;
    private static AccidentReportAdapter aAccidentReportAdapter;
    public static FuelSheetAdapter aFuelSheetAdapter;
    private static Map.Entry<String, String> type;
    private static TypeListAdapter aTypeListAdapter;
    private static TyreRepairAdapter aTyreRepairAdapter;
    private static Map<String, String> map;


    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public static boolean isNetworkAvailable(final Context context) {
        int conn = getConnectivityStatus(context);
        boolean status = false;
        if (conn == TYPE_WIFI) {
            status = true;
        } else if (conn == TYPE_MOBILE) {
            status = true;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = false;
        }
        return status;
    }

    private static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getDateFormat(String fromdate) {
        SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = dateFormatOfStringInDB.parse(fromdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormatYouWant.format(d1);
    }

    private static HashMap<String, String> sendValueWithRetrofit(TextView mFromDate, TextView mFromTo) {
        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.USER_ID, Constants.driverList.get(adapterPosition).getId());
        params.put(Constants.PARAMS_START_DATE, mFromDate.getText().toString());
        params.put(Constants.PARAMS_END_DATE, mFromTo.getText().toString());
        return params;
    }


    public static void searchPopupWindowUsers(final Context mContext, final PopupWindow popupSearch, final String PAGE, final LayoutInflater layoutInflater, final ListView mPaysheetView) {
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


    }

    public static void searchFridgeCodec(final Context mContext, final PopupWindow popupSearch, final String PAGE, final LayoutInflater layoutInflater, final ListView mPaysheetView) {
        mCustomProgressDialog = new CustomProgressDialog(mContext);
        popupSearch.setAnimationStyle(android.R.style.Animation_Dialog);
        popupSearch.setOutsideTouchable(false);
        View searchView = layoutInflater.inflate(R.layout.include_search_fridge_codec, null);
        popupSearch.setContentView(searchView);
        popupSearch.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));
        popupSearch.setWindowLayoutMode(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupSearch.setHeight(1);
        popupSearch.setWidth(1);
        Button fireSearch = (Button) searchView.findViewById(R.id.ISFC_BT_search);
        final Button firecancel = (Button) searchView.findViewById(R.id.ISFC_BT_cancel);
        final TextView fireType = (TextView) searchView.findViewById(R.id.ISFC_TV_choose_type);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View typeLayout = layoutInflater.inflate(R.layout.dialog_type_list, null);
        builder.setView(typeLayout);
        final AlertDialog alert = builder.create();
        final ListView mTypeWidgets = (ListView) typeLayout.findViewById(R.id.DTL_LV_type);


        fireType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (PAGE) {
                    case "FRIDGE CODEC":
                        map = new HashMap<String, String>();
                        map.put("thermal", mContext.getString(R.string.str_thermal));
                        map.put("carrier", mContext.getString(R.string.str_carrier));
                        aTypeListAdapter = new TypeListAdapter(mContext, map);
                        mTypeWidgets.setAdapter(aTypeListAdapter);

                        mTypeWidgets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                type = aTypeListAdapter.getItem(position);
                                fireType.setText(type.getValue());
                                alert.dismiss();
                            }
                        });

                        alert.show();
                        break;

                    case "ENGINE CODEC":
                        map = new HashMap<String, String>();
                        map.put("cummins", mContext.getString(R.string.str_cummins));
                        map.put("catapillar", mContext.getString(R.string.str_catapillar));
                        aTypeListAdapter = new TypeListAdapter(mContext, map);
                        mTypeWidgets.setAdapter(aTypeListAdapter);

                        mTypeWidgets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                type = aTypeListAdapter.getItem(position);
                                fireType.setText(type.getValue());
                                alert.dismiss();
                            }
                        });

                        alert.show();
                        break;
                }


            }
        });

        fireSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (PAGE) {
                    case "FRIDGE CODEC":
                        ((FridgeCodec) mContext).typeSearch(type.getKey());
                        break;
                    case "ENGINE CODEC":
                        ((EngineCodec) mContext).typeSearch(type.getKey());
                        break;
                }
            }
        });

        firecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (PAGE) {
                    case "FRIDGE CODEC":
                        mPaysheetView.setAdapter(null);
                        ((FridgeCodec) mContext).refresh();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;
                    case "ENGINE CODEC":
                        mPaysheetView.setAdapter(null);
                        ((EngineCodec) mContext).refresh();
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
                Constants.AdminMenu.getFromDate(mContext, mFromDate);
            }
        });

        mFromTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.AdminMenu.getFromDate(mContext, mFromTo);
            }
        });


        fireDriverlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.AdminMenu.getDrivers(mContext, fireDriverlist);
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
                } else if (!isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, mContext.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                } else {
                    mCustomProgressDialog.showDialog();
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    switch (PAGE) {
                        case "PAYSHEET":
                            ((Paysheet) mContext).printInstance(fireDriverlist, mFromDate, mFromTo);
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
                            ((RepairSheet) mContext).printInstance(fireDriverlist, mFromDate, mFromTo);
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

                        case "TRIP":
                            Call<TripListAPI.TripsResponse> tripcall = apiService.getTripLists(sendValueWithRetrofit(mFromDate, mFromTo));
                            tripcall.enqueue(new Callback<TripListAPI.TripsResponse>() {
                                @Override
                                public void onResponse(Call<TripListAPI.TripsResponse> call, Response<TripListAPI.TripsResponse> response) {
                                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                                        fillTripData(mContext, response.body().getData(), mPaysheetView);
                                    } else {
                                        mPaysheetView.setAdapter(null);
                                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mCustomProgressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<TripListAPI.TripsResponse> call, Throwable t) {
                                    mCustomProgressDialog.dismiss();
                                    Log.i(((Trips) mContext).getPackageName(), t.getMessage());
                                }
                            });

                            break;

                        case "ACCIDENT REPORT":
                            Call<AccidentReportAPI.AccidentReportResponse> accidentReportCall = apiService.getSearchAccidentResports(sendValueWithRetrofit(mFromDate, mFromTo));

                            accidentReportCall.enqueue(new Callback<AccidentReportAPI.AccidentReportResponse>() {
                                @Override
                                public void onResponse(Call<AccidentReportAPI.AccidentReportResponse> call, Response<AccidentReportAPI.AccidentReportResponse> response) {
                                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                                        mPaysheetView.setAdapter(null);
                                        aAccidentReportAdapter = new AccidentReportAdapter(mContext, response.body().getData());
                                        mPaysheetView.setAdapter(aAccidentReportAdapter);
                                        aAccidentReportAdapter.notifyDataSetChanged();
                                    } else {
                                        mPaysheetView.setAdapter(null);
                                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mCustomProgressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<AccidentReportAPI.AccidentReportResponse> call, Throwable t) {
                                    Log.i(((Accidentreport) mContext).getPackageName(), t.getMessage());
                                    mCustomProgressDialog.dismiss();
                                }
                            });
                            break;

                        case "FUELSHEET":
                            ((Fuelsheet) mContext).printInstance(fireDriverlist, mFromDate, mFromTo);
                            Call<FuelsheetAPI.FuelSheetListResponse> fuelSheetCall = apiService.getFuelsheetLists(sendValueWithRetrofit(mFromDate, mFromTo));

                            fuelSheetCall.enqueue(new Callback<FuelsheetAPI.FuelSheetListResponse>() {
                                @Override
                                public void onResponse(Call<FuelsheetAPI.FuelSheetListResponse> call, Response<FuelsheetAPI.FuelSheetListResponse> response) {
                                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                                        fillFuelSheetData(mContext, response.body().getData(), mPaysheetView);
                                    } else {
                                        mPaysheetView.setAdapter(null);
                                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mCustomProgressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<FuelsheetAPI.FuelSheetListResponse> call, Throwable t) {
                                    Log.i(((Fuelsheet) mContext).getPackageName(), t.getMessage());
                                    mCustomProgressDialog.dismiss();
                                }
                            });
                            break;

                        case "TYRE REPAIR":
                            Call<TyreRepairAPI.TyrerepairResponse> tyreRepair = apiService.getTyreRepairLists(sendValueWithRetrofit(mFromDate, mFromTo));

                            tyreRepair.enqueue(new Callback<TyreRepairAPI.TyrerepairResponse>() {
                                @Override
                                public void onResponse(Call<TyreRepairAPI.TyrerepairResponse> call, Response<TyreRepairAPI.TyrerepairResponse> response) {
                                    if (response.body().getMeta().equals(Constants.SUCCESS)) {
                                        aTyreRepairAdapter = new TyreRepairAdapter(mContext, response.body().getData());
                                        mPaysheetView.setAdapter(aTyreRepairAdapter);
                                        aTyreRepairAdapter.notifyDataSetChanged();
                                    } else {
                                        mPaysheetView.setAdapter(null);
                                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    mCustomProgressDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<TyreRepairAPI.TyrerepairResponse> call, Throwable t) {
                                    Log.i(((Tyrerepair) mContext).getPackageName(), t.getMessage());
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
                        mPaysheetView.setAdapter(null);
                        ((Paysheet) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;
                    case "REPAIR":
                        mPaysheetView.setAdapter(null);
                        ((RepairSheet) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;
                    case "TRIP":
                        mPaysheetView.setAdapter(null);
                        ((Trips) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;
                    case "ACCIDENT REPORT":
                        mPaysheetView.setAdapter(null);
                        ((Accidentreport) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;

                    case "FUELSHEET":
                        mPaysheetView.setAdapter(null);
                        ((Fuelsheet) mContext).searchNotify();
                        listAdjustableMethod(popupSearch, mPaysheetView);
                        break;

                    case "TYRE REPAIR":
                        mPaysheetView.setAdapter(null);
                        ((Tyrerepair) mContext).searchNotify();
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

    public static void fillTripData(Context mContext, List<TripListAPI.Datum> data, ListView mPaysheetView) {
        TripList mTripList;
        List<TripList> mTripListData = new ArrayList<TripList>();
        for (int i = 0; i < data.size(); i++) {
            TripListAPI.Datum datas = data.get(i);
            mTripList = new TripList();
            mTripList.setAdmin_cmt(datas.getAdmin_cmt());
            mTripList.setChangeover(datas.getChangeover());
            mTripList.setCreated_date(datas.getCreated_date());
            mTripList.setDid(datas.getDid());
            mTripList.setDolly(datas.getDolly());
            mTripList.setFirst_name(datas.getFirst_name());
            mTripList.setFrom(datas.getFrom());
            mTripList.setLast_name(datas.getLast_name());
            mTripList.setLoad_due(datas.getLoad_due());
            mTripList.setLoad_from(datas.getLoad_from());
            mTripList.setLoad_type(datas.getLoad_type());
            mTripList.setLocation(datas.getLocation());
            mTripList.setManifest_no(datas.getManifest_no());
            mTripList.setReason(datas.getReason());
            mTripList.setRtbd(datas.getRtbd());
            mTripList.setStatus(datas.getStatus()); // 0 - wait , 1 - accept, 2 - denied
            mTripList.setTid(datas.getTid());
            mTripList.setTrip_id(datas.getTrip_id());
            mTripList.setTo(datas.getTo());
            mTripList.setTrailers(datas.getTrailers());
            mTripList.setTruck(datas.getTruck());
            mTripList.setUid(datas.getUid());
            mTripListData.add(mTripList);
            mTripReport.setData(mTripListData);
        }

        if (mTripReport.getData().size() > 0) {
            mPaysheetView.setAdapter(null);
            aTripAdapter = new TripAdapter(mContext, mTripReport.getData());
            mPaysheetView.setAdapter(aTripAdapter);
            aTripAdapter.notifyDataSetChanged();
        } else {
            mPaysheetView.setAdapter(null);
            Toast.makeText(mContext, mContext.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public static void showDeleteAlert(final Context mContext, final String delete_id, final int position, final String PAGE) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setMessage(mContext.getString(R.string.alert_message));
        mBuilder.setPositiveButton(mContext.getString(R.string.str_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (PAGE) {
                    case "REPAIR":
                        ((RepairSheet) mContext).deleteRepairSheet(delete_id, position);
                        break;
                    case "TRIP":
                        ((Trips) mContext).delete(delete_id, position);
                        break;
                    case "USERS":
                        ((Users) mContext).delete(delete_id, position);
                        break;
                    case "FUELSHEET":
                        ((Fuelsheet) mContext).delete(delete_id, position);
                        break;
                }
                dialog.dismiss();
            }
        });
        mBuilder.setNegativeButton(mContext.getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mBuilder.show();
    }


    public static void buildCountryAlert(Context mContext, LayoutInflater mInflater, final TextView mCountry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View driverlayout = mInflater.inflate(R.layout.dialog_sub_items_list, null);
        builder.setView(driverlayout);
        final AlertDialog alert = builder.create();
        ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DSIL_LV_sub_lists);
        final EditText mSearchWord = (EditText) driverlayout.findViewById(R.id.DSIL_ET_search_word);

        final CountryListAdapter aCountryListAdapter = new CountryListAdapter(mContext, Constants.countrieslist, alert, mCountry);
        mDriverViews.setAdapter(aCountryListAdapter);
        mSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aCountryListAdapter.setSearchEnabled(true, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        alert.show();

    }

    public static void buildUserTypeAlert(Context mContext, LayoutInflater mInflater, final TextView mUserType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View driverlayout = mInflater.inflate(R.layout.dialog_user_type, null);
        builder.setView(driverlayout);

        ListView mDriverViews = (ListView) driverlayout.findViewById(R.id.DUT_LV_sub_lists);

        UserTypeListAdapter aUserTypeListAdapter = new UserTypeListAdapter(mContext, usertype);
        mDriverViews.setAdapter(aUserTypeListAdapter);
        final AlertDialog alert = builder.create();
        alert.show();

        mDriverViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mUserType.setText(Constants.usertype.get(position).getName());
                alert.dismiss();
            }
        });
    }

    public static void fillFuelSheetData(Context mContext, List<FuelsheetAPI.Datum> data, ListView mPaysheetView) {
        ArrayList<FuelSheetModel> mFuelListData;
        mFuelListData = new ArrayList<FuelSheetModel>();
        for (FuelsheetAPI.Datum mData : data) {
            FuelSheetModel mFuelSheetModel = new FuelSheetModel();
            mFuelSheetModel.setFdate(mData.getFdate());
            mFuelSheetModel.setTruckNo(mData.getTruckNo());
            mFuelSheetModel.setTrailerNo(mData.getTrailerNo());
            mFuelSheetModel.setTrlDiesel(mData.getTrlDiesel());
            mFuelSheetModel.setTrlOil(mData.getTrlOil());
            mFuelSheetModel.setPlace(mData.getPlace());
            mFuelSheetModel.setCurKm(mData.getCurKm());
            mFuelSheetModel.setPreKm(mData.getPreKm());
            mFuelSheetModel.setDiesel(mData.getDiesel());
            mFuelSheetModel.setOil(mData.getOil());
            mFuelSheetModel.setEmail(mData.getEmail());
            mFuelSheetModel.setFid(mData.getFid());
            mFuelSheetModel.setFirstName(mData.getFirstName());
            mFuelSheetModel.setLastName(mData.getLastName());
            mFuelListData.add(mFuelSheetModel);
            mFuelSheetData.setData(mFuelListData);
        }

        if (mFuelSheetData.getData().size() > 0) {
            aFuelSheetAdapter = new FuelSheetAdapter(mContext, mFuelSheetData.getData());
            mPaysheetView.setAdapter(aFuelSheetAdapter);
            aFuelSheetAdapter.notifyDataSetChanged();
        }
    }

}
