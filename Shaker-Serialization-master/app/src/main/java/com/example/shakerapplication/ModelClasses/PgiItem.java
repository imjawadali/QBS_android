package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class PgiItem {
    @SerializedName("RFPOS")
    private String posnr;

    @SerializedName("SERNR")
    private String sernr;


    @SerializedName("UII")
    private String uii;


    public PgiItem(String posnr, String sernr, String uii) {
        this.posnr = posnr;
        this.sernr = sernr;
        this.uii = uii;
    }

    public String getPosnr() {
        return posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    public String getSernr() {
        return sernr;
    }

    public void setSernr(String sernr) {
        this.sernr = sernr;
    }

}
