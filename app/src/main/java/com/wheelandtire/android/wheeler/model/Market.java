package com.wheelandtire.android.wheeler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Market {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("abbr")
    @Expose
    private String abbr;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_en")
    @Expose
    private String nameEn;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

}