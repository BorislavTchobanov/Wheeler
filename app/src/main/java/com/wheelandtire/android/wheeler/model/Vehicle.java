package com.wheelandtire.android.wheeler.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("market")
    @Expose
    private Market market;
    @SerializedName("body")
    @Expose
    private Object body;
    @SerializedName("trim")
    @Expose
    private String trim;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("generation")
    @Expose
    private Generation generation;
    @SerializedName("stud_holes")
    @Expose
    private int studHoles;
    @SerializedName("pcd")
    @Expose
    private float pcd;
    @SerializedName("centre_bore")
    @Expose
    private float centreBore;
    @SerializedName("lock_type")
    @Expose
    private String lockType;
    @SerializedName("lock_text")
    @Expose
    private String lockText;
    @SerializedName("bolt_pattern")
    @Expose
    private String boltPattern;
    @SerializedName("power")
    @Expose
    private Power power;
    @SerializedName("engine_type")
    @Expose
    private String engineType;
    @SerializedName("fuel")
    @Expose
    private String fuel;
    @SerializedName("wheels")
    @Expose
    private List<Wheel> wheels = null;

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public int getStudHoles() {
        return studHoles;
    }

    public void setStudHoles(int studHoles) {
        this.studHoles = studHoles;
    }

    public float getPcd() {
        return pcd;
    }

    public void setPcd(float pcd) {
        this.pcd = pcd;
    }

    public float getCentreBore() {
        return centreBore;
    }

    public void setCentreBore(float centreBore) {
        this.centreBore = centreBore;
    }

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public String getLockText() {
        return lockText;
    }

    public void setLockText(String lockText) {
        this.lockText = lockText;
    }

    public String getBoltPattern() {
        return boltPattern;
    }

    public void setBoltPattern(String boltPattern) {
        this.boltPattern = boltPattern;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public List<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(List<Wheel> wheels) {
        this.wheels = wheels;
    }

}