package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Goods_Receipt_Serial_Sto_Item_ModelClass {
    @SerializedName("EBELN")
    private String ebeln;

    @SerializedName("EBELP")
    private String ebelp;

    @SerializedName("MATNR")
    private String matnr;

    @SerializedName("WERKS")
    private String werks;

    @SerializedName("EAN11")
    private String ean11;

    @SerializedName("LGORT")
    private String lgort;

    @SerializedName("MENGE")
    private String menge;

    @SerializedName("MEINS")
    private String meins;

    @SerializedName("NETPR")
    private String netpr;

    @SerializedName("NETWR")
    private String netwr;

    @SerializedName("AEDAT")
    private String aedat;

    @SerializedName("RESWK")
    private String reswk;

    @SerializedName("MAKTX")
    private String maktx;

    @SerializedName("SERIALNO")
    private String serialno;

    @SerializedName("BWART")
    private String bwart;

    @SerializedName("GRUND")
    private String grund;

    @SerializedName("UMWRK")
    private String umwrk;

    @SerializedName("UMLGO")
    private String umlgo;

    @SerializedName("VBELN")
    private String vbeln;

    @SerializedName("POSNR")
    private String posnr;

    @SerializedName("LFIMG")
    private String lfimg;

    @SerializedName("SERNR")
    private String sernr;

    @SerializedName("GI_QTY")
    private String gi_qty;


    @SerializedName("STO_TYP")
    private String sto_typ;


    public Goods_Receipt_Serial_Sto_Item_ModelClass(String ebeln, String ebelp, String matnr, String werks, String ean11, String lgort, String menge, String meins, String netpr, String netwr, String aedat, String reswk, String maktx, String serialno, String bwart, String grund, String umwrk, String umlgo, String vbeln, String posnr, String lfimg, String sernr, String gi_qty, String sto_typ) {
        this.ebeln = ebeln;
        this.ebelp = ebelp;
        this.matnr = matnr;
        this.werks = werks;
        this.ean11 = ean11;
        this.lgort = lgort;
        this.menge = menge;
        this.meins = meins;
        this.netpr = netpr;
        this.netwr = netwr;
        this.aedat = aedat;
        this.reswk = reswk;
        this.maktx = maktx;
        this.serialno = serialno;
        this.bwart = bwart;
        this.grund = grund;
        this.umwrk = umwrk;
        this.umlgo = umlgo;
        this.vbeln = vbeln;
        this.posnr = posnr;
        this.lfimg = lfimg;
        this.sernr = sernr;
        this.gi_qty = gi_qty;
        this.sto_typ = sto_typ;
    }

    public String getGi_qty() {
        return gi_qty;
    }

    public void setGi_qty(String gi_qty) {
        this.gi_qty = gi_qty;
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

    public String getEan11() {
        return ean11;
    }

    public void setEan11(String ean11) {
        this.ean11 = ean11;
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

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getNetpr() {
        return netpr;
    }

    public void setNetpr(String netpr) {
        this.netpr = netpr;
    }

    public String getNetwr() {
        return netwr;
    }

    public void setNetwr(String netwr) {
        this.netwr = netwr;
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

    public String getBwart() {
        return bwart;
    }

    public void setBwart(String bwart) {
        this.bwart = bwart;
    }

    public String getGrund() {
        return grund;
    }

    public void setGrund(String grund) {
        this.grund = grund;
    }

    public String getUmwrk() {
        return umwrk;
    }

    public void setUmwrk(String umwrk) {
        this.umwrk = umwrk;
    }

    public String getUmlgo() {
        return umlgo;
    }

    public void setUmlgo(String umlgo) {
        this.umlgo = umlgo;
    }

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public String getPosnr() {
        return posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    public String getLfimg() {
        return lfimg;
    }

    public void setLfimg(String lfimg) {
        this.lfimg = lfimg;
    }

    public String getSernr() {
        return sernr;
    }

    public void setSernr(String sernr) {
        this.sernr = sernr;
    }

    public String getSto_typ() {
        return sto_typ;
    }

    public void setSto_typ(String sto_typ) {
        this.sto_typ = sto_typ;
    }
}
