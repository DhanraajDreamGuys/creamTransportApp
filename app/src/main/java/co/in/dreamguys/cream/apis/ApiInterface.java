package co.in.dreamguys.cream.apis;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginAPI.LoginResponse> getLoginDetails(@FieldMap HashMap<String, String> meta);

    @FormUrlEncoded
    @POST("view_profile")
    Call<AccountAPI.AccountResponse> getAccount(@Field("user_id") String stringPref);

    @Multipart
    @POST("edit_profile")
    Call<EditAccountAPI.EditAccountResponse> getEditAccount(@Part("user_id") String user_id, @Part("first_name") String first_name,
                                                            @Part("last_name") String last_name, @Part("email") String email,
                                                            @Part("usertype") String usertype, @Part MultipartBody.Part file,
                                                            @Part("password") String password, @Part("pure_password") String pure_password);

    @GET("drivers_list")
    Call<DriverListsAPI.DriverResponse> getDriverLists();

    @GET("country_list")
    Call<CountriesAPI.CountryListResponse> getCountries();

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


}