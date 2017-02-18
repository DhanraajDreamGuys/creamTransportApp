package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 15-02-2017.
 */
public class RepairsheetCurrentDayAPI {
    private static RepairsheetCurrentDayAPI ourInstance = new RepairsheetCurrentDayAPI();

    public static RepairsheetCurrentDayAPI getInstance() {
        return ourInstance;
    }

    private RepairsheetCurrentDayAPI() {
    }

    public class Datum implements Serializable{

        @SerializedName("rdate")
        @Expose
        private String rdate;
        @SerializedName("truck_no")
        @Expose
        private String truck_no;
        @SerializedName("trl_no")
        @Expose
        private String trl_no;
        @SerializedName("dolly_no")
        @Expose
        private String dolly_no;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("rid")
        @Expose
        private String rid;
        @SerializedName("regn_no")
        @Expose
        private String regn_no;
        @SerializedName("regn1_no")
        @Expose
        private String regn1_no;
        @SerializedName("regn2_no")
        @Expose
        private String regn2_no;
        @SerializedName("image_one")
        @Expose
        private String image_one;
        @SerializedName("image_two")
        @Expose
        private String image_two;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("faults")
        @Expose
        private String faults;
        @SerializedName("uid")
        @Expose
        private String uid;

        public String getRdate() {
            return rdate;
        }

        public void setRdate(String rdate) {
            this.rdate = rdate;
        }

        public String getTruck_no() {
            return truck_no;
        }

        public void setTruck_no(String truck_no) {
            this.truck_no = truck_no;
        }

        public String getTrl_no() {
            return trl_no;
        }

        public void setTrl_no(String trl_no) {
            this.trl_no = trl_no;
        }

        public String getDolly_no() {
            return dolly_no;
        }

        public void setDolly_no(String dolly_no) {
            this.dolly_no = dolly_no;
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

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getRegn_no() {
            return regn_no;
        }

        public void setRegn_no(String regn_no) {
            this.regn_no = regn_no;
        }

        public String getRegn1_no() {
            return regn1_no;
        }

        public void setRegn1_no(String regn1_no) {
            this.regn1_no = regn1_no;
        }

        public String getRegn2_no() {
            return regn2_no;
        }

        public void setRegn2_no(String regn2_no) {
            this.regn2_no = regn2_no;
        }

        public String getImage_one() {
            return image_one;
        }

        public void setImage_one(String image_one) {
            this.image_one = image_one;
        }

        public String getImage_two() {
            return image_two;
        }

        public void setImage_two(String image_two) {
            this.image_two = image_two;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getFaults() {
            return faults;
        }

        public void setFaults(String faults) {
            this.faults = faults;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

    }

    public class RepairsheetResponse {

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
