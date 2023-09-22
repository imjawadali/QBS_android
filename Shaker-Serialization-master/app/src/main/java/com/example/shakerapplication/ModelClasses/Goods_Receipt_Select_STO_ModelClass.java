package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Goods_Receipt_Select_STO_ModelClass {
    @SerializedName("EBELN")
    private String EBELN;

    @SerializedName("AEDAT")
    private String AEDAT;

    @SerializedName("RESWK")
    private String RESWK;

    @SerializedName("TOT_ITEMS")
    private String TOT_ITEMS;

    @SerializedName("TOT_QTY")
    private String TOT_QTY;


    public Goods_Receipt_Select_STO_ModelClass(String EBELN, String AEDAT, String RESWK, String TOT_ITEMS, String TOT_QTY) {
        this.EBELN = EBELN;
        this.AEDAT = AEDAT;
        this.RESWK = RESWK;
        this.TOT_ITEMS = TOT_ITEMS;
        this.TOT_QTY = TOT_QTY;
    }

    public String getAEDAT() {
        return AEDAT;
    }

    public void setAEDAT(String AEDAT) {
        this.AEDAT = AEDAT;
    }

    public String getRESWK() {
        return RESWK;
    }

    public void setRESWK(String RESWK) {
        this.RESWK = RESWK;
    }

    public String getEBELN() {
        return EBELN;
    }

    public void setEBELN(String EBELN) {
        this.EBELN = EBELN;
    }


    public String getTOT_ITEMS() {
        return TOT_ITEMS;
    }

    public void setTOT_ITEMS(String TOT_ITEMS) {
        this.TOT_ITEMS = TOT_ITEMS;
    }

    public String getTOT_QTY() {
        return TOT_QTY;
    }

    public void setTOT_QTY(String TOT_QTY) {
        this.TOT_QTY = TOT_QTY;
    }
}

