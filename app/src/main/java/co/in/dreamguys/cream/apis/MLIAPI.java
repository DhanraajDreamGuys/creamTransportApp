package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 13-03-2017.
 */
public class MLIAPI {
    private static MLIAPI ourInstance = new MLIAPI();

    public static MLIAPI getInstance() {
        return ourInstance;
    }

    private MLIAPI() {
    }

    public class Datum {

        @SerializedName("tid")
        @Expose
        private String tid;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("reason")
        @Expose
        private String reason;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("truck")
        @Expose
        private String truck;
        @SerializedName("trailers")
        @Expose
        private String trailers;
        @SerializedName("manifest_no")
        @Expose
        private String manifest_no;
        @SerializedName("dolly")
        @Expose
        private String dolly;
        @SerializedName("load_from")
        @Expose
        private String load_from;
        @SerializedName("load_due")
        @Expose
        private String load_due;
        @SerializedName("load_type")
        @Expose
        private String load_type;
        @SerializedName("created_date")
        @Expose
        private String created_date;
        @SerializedName("changeover")
        @Expose
        private String changeover;
        @SerializedName("admin_cmt")
        @Expose
        private String admin_cmt;
        @SerializedName("did")
        @Expose
        private String did;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public String getTruck() {
            return truck;
        }

        public void setTruck(String truck) {
            this.truck = truck;
        }

        public String getTrailers() {
            return trailers;
        }

        public void setTrailers(String trailers) {
            this.trailers = trailers;
        }

        public String getManifest_no() {
            return manifest_no;
        }

        public void setManifest_no(String manifest_no) {
            this.manifest_no = manifest_no;
        }

        public String getDolly() {
            return dolly;
        }

        public void setDolly(String dolly) {
            this.dolly = dolly;
        }

        public String getLoad_from() {
            return load_from;
        }

        public void setLoad_from(String load_from) {
            this.load_from = load_from;
        }

        public String getLoad_due() {
            return load_due;
        }

        public void setLoad_due(String load_due) {
            this.load_due = load_due;
        }

        public String getLoad_type() {
            return load_type;
        }

        public void setLoad_type(String load_type) {
            this.load_type = load_type;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getChangeover() {
            return changeover;
        }

        public void setChangeover(String changeover) {
            this.changeover = changeover;
        }

        public String getAdmin_cmt() {
            return admin_cmt;
        }

        public void setAdmin_cmt(String admin_cmt) {
            this.admin_cmt = admin_cmt;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

    }

    public class MLIsresponse {

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
