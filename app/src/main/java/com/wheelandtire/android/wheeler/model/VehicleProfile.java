package com.wheelandtire.android.wheeler.model;


import java.io.Serializable;

public class VehicleProfile implements Serializable {

    private String profileName;
    private String profileMake;
    private String profileModel;
    private String profileYear;
    private String profileTireWidth;
    private String profileTireHeight;
    private String profileTireAndRimDiameter;
    private String profileRimWidth;
    private String profileRimOffset;


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
}