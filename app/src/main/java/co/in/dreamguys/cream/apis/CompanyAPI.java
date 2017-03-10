package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 09-03-2017.
 */
public class CompanyAPI {
    private static CompanyAPI ourInstance = new CompanyAPI();

    public static CompanyAPI getInstance() {
        return ourInstance;
    }

    private CompanyAPI() {
    }

    public class CompanyResponse {

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

    public class Datum implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("ref_str")
        @Expose
        private String ref_str;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("reg_no")
        @Expose
        private String reg_no;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("user_type")
        @Expose
        private String user_type;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("phone2")
        @Expose
        private String phone2;
        @SerializedName("skype")
        @Expose
        private String skype;
        @SerializedName("fax")
        @Expose
        private String fax;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("pin_code")
        @Expose
        private String pin_code;
        @SerializedName("notes")
        @Expose
        private String notes;
        @SerializedName("delete_sts")
        @Expose
        private String delete_sts;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReg_no() {
            return reg_no;
        }

        public void setReg_no(String reg_no) {
            this.reg_no = reg_no;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        public String getSkype() {
            return skype;
        }

        public void setSkype(String skype) {
            this.skype = skype;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPin_code() {
            return pin_code;
        }

        public void setPin_code(String pin_code) {
            this.pin_code = pin_code;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getDelete_sts() {
            return delete_sts;
        }

        public void setDelete_sts(String delete_sts) {
            this.delete_sts = delete_sts;
        }

    }
}
