package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 28-02-2017.
 */
public class TripHoursAPI {
    private static TripHoursAPI ourInstance = new TripHoursAPI();

    public static TripHoursAPI getInstance() {
        return ourInstance;
    }

    private TripHoursAPI() {
    }

    public class Datum implements Serializable{

        @SerializedName("hid")
        @Expose
        private String hid;
        @SerializedName("p1")
        @Expose
        private String p1;
        @SerializedName("p2")
        @Expose
        private String p2;
        @SerializedName("hours")
        @Expose
        private String hours;

        public String getHid() {
            return hid;
        }

        public void setHid(String hid) {
            this.hid = hid;
        }

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

    }

    public class TripHoursResponse {

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
