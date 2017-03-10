package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 08-03-2017.
 */
public class TyreRepairAPI {
    private static TyreRepairAPI ourInstance = new TyreRepairAPI();

    public static TyreRepairAPI getInstance() {
        return ourInstance;
    }

    private TyreRepairAPI() {
    }

    public class Datum implements Serializable {


        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("issue")
        @Expose
        private String issue;
        @SerializedName("destination")
        @Expose
        private String destination;
        @SerializedName("truck")
        @Expose
        private String truck;
        @SerializedName("trailer")
        @Expose
        private String trailer;
        @SerializedName("dolly")
        @Expose
        private String dolly;
        @SerializedName("mech_id")
        @Expose
        private String mech_id;
        @SerializedName("mech_issues")
        @Expose
        private String mech_issues;
        @SerializedName("mech_comts")
        @Expose
        private String mech_comts;
        @SerializedName("approval")
        @Expose
        private String approval;
        @SerializedName("created_date")
        @Expose
        private String created_date;
        @SerializedName("mech_udate")
        @Expose
        private String mech_udate;
        @SerializedName("hdmech_id")
        @Expose
        private String hdmech_id;
        @SerializedName("hdmech_cmts")
        @Expose
        private String hdmech_cmts;
        @SerializedName("hdmech_udate")
        @Expose
        private String hdmech_udate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getTruck() {
            return truck;
        }

        public void setTruck(String truck) {
            this.truck = truck;
        }

        public String getTrailer() {
            return trailer;
        }

        public void setTrailer(String trailer) {
            this.trailer = trailer;
        }

        public String getDolly() {
            return dolly;
        }

        public void setDolly(String dolly) {
            this.dolly = dolly;
        }

        public String getMech_id() {
            return mech_id;
        }

        public void setMech_id(String mech_id) {
            this.mech_id = mech_id;
        }

        public String getMech_issues() {
            return mech_issues;
        }

        public void setMech_issues(String mech_issues) {
            this.mech_issues = mech_issues;
        }

        public String getMech_comts() {
            return mech_comts;
        }

        public void setMech_comts(String mech_comts) {
            this.mech_comts = mech_comts;
        }

        public String getApproval() {
            return approval;
        }

        public void setApproval(String approval) {
            this.approval = approval;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getMech_udate() {
            return mech_udate;
        }

        public void setMech_udate(String mech_udate) {
            this.mech_udate = mech_udate;
        }

        public String getHdmech_id() {
            return hdmech_id;
        }

        public void setHdmech_id(String hdmech_id) {
            this.hdmech_id = hdmech_id;
        }

        public String getHdmech_cmts() {
            return hdmech_cmts;
        }

        public void setHdmech_cmts(String hdmech_cmts) {
            this.hdmech_cmts = hdmech_cmts;
        }

        public String getHdmech_udate() {
            return hdmech_udate;
        }

        public void setHdmech_udate(String hdmech_udate) {
            this.hdmech_udate = hdmech_udate;
        }

    }

    public class TyrerepairResponse {

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
