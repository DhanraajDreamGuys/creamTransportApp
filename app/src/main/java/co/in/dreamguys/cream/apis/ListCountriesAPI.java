package co.in.dreamguys.cream.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user5 on 22-02-2017.
 */
public class ListCountriesAPI {
    private static ListCountriesAPI ourInstance = new ListCountriesAPI();

    public static ListCountriesAPI getInstance() {
        return ourInstance;
    }

    private ListCountriesAPI() {
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("continent")
        @Expose
        private String continent;
        @SerializedName("phone_prefix")
        @Expose
        private String phone_prefix;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
            this.continent = continent;
        }

        public String getPhone_prefix() {
            return phone_prefix;
        }

        public void setPhone_prefix(String phone_prefix) {
            this.phone_prefix = phone_prefix;
        }

    }

    public class CountryListResponse {

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
