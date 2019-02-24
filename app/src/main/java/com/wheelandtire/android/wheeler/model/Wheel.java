package com.wheelandtire.android.wheeler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wheel implements Serializable {

    @SerializedName("showing_fp_only")
    @Expose
    private boolean showingFpOnly;
    @SerializedName("is_stock")
    @Expose
    private boolean isStock;
    @SerializedName("front")
    @Expose
    private Front front;
    @SerializedName("rear")
    @Expose
    private Rear rear;

    public boolean isShowingFpOnly() {
        return showingFpOnly;
    }

    public void setShowingFpOnly(boolean showingFpOnly) {
        this.showingFpOnly = showingFpOnly;
    }

    public boolean isIsStock() {
        return isStock;
    }

    public void setIsStock(boolean isStock) {
        this.isStock = isStock;
    }

    public Front getFront() {
        return front;
    }

    public void setFront(Front front) {
        this.front = front;
    }

    public Rear getRear() {
        return rear;
    }

    public void setRear(Rear rear) {
        this.rear = rear;
    }

}