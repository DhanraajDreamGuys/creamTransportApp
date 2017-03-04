package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user5 on 03-03-2017.
 */
public class PrintURLAPI {
    private static PrintURLAPI ourInstance = new PrintURLAPI();

    public static PrintURLAPI getInstance() {
        return ourInstance;
    }

    private PrintURLAPI() {
    }

    public class Data {

        @SerializedName("link")
        @Expose
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

    }


    public class PrintURLResponse {

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
}
