package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 13-03-2017.
 */
public class DetailLogAPI {
    private static DetailLogAPI ourInstance = new DetailLogAPI();

    public static DetailLogAPI getInstance() {
        return ourInstance;
    }

    private DetailLogAPI() {
    }

    public class Data {

        @SerializedName("sno")
        @Expose
        private Integer sno;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("workhrs")
        @Expose
        private Integer workhrs;
        @SerializedName("totalhrs")
        @Expose
        private Integer totalhrs;
        @SerializedName("availhrs")
        @Expose
        private Integer availhrs;
        @SerializedName("cmt")
        @Expose
        private String cmt;

        public Integer getSno() {
            return sno;
        }

        public void setSno(Integer sno) {
            this.sno = sno;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getWorkhrs() {
            return workhrs;
        }

        public void setWorkhrs(Integer workhrs) {
            this.workhrs = workhrs;
        }

        public Integer getTotalhrs() {
            return totalhrs;
        }

        public void setTotalhrs(Integer totalhrs) {
            this.totalhrs = totalhrs;
        }

        public Integer getAvailhrs() {
            return availhrs;
        }

        public void setAvailhrs(Integer availhrs) {
            this.availhrs = availhrs;
        }

        public String getCmt() {
            return cmt;
        }

        public void setCmt(String cmt) {
            this.cmt = cmt;
        }

    }


    public class DetailLogResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("meta")
        @Expose
        private Integer meta;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;
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

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
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
