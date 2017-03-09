package co.in.dreamguys.cream.utils;

import java.util.List;

import co.in.dreamguys.cream.CustomFields;
import co.in.dreamguys.cream.EngineCodec;
import co.in.dreamguys.cream.FridgeCodec;
import co.in.dreamguys.cream.Fuelsheet;
import co.in.dreamguys.cream.Leave;
import co.in.dreamguys.cream.RepairSheet;
import co.in.dreamguys.cream.TripHours;
import co.in.dreamguys.cream.Trips;
import co.in.dreamguys.cream.UsefulLinks;
import co.in.dreamguys.cream.UserStatus;
import co.in.dreamguys.cream.Users;
import co.in.dreamguys.cream.apis.BranchAPI;
import co.in.dreamguys.cream.apis.CustomFieldTypeAPI;
import co.in.dreamguys.cream.apis.DriverListsAPI;
import co.in.dreamguys.cream.apis.ListCountriesAPI;
import co.in.dreamguys.cream.apis.UserTypeAPI;
import co.in.dreamguys.cream.apis.UserstatuslistAPI;

/**
 * Created by user5 on 13-02-2017.
 */

public class Constants {


    public static List<CustomFieldTypeAPI.Datum> CUSTOM_FIELD_TYPE;
    public static UserStatus USER_STATUS;
    public static List<UserstatuslistAPI.Statu> USER_STATUS_LISTS;
    public static String USERS_DATA = "user_data";
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
    public static String VIEW_TYPE = "view_type";
    public static String MODE = "mode";
    public static String PARAMS_START_DATE = "sdate";
    public static String PARAMS_END_DATE = "edate";

    public static String PAYSHEET = "PAYSHEET";
    public static String REPAIR = "REPAIR";
    public static String TRIPS = "TRIP";


    public static String PARAMS_ID = "id";
    public static String PARAMS_TRIP_ID = "trip_id";
    public static String PARAMS_OFFICE_USE = "office_use_comts";
    public static String REPAIRSHEETDETAILS = "repair_sheet_details";
    public static String TRIPSHEETDETAILS = "trip_sheet";
    public static List<BranchAPI.Datum> countries;
    public static int From, To;
    public static co.in.dreamguys.cream.AdminMenu AdminMenu;
    public static String PARAMS_DRIVER_ID = "driver_id";
    public static String PARAMS_TRUCK_NO = "truck";
    public static String PARAMS_TRUCK_ID = "truck_no";
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
    public static String PARAMS_USER_ID = "user_id";
    public static RepairSheet Repairsheet;
    public static co.in.dreamguys.cream.ViewRepairsheet ViewRepairsheet;

    public static String FromString = "From";
    public static String ToString = "To";
    public static String PARAMS_LDATE = "ldate";
    public static String PARAMS_DID = "did";
    public static String USERS = "USERS";
    public static String PARAMS_FIRSTNAME = "first_name";
    public static String PARAMS_LASTTNAME = "last_name";
    public static String PARAMS_EMAIL = "email";
    public static String PARAMS_PHONE = "phone";
    public static String PARAMS_PHONE_NO = "phone_no";
    public static String PARAMS_STREET = "street";
    public static String PARAMS_CITY = "city";
    public static String PARAMS_STATE = "state";
    public static String PARAMS_COUNTRY = "country";
    public static String PARAMS_PINCODE = "pincode";


    public static Users USERSCLASS;
    public static List<ListCountriesAPI.Datum> countrieslist;
    public static List<UserTypeAPI.Datum> usertype;
    public static String EDIT_LEAVE_DATA = "leave_edit_data";
    public static String PARAMS_APP_DATE = "app_date";
    public static String PARAMS_SIGNATURE = "signature";
    public static String PARAMS_PRINTED_NAME = "printed_name";
    public static String PARAMS_APPROVE = "approve";
    public static Leave LEAVEFORM;
    public static String EDIT_ACCIDENT_DATA = "edit_accident_data";
    public static String ACCIDENT_REPORT = "ACCIDENT REPORT";
    public static String PREVIEW_ACCIDENT_REPORT = "preview_accident_report";
    public static String CURRENT_IMAGE = "current_image";
    public static String PARAMS_USER_TYPE = "user_type";
    public static String PARAMS_USER_BRANCH = "user_branch";
    public static String PARAMS_ORDER_ASC = "order_acss";
    public static String PARAMS_FTNAME = "fname";
    public static String PARAMS_LTNAME = "lname";
    public static String PARAMS_UTYPE = "usrtype";
    public static String PARAMS_UBRANCH = "brlocation";
    public static String PARAMS_CDATE = "c_date";
    public static String FUEL_SHEET = "fuel_sheet";
    public static String FUELSHEET = "FUELSHEET";
    public static String FUEL_SHEET_DATA = "fuel_sheet_data";
    public static Fuelsheet FUELSHEETCLASS;
    public static Trips TRIPCLASS;
    public static String FRIDGE_CODE_DATA = "fridge_code_data";
    public static String PARAMS_TYPE = "type";
    public static String PARAMS_CODE = "code";
    public static String PARAMS_COLOR = "color";
    public static String PARAMS_DESC = "desc";
    public static String PARAMS_OPERATION = "operation";
    public static FridgeCodec FRIDGE_CODEC;
    public static String FRIDGE_CODEC_STRING = "FRIDGE CODEC";
    public static EngineCodec ENGINE_CODEC;
    public static String ENGINE_CODEC_STRING = "ENGINE CODEC";
    public static String ENGINE_CODE_DATA = "engine_codec_data";
    public static String EDIT_TRIP_HR_DATA = "trip_hours_data";
    public static String PARAMS_EDIT_ID = "edit_id";
    public static String PARAMS_TRIP_HOUR = "trip_hour";
    public static String PARAMS_FROM_TRIP = "from_trip";
    public static String PARAMS_TO_TRIP = "to_trip";
    public static TripHours TRIPHOURS;
    public static String USER_STATUS_DATA = "user_status_data";
    public static String PARAMS_UID = "uid";
    public static String PARAMS_REASON = "reason";
    public static String PARAMS_ONOFF = "onoff";
    public static String PARAMS_CUSTOM_TYPE = "type";
    public static String PARAMS_CUSTOM_NAME = "name";
    public static CustomFields CUSTOM_FIELD;
    public static String PARAMS_LINK_NAME = "name";
    public static String PARAMS_WEB_LINK = "weblink";
    public static String PARAMS_LINK_ID = "id";
    public static String PARAMS_LINK_STATUS = "status";
    public static String PARAMS_DESCRIPTION = "description";
    public static String PARAMS_SPLASH_TEXT = "splashtext";


    public static UsefulLinks USEFULLINK;
    public static String EDIT_USEFUL_LINKS = "edit_useful_links";
    public static String LEAVEFORMSTRING = "";
    public static String URL_LINK = "url_link";
    public static String PB_TRUCK_DATA = "pb_truck_data";
    public static String PB_WORKSHOP_DATA = "pb_work_shop_data";

    public static String PARAMS_ADDRESS = "address";
    public static String PARAMS_MANAGER = "manager";
    public static String PB_MANAGEMENT_DATA = "pb_management_data";
    public static String PB_CUSTOMER_DATA = "pb_customer_data";
    public static String TYREREPAIR = "TYRE REPAIR";
    public static String TYRE_REPAIR_DATA = "tyre_repair_data";
}
