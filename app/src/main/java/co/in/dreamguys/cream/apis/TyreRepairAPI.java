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

        @SerializedName("tid")
        @Expose
        private String tid;
        @SerializedName("truck")
        @Expose
        private String truck;
        @SerializedName("trailer")
        @Expose
        private String trailer;
        @SerializedName("dolly")
        @Expose
        private String dolly;
        @SerializedName("issue")
        @Expose
        private String issue;
        @SerializedName("mech_issues")
        @Expose
        private String mech_issues;
        @SerializedName("approval")
        @Expose
        private String approval;
        @SerializedName("pname")
        @Expose
        private String pname;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("cdate")
        @Expose
        private String cdate;
        @SerializedName("mech_comts")
        @Expose
        private String mech_comts;
        @SerializedName("hdmech_cmts")
        @Expose
        private String hdmech_cmts;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
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

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getMech_issues() {
            return mech_issues;
        }

        public void setMech_issues(String mech_issues) {
            this.mech_issues = mech_issues;
        }

        public String getApproval() {
            return approval;
        }

        public void setApproval(String approval) {
            this.approval = approval;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
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

        public String getCdate() {
            return cdate;
        }

        public void setCdate(String cdate) {
            this.cdate = cdate;
        }

        public String getMech_comts() {
            return mech_comts;
        }

        public void setMech_comts(String mech_comts) {
            this.mech_comts = mech_comts;
        }

        public String getHdmech_cmts() {
            return hdmech_cmts;
        }

        public void setHdmech_cmts(String hdmech_cmts) {
            this.hdmech_cmts = hdmech_cmts;
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
