package co.in.dreamguys.cream.apis;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

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

    @GET("settings_user_status_list")
    Call<UserstatuslistAPI.UserStatusListResponse> getUserStatusLists();

    @FormUrlEncoded
    @POST("settings_user_status_edit")
    Call<UpdateUsersAPI.UpdateUsersResponse> updateUserStatus(@FieldMap HashMap<String, String> meta);

    @GET("custom_fields_list")
    Call<CustomFieldsAPI.CustomFieldResponse> getCustomFields();

    @GET("custom_fields_type")
    Call<CustomFieldTypeAPI.CustomFieldTypeResponse> getCustomFieldsType();

    @FormUrlEncoded
    @POST("custom_fields_add")
    Call<UpdateUsersAPI.UpdateUsersResponse> addCustomFieldsType(@FieldMap HashMap<String, String> meta);

    @GET("usefulllinks_list")
    Call<UsefulLinksAPI.UsefulLinksResponse> getUsefulLinks();

    @FormUrlEncoded
    @POST("usefulllinks_add")
    Call<UpdateUsersAPI.UpdateUsersResponse> addUsefulLinks(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("appsetting_edit")
    Call<UpdateUsersAPI.UpdateUsersResponse> editAppSettings(@FieldMap HashMap<String, String> meta);

    @Multipart
    @POST("appsetting_edit")
    Call<UpdateUsersAPI.UpdateUsersResponse> saveAppSettings(@PartMap() Map<String, RequestBody> partMap,
                                                             @Part MultipartBody.Part file,
                                                             @Part MultipartBody.Part file1,
                                                             @Part MultipartBody.Part file2);

    @GET("appsetting_list")
    Call<AppsettingAPI.AppsettingsResponse> getAppSettings();

    @GET("pdf_file/{fullUrl}")
    @Streaming
    Call<ResponseBody> downloadFile(@Path("fullUrl") String fullUrl);

    @FormUrlEncoded
    @POST("pdfform")
    Call<PrintURLAPI.PrintURLResponse> getPrintURL(@FieldMap HashMap<String, String> meta);

    @GET("phonebook_trucklist")
    Call<PBTruckAPI.PBTruckListResponse> getPBTruckLists();

    @FormUrlEncoded
    @POST("phonebook_truckedit")
    Call<UpdateUsersAPI.UpdateUsersResponse> editPBTrucks(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("phonebook_statusUpdate")
    Call<UpdateUsersAPI.UpdateUsersResponse> editStatus(@Field("id") String id, @Field("status") String truck_no, @Field("truck_no") String status);

    @FormUrlEncoded
    @POST("phonebook_truckadd")
    Call<UpdateUsersAPI.UpdateUsersResponse> addPBTrucks(@FieldMap HashMap<String, String> meta);

    @GET("phonebook_workshoplist")
    Call<PBWorkShopAPI.PBWorkShopListResponse> getPBWorkShopLists();

    @FormUrlEncoded
    @POST("phonebook_workshopedit")
    Call<UpdateUsersAPI.UpdateUsersResponse> editPBWorkshop(@FieldMap HashMap<String, String> meta);


    @FormUrlEncoded
    @POST("phonebook_workshop_statusUpdate")
    Call<UpdateUsersAPI.UpdateUsersResponse> editWorkshop(@Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("phonebook_customer_statusUpdate")
    Call<UpdateUsersAPI.UpdateUsersResponse> updateCustomerStatus(@Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("phonebook_workshopadd")
    Call<UpdateUsersAPI.UpdateUsersResponse> addPBWorkshop(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("phonebook_customeradd")
    Call<UpdateUsersAPI.UpdateUsersResponse> addPBCustomer(@FieldMap HashMap<String, String> meta);

    @GET("phonebook_managementlist")
    Call<PBManagementAPI.PBManagementListResponse> getPBManagementLists();

    @FormUrlEncoded
    @POST("phonebook_managementedit")
    Call<UpdateUsersAPI.UpdateUsersResponse> editPBManagement(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("phonebook_management_statusUpdate")
    Call<UpdateUsersAPI.UpdateUsersResponse> editManagementStatus(@Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("phonebook_managementadd")
    Call<UpdateUsersAPI.UpdateUsersResponse> addPBManagement(@FieldMap HashMap<String, String> meta);

    @GET("phonebook_customerlist")
    Call<PBCustomerAPI.PBCustomerResponse> getPBCustomerLists();

    @GET("tyrerepair_list")
    Call<TyreRepairAPI.TyrerepairResponse> getTyreRepairs();

    @FormUrlEncoded
    @POST("tyrerepair_search_result")
    Call<TyreRepairAPI.TyrerepairResponse> getTyreRepairLists(@FieldMap HashMap<String, String> meta);

    @GET("alert_email_list")
    Call<AlertemplateAPI.AlertTemplateResponse> getTemplates();

    @FormUrlEncoded
    @POST("alert_email")
    Call<UpdateUsersAPI.UpdateUsersResponse> sendAlerts(@FieldMap HashMap<String, String> meta);

    @GET("company_list")
    Call<CompanyAPI.CompanyResponse> getCompany();

    @FormUrlEncoded
    @POST("company_update")
    Call<UpdateUsersAPI.UpdateUsersResponse> saveCompanyDetails(@FieldMap HashMap<String, String> meta);
}