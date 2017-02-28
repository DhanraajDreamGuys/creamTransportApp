package co.in.dreamguys.cream.apis;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginAPI.LoginResponse> getLoginDetails(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("view_profile")
    Call<AccountAPI.AccountResponse> getAccount(@Field("user_id") String stringPref);

    @Multipart
    @POST("edit_profile")
    Call<EditAccountAPI.EditAccountResponse> getEditAccount(@Part("user_id") RequestBody user_id, @Part("first_name") RequestBody first_name,
                                                            @Part("last_name") RequestBody last_name, @Part("email") RequestBody email,
                                                            @Part("usertype") RequestBody usertype, @Part MultipartBody.Part file,
                                                            @Part("password") RequestBody password, @Part("pure_password") RequestBody pure_password);

    @GET("drivers_list")
    Call<DriverListsAPI.DriverResponse> getDriverLists();

    @GET("country_list")
    Call<BranchAPI.CountryListResponse> getCountries();

    @GET("repair_currentDay_result")
    Call<RepairsheetCurrentDayAPI.RepairsheetResponse> getRepairsheet();

    @FormUrlEncoded
    @POST("paysheet_lastWeek_result")
    Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> getPaysheetLastWeekReport(@Field("user_id") String stringPref);

    @FormUrlEncoded
    @POST("paysheet_search_result")
    Call<PaysheetLastWeekAPI.PaysheetLastWeekResponse> getSearchPaysheetReport(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("repair_search_result")
    Call<RepairsheetCurrentDayAPI.RepairsheetResponse> getSearchRepairsheetReport(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("update_paysheet")
    Call<UpdateSheetAPI.UpdatePaysheetResponse> getUpdatePaysheetReport(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("update_repairsheet")
    Call<UpdateSheetAPI.UpdatePaysheetResponse> getUpdateRepairsheetReport(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("delete_repairsheet")
    Call<DeleteRepairsheetAPI.DeleteRepairsheetResponse> getDeleteRepairsheetReport(@Field("id") String stringPref);

    @FormUrlEncoded
    @POST("view_repairsheet")
    Call<RepairsheetCurrentDayAPI.RepairsheetResponse> getViewRepairsheetReport(@Field("id") String stringPref);

    @FormUrlEncoded
    @POST("add_trip")
    Call<AddTripAPI.AddTripResponse> addNewTrip(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("trip_search_filter")
    Call<TripListAPI.TripsResponse> getTripLists(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("view_trip")
    Call<TripListAPI.TripsResponse> getViewTripSheet(@Field("id") String stringPref);

    @FormUrlEncoded
    @POST("delete_trip")
    Call<DeleteSheetAPI.DeleteTripsResponse> getDeleteTrip(@Field("id") String stringPref);

    @GET("current_triplist")
    Call<TripListAPI.TripsResponse> getCurrentTrips();

    @FormUrlEncoded
    @POST("trip_edit")
    Call<UpdateSheetAPI.UpdatePaysheetResponse> getUpdateTripsheetReport(@FieldMap HashMap<String, String> meta);

    @GET("dashboard")
    Call<DashboardAPI.DashboardResponse> getDashboard();

    @GET("users_list")
    Call<UsersAPI.UsersResponse> getUsers();

    @GET("list_country")
    Call<ListCountriesAPI.CountryListResponse> getListCountries();

    @GET("user_type")
    Call<UserTypeAPI.UsersTypeResponse> getUsersType();

    @Multipart
    @POST("users_edit")
    Call<UpdateUsersAPI.UpdateUsersResponse> getEditUser(@PartMap() Map<String, RequestBody> partMap,
                                                         @Part MultipartBody.Part file);

    @Multipart
    @POST("add_users")
    Call<UpdateUsersAPI.UpdateUsersResponse> getAddUser(@PartMap() Map<String, RequestBody> partMap,
                                                        @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("delete_user")
    Call<UpdateUsersAPI.UpdateUsersResponse> getDeleteUser(@Field("id") String id);


    @GET("leave_form_list")
    Call<LeaveAPI.LeaveListResponse> getLeaveLists();

    @FormUrlEncoded
    @POST("edit_leave_form")
    Call<UpdateUsersAPI.UpdateUsersResponse> saveLeaveForm(@FieldMap HashMap<String, String> meta);

    @GET("accident_list")
    Call<AccidentReportAPI.AccidentReportResponse> getAccidentResports();

    @FormUrlEncoded
    @POST("accident_search_result")
    Call<AccidentReportAPI.AccidentReportResponse> getSearchAccidentResports(@FieldMap HashMap<String, String> meta);

    @GET("fuelsheet_current_list")
    Call<FuelsheetAPI.FuelSheetListResponse> getFuelsheetLists();

    @FormUrlEncoded
    @POST("fuelsheet_search_result")
    Call<FuelsheetAPI.FuelSheetListResponse> getFuelsheetLists(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("delete_fuelsheet")
    Call<UpdateUsersAPI.UpdateUsersResponse> getDeleteFuelSheet(@Field("id") String id);

    @FormUrlEncoded
    @POST("fridgecode_codes")
    Call<FridgeCodeAPI.FridgecodeResponse> getFridgeCodeList(@Field("type") String type);

    @GET("fridgecode")
    Call<FridgeCodeAPI.FridgecodeResponse> getFridgeCodeList();

    @FormUrlEncoded
    @POST("fridgecode_add")
    Call<UpdateUsersAPI.UpdateUsersResponse> addFridgeCodec(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("fridgecode_edit")
    Call<UpdateUsersAPI.UpdateUsersResponse> updateFridgeCodec(@FieldMap HashMap<String, String> meta);

    @GET("enginecode")
    Call<FridgeCodeAPI.FridgecodeResponse> getEngineCodeList();

    @FormUrlEncoded
    @POST("enginecode_add")
    Call<UpdateUsersAPI.UpdateUsersResponse> addEngineCodec(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("enginecode_edit")
    Call<UpdateUsersAPI.UpdateUsersResponse> updateEngineCodec(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("fridgecode_status")
    Call<UpdateUsersAPI.UpdateUsersResponse> addFridgeCodec(@Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("enginecode_status")
    Call<UpdateUsersAPI.UpdateUsersResponse> addEngineCodec(@Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("enginecode_codes")
    Call<FridgeCodeAPI.FridgecodeResponse> getEngineCodeList(@Field("type") String type);

    @GET("trip_hours_list")
    Call<TripHoursAPI.TripHoursResponse> getTripHoursLists();

    @FormUrlEncoded
    @POST("trip_edit_hour")
    Call<UpdateUsersAPI.UpdateUsersResponse> addTripHours(@FieldMap HashMap<String, String> meta);

}