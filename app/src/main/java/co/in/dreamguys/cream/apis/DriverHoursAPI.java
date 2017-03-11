package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 10-03-2017.
 */
public class DriverHoursAPI {
    private static DriverHoursAPI ourInstance = new DriverHoursAPI();

    public static DriverHoursAPI getInstance() {
        return ourInstance;
    }

    private DriverHoursAPI() {
    }

    public class Datum {


        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("tdate")
        @Expose
        private String tdate;
        @SerializedName("tid")
        @Expose
        private String tid;
        @SerializedName("hrs")
        @Expose
        private String hrs;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("nbcnt")
        @Expose
        private String nbcnt;
        @SerializedName("completenightbreak")
        @Expose
        private String completenightbreak;
        @SerializedName("nighthrs")
        @Expose
        private Integer nighthrs;
        @SerializedName("availhrs")
        @Expose
        private Integer availhrs;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getTdate() {
            return tdate;
        }

        public void setTdate(String tdate) {
            this.tdate = tdate;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getHrs() {
            return hrs;
        }

        public void setHrs(String hrs) {
            this.hrs = hrs;
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

        public String getNbcnt() {
            return nbcnt;
        }

        public void setNbcnt(String nbcnt) {
            this.nbcnt = nbcnt;
        }

        public String getCompletenightbreak() {
            return completenightbreak;
        }

        public void setCompletenightbreak(String completenightbreak) {
            this.completenightbreak = completenightbreak;
        }

        public Integer getNighthrs() {
            return nighthrs;
        }

        public void setNighthrs(Integer nighthrs) {
            this.nighthrs = nighthrs;
        }

        public Integer getAvailhrs() {
            return availhrs;
        }

        public void setAvailhrs(Integer availhrs) {
            this.availhrs = availhrs;
        }


    }

    public class DriverHoursResponse {

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
