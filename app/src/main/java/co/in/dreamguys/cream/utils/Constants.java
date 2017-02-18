package co.in.dreamguys.cream.utils;

import java.util.List;

import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.apis.CountriesAPI;
import co.in.dreamguys.cream.apis.DriverListsAPI;

/**
 * Created by user5 on 13-02-2017.
 */

public class Constants {

    public static String USER_ID = "user_id";
    public static String PARAMS_PASSWORD = "password";
    public static String PARAMS_EMAILID = "user_name";
    public static Integer SUCCESS = 200;

    public static String FIRSTNAME = "firstname";
    public static String LASTNAME = "lastname";
    public static String EMAIL = "email";
    public static String USERTYPE = "usertype";

    public static List<DriverListsAPI.Datum> driverList;
    public static String PAYSHEETDETAILS = "paysheetdetails";
    public static String TYPE = "type";
    public static String MODE = "mode";
    public static String PARAMS_START_DATE = "sdate";
    public static String PARAMS_END_DATE = "edate";

    public static String PAYSHEET = "PAYSHEET";
    public static String REPAIR = "REPAIR";

    public static String PARAMS_ID = "id";
    public static String PARAMS_OFFICE_USE = "office_use_comts";
    public static String REPAIRSHEETDETAILS = "repair_sheet_details";
    public static List<CountriesAPI.Datum> countries;
    public static int From, To;
    public static co.in.dreamguys.cream.AdminMenu AdminMenu;
    public static String PARAMS_DRIVER_ID = "driver_id";
    public static String PARAMS_TRUCK_NO = "truck";
    public static String PARAMS_TRAILERS = "trailers";
    public static String PARAMS_MNO = "mno";
    public static String PARAMS_DOLLYNO = "dolly";
    public static String PARAMS_LTIME = "ltime";
    public static String PARAMS_LFROM = "lfrom";
    public static String PARAMS_LDUE = "ldue";
    public static String PARAMS_IDFROM = "ldfrom";
    public static String PARAMS_CTRUCK = "ctruck";
    public static String PARAMS_CDRIVER = "cdriver";
    public static String PARAMS_ITYPE = "ltype";
    public static String PARAMS_SDATE = "sdate";
    public static String PARAMS_FROM = "from";
    public static String PARAMS_TRIP_TO = "trip_to";
    public static String PARAMS_ADMIN_COMMENT = "admin_cmt";
    public static RepairSheet Repairsheet;
    public static co.in.dreamguys.cream.ViewRepairsheet ViewRepairsheet;
}
