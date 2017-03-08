package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user5 on 06-03-2017.
 */
public class PBTruckAPI {
    private static PBTruckAPI ourInstance = new PBTruckAPI();

    public static PBTruckAPI getInstance() {
        return ourInstance;
    }

    private PBTruckAPI() {
    }

    public class Datum implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("truck_no")
        @Expose
        private String truck_no;
        @SerializedName("phone_no")
        @Expose
        private String phone_no;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTruck_no() {
            return truck_no;
        }

        public void setTruck_no(String truck_no) {
            this.truck_no = truck_no;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class PBTruckListResponse {

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
