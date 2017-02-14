package co.in.dreamguys.cream.apis;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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


}