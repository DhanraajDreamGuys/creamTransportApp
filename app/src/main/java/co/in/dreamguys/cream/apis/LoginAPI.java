package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 14-02-2017.
 */
public class LoginAPI {
    private static LoginAPI ourInstance = new LoginAPI();

    public static LoginAPI getInstance() {
        return ourInstance;
    }

    private LoginAPI() {
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("ref_str")
        @Expose
        private String ref_str;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("pure_password")
        @Expose
        private String pure_password;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("profile_img")
        @Expose
        private String profile_img;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("user_type")
        @Expose
        private String user_type;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("date_created")
        @Expose
        private String date_created;
        @SerializedName("date_deleted")
        @Expose
        private String date_deleted;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("delete_sts")
        @Expose
        private String delete_sts;
        @SerializedName("working_sts")
        @Expose
        private String working_sts;
        @SerializedName("reason")
        @Expose
        private String reason;
        @SerializedName("branch_id")
        @Expose
        private String branch_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRef_str() {
            return ref_str;
        }

        public void setRef_str(String ref_str) {
            this.ref_str = ref_str;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPure_password() {
            return pure_password;
        }

        public void setPure_password(String pure_password) {
            this.pure_password = pure_password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }

        public String getDate_deleted() {
            return date_deleted;
        }

        public void setDate_deleted(String date_deleted) {
            this.date_deleted = date_deleted;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelete_sts() {
            return delete_sts;
        }

        public void setDelete_sts(String delete_sts) {
            this.delete_sts = delete_sts;
        }

        public String getWorking_sts() {
            return working_sts;
        }

        public void setWorking_sts(String working_sts) {
            this.working_sts = working_sts;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

    }

    public class LoginResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("meta")
        @Expose
        private Integer meta;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("message")
        @Expose
        private String message;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public Integer getMeta() {
            return meta;
        }

        public void setMeta(Integer meta) {
            this.meta = meta;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
