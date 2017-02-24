package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 23-02-2017.
 */
public class AccidentReportAPI {
    private static AccidentReportAPI ourInstance = new AccidentReportAPI();

    public static AccidentReportAPI getInstance() {
        return ourInstance;
    }

    private AccidentReportAPI() {
    }

    public class AccidentReportResponse {

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

        @SerializedName("tid")
        @Expose
        private String tid;
        @SerializedName("acc_date")
        @Expose
        private String acc_date;
        @SerializedName("acc_time")
        @Expose
        private String acc_time;
        @SerializedName("acc_loc")
        @Expose
        private String acc_loc;
        @SerializedName("othervehicles")
        @Expose
        private String othervehicles;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("witness")
        @Expose
        private String witness;
        @SerializedName("mytruck")
        @Expose
        private String mytruck;
        @SerializedName("images")
        @Expose
        private String images;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getAcc_date() {
            return acc_date;
        }

        public void setAcc_date(String acc_date) {
            this.acc_date = acc_date;
        }

        public String getAcc_time() {
            return acc_time;
        }

        public void setAcc_time(String acc_time) {
            this.acc_time = acc_time;
        }

        public String getAcc_loc() {
            return acc_loc;
        }

        public void setAcc_loc(String acc_loc) {
            this.acc_loc = acc_loc;
        }

        public String getOthervehicles() {
            return othervehicles;
        }

        public void setOthervehicles(String othervehicles) {
            this.othervehicles = othervehicles;
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

        public String getWitness() {
            return witness;
        }

        public void setWitness(String witness) {
            this.witness = witness;
        }

        public String getMytruck() {
            return mytruck;
        }

        public void setMytruck(String mytruck) {
            this.mytruck = mytruck;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

    }
}
