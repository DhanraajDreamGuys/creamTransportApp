
package co.in.dreamguys.cream.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("delete_sts")
        @Expose
        private String deleteSts;
        @SerializedName("cdate")
        @Expose
        private String cdate;
        @SerializedName("date")
        @Expose
        private String date;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeleteSts() {
            return deleteSts;
        }

        public void setDeleteSts(String deleteSts) {
            this.deleteSts = deleteSts;
        }

        public String getCdate() {
            return cdate;
        }

        public void setCdate(String cdate) {
            this.cdate = cdate;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }