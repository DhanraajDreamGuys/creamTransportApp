package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 03-03-2017.
 */
public class AppsettingAPI {
    private static AppsettingAPI ourInstance = new AppsettingAPI();

    public static AppsettingAPI getInstance() {
        return ourInstance;
    }

    private AppsettingAPI() {
    }


    public class AppsettingsResponse {

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

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("bgimage")
        @Expose
        private String bgimage;
        @SerializedName("splash")
        @Expose
        private String splash;
        @SerializedName("splashtext")
        @Expose
        private String splashtext;
        @SerializedName("description")
        @Expose
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBgimage() {
            return bgimage;
        }

        public void setBgimage(String bgimage) {
            this.bgimage = bgimage;
        }

        public String getSplash() {
            return splash;
        }

        public void setSplash(String splash) {
            this.splash = splash;
        }

        public String getSplashtext() {
            return splashtext;
        }

        public void setSplashtext(String splashtext) {
            this.splashtext = splashtext;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
