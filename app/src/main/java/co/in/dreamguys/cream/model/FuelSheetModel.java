package co.in.dreamguys.cream.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user5 on 25-02-2017.
 */

public class FuelSheetModel implements Serializable{


    @SerializedName("fdate")
    @Expose
    private String fdate;
    @SerializedName("truck_no")
    @Expose
    private String truckNo;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("diesel")
    @Expose
    private String diesel;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fid")
    @Expose
    private String fid;
    @SerializedName("trailer_no")
    @Expose
    private String trailerNo;
    @SerializedName("trl_diesel")
    @Expose
    private String trlDiesel;
    @SerializedName("trl_oil")
    @Expose
    private String trlOil;
    @SerializedName("cur_km")
    @Expose
    private String curKm;
    @SerializedName("pre_km")
    @Expose
    private String preKm;
    @SerializedName("oil")
    @Expose
    private String oil;

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDiesel() {
        return diesel;
    }

    public void setDiesel(String diesel) {
        this.diesel = diesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTrailerNo() {
        return trailerNo;
    }

    public void setTrailerNo(String trailerNo) {
        this.trailerNo = trailerNo;
    }

    public String getTrlDiesel() {
        return trlDiesel;
    }

    public void setTrlDiesel(String trlDiesel) {
        this.trlDiesel = trlDiesel;
    }

    public String getTrlOil() {
        return trlOil;
    }

    public void setTrlOil(String trlOil) {
        this.trlOil = trlOil;
    }

    public String getCurKm() {
        return curKm;
    }

    public void setCurKm(String curKm) {
        this.curKm = curKm;
    }

    public String getPreKm() {
        return preKm;
    }

    public void setPreKm(String preKm) {
        this.preKm = preKm;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }


}
