package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dhanraaj on 2/21/2017.
 */
public class DashboardAPI {
    private static DashboardAPI ourInstance = new DashboardAPI();

    public static DashboardAPI getInstance() {
        return ourInstance;
    }

    private DashboardAPI() {
    }

    public class DashboardResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("meta")
        @Expose
        private Integer meta;
        @SerializedName("data")
        @Expose
        private Data data;
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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }


    public class Data {

        @SerializedName("user_list")
        @Expose
        private Integer user_list;
        @SerializedName("paysheet_list")
        @Expose
        private Integer paysheet_list;

        public Integer getUser_list() {
            return user_list;
        }

        public void setUser_list(Integer user_list) {
            this.user_list = user_list;
        }

        public Integer getPaysheet_list() {
            return paysheet_list;
        }

        public void setPaysheet_list(Integer paysheet_list) {
            this.paysheet_list = paysheet_list;
        }

    }
}
