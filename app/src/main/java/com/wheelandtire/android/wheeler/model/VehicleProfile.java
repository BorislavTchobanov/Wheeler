package com.wheelandtire.android.wheeler.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.wheelandtire.android.wheeler.utility.VehicleListConverter;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "vehicleProfile")
public class VehicleProfile implements Serializable {

    @PrimaryKey
    private int id;
    private String profileName;
    private String profileMake;
    private String profileModel;
    private String profileYear;
    private String profileTrim;
    private String profileTireWidth;
    private String profileTireHeight;
    private String profileTireAndRimDiameter;
    private String profileRimWidth;
    private String profileRimOffset;
    @TypeConverters(VehicleListConverter.class)
    private List<Vehicle> vehicleList;

    public VehicleProfile(int id, String profileName, String profileMake, String profileModel, String profileYear,
                          String profileTrim, String profileTireWidth, String profileTireHeight,
                          String profileTireAndRimDiameter, String profileRimWidth, String profileRimOffset,
                          List<Vehicle> vehicleList) {
        this.id = id;
        this.profileName = profileName;
        this.profileMake = profileMake;
        this.profileModel = profileModel;
        this.profileYear = profileYear;
        this.profileTrim = profileTrim;
        this.profileTireWidth = profileTireWidth;
        this.profileTireHeight = profileTireHeight;
        this.profileTireAndRimDiameter = profileTireAndRimDiameter;
        this.profileRimWidth = profileRimWidth;
        this.profileRimOffset = profileRimOffset;
        this.vehicleList = vehicleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileMake() {
        return profileMake;
    }

    public void setProfileMake(String profileMake) {
        this.profileMake = profileMake;
    }

    public String getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(String profileModel) {
        this.profileModel = profileModel;
    }

    public String getProfileYear() {
        return profileYear;
    }

    public void setProfileYear(String profileYear) {
        this.profileYear = profileYear;
    }

    public String getProfileTrim() {
        return profileTrim;
    }

    public void setProfileTrim(String profileTrim) {
        this.profileTrim = profileTrim;
    }

    public String getProfileTireWidth() {
        return profileTireWidth;
    }

    public void setProfileTireWidth(String profileTireWidth) {
        this.profileTireWidth = profileTireWidth;
    }

    public String getProfileTireHeight() {
        return profileTireHeight;
    }

    public void setProfileTireHeight(String profileTireHeight) {
        this.profileTireHeight = profileTireHeight;
    }

    public String getProfileTireAndRimDiameter() {
        return profileTireAndRimDiameter;
    }

    public void setProfileTireAndRimDiameter(String profileTireAndRimDiameter) {
        this.profileTireAndRimDiameter = profileTireAndRimDiameter;
    }

    public String getProfileRimWidth() {
        return profileRimWidth;
    }

    public void setProfileRimWidth(String profileRimWidth) {
        this.profileRimWidth = profileRimWidth;
    }

    public String getProfileRimOffset() {
        return profileRimOffset;
    }

    public void setProfileRimOffset(String profileRimOffset) {
        this.profileRimOffset = profileRimOffset;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}