package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 15-02-2017.
 */
public class PaysheetLastWeekAPI {
    private static PaysheetLastWeekAPI ourInstance = new PaysheetLastWeekAPI();

    public static PaysheetLastWeekAPI getInstance() {
        return ourInstance;
    }

    private PaysheetLastWeekAPI() {
    }


    public class Datum {

        @SerializedName("pdate")
        @Expose
        private String pdate;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("pid")
        @Expose
        private String pid;
        @SerializedName("first_name")
        @Expose
        private String first_name;
        @SerializedName("last_name")
        @Expose
        private String last_name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mf_no")
        @Expose
        private String mf_no;
        @SerializedName("truck_no")
        @Expose
        private String truck_no;
        @SerializedName("tr1_no")
        @Expose
        private String tr1_no;
        @SerializedName("tr2_no")
        @Expose
        private String tr2_no;
        @SerializedName("tr3_no")
        @Expose
        private String tr3_no;
        @SerializedName("dolly_no")
        @Expose
        private String dolly_no;
        @SerializedName("rt_bd")
        @Expose
        private String rt_bd;
        @SerializedName("duty")
        @Expose
        private String duty;
        @SerializedName("start_km")
        @Expose
        private String start_km;
        @SerializedName("end_km")
        @Expose
        private String end_km;
        @SerializedName("inspection")
        @Expose
        private String inspection;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("unloading_time")
        @Expose
        private String unloading_time;
        @SerializedName("office_use")
        @Expose
        private String office_use;
        @SerializedName("uid")
        @Expose
        private String uid;

        public String getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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

        public String getMf_no() {
            return mf_no;
        }

        public void setMf_no(String mf_no) {
            this.mf_no = mf_no;
        }

        public String getTruck_no() {
            return truck_no;
        }

        public void setTruck_no(String truck_no) {
            this.truck_no = truck_no;
        }

        public String getTr1_no() {
            return tr1_no;
        }

        public void setTr1_no(String tr1_no) {
            this.tr1_no = tr1_no;
        }

        public String getTr2_no() {
            return tr2_no;
        }

        public void setTr2_no(String tr2_no) {
            this.tr2_no = tr2_no;
        }

        public String getTr3_no() {
            return tr3_no;
        }

        public void setTr3_no(String tr3_no) {
            this.tr3_no = tr3_no;
        }

        public String getDolly_no() {
            return dolly_no;
        }

        public void setDolly_no(String dolly_no) {
            this.dolly_no = dolly_no;
        }

        public String getRt_bd() {
            return rt_bd;
        }

        public void setRt_bd(String rt_bd) {
            this.rt_bd = rt_bd;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public String getStart_km() {
            return start_km;
        }

        public void setStart_km(String start_km) {
            this.start_km = start_km;
        }

        public String getEnd_km() {
            return end_km;
        }

        public void setEnd_km(String end_km) {
            this.end_km = end_km;
        }

        public String getInspection() {
            return inspection;
        }

        public void setInspection(String inspection) {
            this.inspection = inspection;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUnloading_time() {
            return unloading_time;
        }

        public void setUnloading_time(String unloading_time) {
            this.unloading_time = unloading_time;
        }

        public String getOffice_use() {
            return office_use;
        }

        public void setOffice_use(String office_use) {
            this.office_use = office_use;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

    }


    public class PaysheetLastWeekResponse {

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
