package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Post_GRN_Model_Class {
    @SerializedName("EBELN")
    private String ebeln;

    @SerializedName("EBELP")
    private String ebelp;

    @SerializedName("MATNR")
    private String matnr;

    @SerializedName("WERKS")
    private String werks;

    @SerializedName("LGORT")
    private String lgort;

    @SerializedName("MENGE")
    private String menge;

    @SerializedName("AEDAT")
    private String aedat;

    @SerializedName("RESWK")
    private String reswk;

    @SerializedName("MAKTX")
    private String maktx;

    @SerializedName("SERIALNO")
    private String serialno;


    public Post_GRN_Model_Class(String ebeln, String ebelp, String matnr, String werks, String lgort, String menge, String aedat, String reswk, String maktx, String serialno) {
        this.ebeln = ebeln;
        this.ebelp = ebelp;
        this.matnr = matnr;
        this.werks = werks;
        this.lgort = lgort;
        this.menge = menge;
        this.aedat = aedat;
        this.reswk = reswk;
        this.maktx = maktx;
        this.serialno = serialno;
    }


    public String getEbeln() {
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    public String getEbelp() {
        return ebelp;
    }

    public void setEbelp(String ebelp) {
        this.ebelp = ebelp;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    public String getMenge() {
        return menge;
    }

    public void setMenge(String menge) {
        this.menge = menge;
    }

    public String getAedat() {
        return aedat;
    }

    public void setAedat(String aedat) {
        this.aedat = aedat;
    }

    public String getReswk() {
        return reswk;
    }

    public void setReswk(String reswk) {
        this.reswk = reswk;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
}
