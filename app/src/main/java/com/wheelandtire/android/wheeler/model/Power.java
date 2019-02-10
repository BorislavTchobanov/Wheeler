package com.wheelandtire.android.wheeler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Power {

    @SerializedName("PS")
    @Expose
    private float pS;
    @SerializedName("hp")
    @Expose
    private float hp;
    @SerializedName("kW")
    @Expose
    private float kW;

    public float getPS() {
        return pS;
    }

    public void setPS(float pS) {
        this.pS = pS;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getKW() {
        return kW;
    }

    public void setKW(float kW) {
        this.kW = kW;
    }

}