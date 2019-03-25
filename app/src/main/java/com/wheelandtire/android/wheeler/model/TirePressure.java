package com.wheelandtire.android.wheeler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TirePressure implements Serializable {

    @SerializedName("kPa")
    @Expose
    private float kPa;
    @SerializedName("psi")
    @Expose
    private float psi;
    @SerializedName("bar")
    @Expose
    private float bar;

    public float getKPa() {
        return kPa;
    }

    public void setKPa(float kPa) {
        this.kPa = kPa;
    }

    public float getPsi() {
        return psi;
    }

    public void setPsi(float psi) {
        this.psi = psi;
    }

    public float getBar() {
        return bar;
    }

    public void setBar(float bar) {
        this.bar = bar;
    }

}