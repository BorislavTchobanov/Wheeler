package com.wheelandtire.android.wheeler.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Front implements Serializable {

    @SerializedName("tire_pressure")
    @Expose
    private TirePressure tirePressure;
    @SerializedName("rim")
    @Expose
    private String rim;
    @SerializedName("rim_diameter")
    @Expose
    private float rimDiameter;
    @SerializedName("rim_width")
    @Expose
    private float rimWidth;
    @SerializedName("rim_offset")
    @Expose
    private float rimOffset;
    @SerializedName("tire")
    @Expose
    private String tire;
    @SerializedName("tire_sizing_system")
    @Expose
    private String tireSizingSystem;
    @SerializedName("tire_construction")
    @Expose
    private String tireConstruction;
    @SerializedName("tire_width")
    @Expose
    private float tireWidth;
    @SerializedName("tire_aspect_ratio")
    @Expose
    private float tireAspectRatio;
    @SerializedName("tire_diameter")
    @Expose
    private Object tireDiameter;
    @SerializedName("tire_section_width")
    @Expose
    private Object tireSectionWidth;
    @SerializedName("tire_is_82series")
    @Expose
    private boolean tireIs82series;
    @SerializedName("load_index")
    @Expose
    private int loadIndex;
    @SerializedName("speed_index")
    @Expose
    private String speedIndex;

    public TirePressure getTirePressure() {
        return tirePressure;
    }

    public void setTirePressure(TirePressure tirePressure) {
        this.tirePressure = tirePressure;
    }

    public String getRim() {
        return rim;
    }

    public void setRim(String rim) {
        this.rim = rim;
    }

    public float getRimDiameter() {
        return rimDiameter;
    }

    public void setRimDiameter(float rimDiameter) {
        this.rimDiameter = rimDiameter;
    }

    public float getRimWidth() {
        return rimWidth;
    }

    public void setRimWidth(float rimWidth) {
        this.rimWidth = rimWidth;
    }

    public float getRimOffset() {
        return rimOffset;
    }

    public void setRimOffset(float rimOffset) {
        this.rimOffset = rimOffset;
    }

    public String getTire() {
        return tire;
    }

    public void setTire(String tire) {
        this.tire = tire;
    }

    public String getTireSizingSystem() {
        return tireSizingSystem;
    }

    public void setTireSizingSystem(String tireSizingSystem) {
        this.tireSizingSystem = tireSizingSystem;
    }

    public String getTireConstruction() {
        return tireConstruction;
    }

    public void setTireConstruction(String tireConstruction) {
        this.tireConstruction = tireConstruction;
    }

    public float getTireWidth() {
        return tireWidth;
    }

    public void setTireWidth(float tireWidth) {
        this.tireWidth = tireWidth;
    }

    public float getTireAspectRatio() {
        return tireAspectRatio;
    }

    public void setTireAspectRatio(float tireAspectRatio) {
        this.tireAspectRatio = tireAspectRatio;
    }

    public Object getTireDiameter() {
        return tireDiameter;
    }

    public void setTireDiameter(Object tireDiameter) {
        this.tireDiameter = tireDiameter;
    }

    public Object getTireSectionWidth() {
        return tireSectionWidth;
    }

    public void setTireSectionWidth(Object tireSectionWidth) {
        this.tireSectionWidth = tireSectionWidth;
    }

    public boolean isTireIs82series() {
        return tireIs82series;
    }

    public void setTireIs82series(boolean tireIs82series) {
        this.tireIs82series = tireIs82series;
    }

    public int getLoadIndex() {
        return loadIndex;
    }

    public void setLoadIndex(int loadIndex) {
        this.loadIndex = loadIndex;
    }

    public String getSpeedIndex() {
        return speedIndex;
    }

    public void setSpeedIndex(String speedIndex) {
        this.speedIndex = speedIndex;
    }

}