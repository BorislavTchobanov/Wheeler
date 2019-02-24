package com.wheelandtire.android.wheeler.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Generation implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bodies")
    @Expose
    private List<Body> bodies = null;
    @SerializedName("start_year")
    @Expose
    private int startYear;
    @SerializedName("end_year")
    @Expose
    private int endYear;
    @SerializedName("years")
    @Expose
    private List<Integer> years = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

}