package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 23-02-2017.
 */
public class LeaveAPI {
    private static LeaveAPI ourInstance = new LeaveAPI();

    public static LeaveAPI getInstance() {
        return ourInstance;
    }

    private LeaveAPI() {
    }

    public class Datum implements Serializable {

        @SerializedName("lid")
        @Expose
        private String lid;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("ldate")
        @Expose
        private String ldate;
        @SerializedName("fromdate")
        @Expose
        private String fromdate;
        @SerializedName("todate")
        @Expose
        private String todate;
        @SerializedName("leavetype")
        @Expose
        private String leavetype;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("lastwork")
        @Expose
        private String lastwork;
        @SerializedName("fromwork")
        @Expose
        private String fromwork;
        @SerializedName("others")
        @Expose
        private String others;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("no_days")
        @Expose
        private String no_days;
        @SerializedName("sup_name")
        @Expose
        private String sup_name;
        @SerializedName("sup_position")
        @Expose
        private String sup_position;
        @SerializedName("app_date")
        @Expose
        private String app_date;
        @SerializedName("lsts")
        @Expose
        private String lsts;

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLdate() {
            return ldate;
        }

        public void setLdate(String ldate) {
            this.ldate = ldate;
        }

        public String getFromdate() {
            return fromdate;
        }

        public void setFromdate(String fromdate) {
            this.fromdate = fromdate;
        }

        public String getTodate() {
            return todate;
        }

        public void setTodate(String todate) {
            this.todate = todate;
        }

        public String getLeavetype() {
            return leavetype;
        }

        public void setLeavetype(String leavetype) {
            this.leavetype = leavetype;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLastwork() {
            return lastwork;
        }

        public void setLastwork(String lastwork) {
            this.lastwork = lastwork;
        }

        public String getFromwork() {
            return fromwork;
        }

        public void setFromwork(String fromwork) {
            this.fromwork = fromwork;
        }

        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNo_days() {
            return no_days;
        }

        public void setNo_days(String no_days) {
            this.no_days = no_days;
        }

        public String getSup_name() {
            return sup_name;
        }

        public void setSup_name(String sup_name) {
            this.sup_name = sup_name;
        }

        public String getSup_position() {
            return sup_position;
        }

        public void setSup_position(String sup_position) {
            this.sup_position = sup_position;
        }

        public String getApp_date() {
            return app_date;
        }

        public void setApp_date(String app_date) {
            this.app_date = app_date;
        }

        public String getLsts() {
            return lsts;
        }

        public void setLsts(String lsts) {
            this.lsts = lsts;
        }

    }

    public class LeaveListResponse {

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
