package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Load_Outbound_Delivery {
    @SerializedName("EBELN")
    private String EBELN;

    @SerializedName("EBELP")
    private String EBELP;

    @SerializedName("MATNR")
    private String MATNR;

    @SerializedName("WERKS")
    private String WERKS;

    @SerializedName("EAN11")
    private String EAN11;

    @SerializedName("LGORT")
    private String LGORT;

    @SerializedName("MENGE")
    private String MENGE;

    @SerializedName("MEINS")
    private String MEINS;

    @SerializedName("NETPR")
    private String NETPR;

    @SerializedName("NETWR")
    private String NETWR;

    @SerializedName("AEDAT")
    private String AEDAT;

    @SerializedName("RESWK")
    private String RESWK;

    @SerializedName("MAKTX")
    private String MAKTX;

    @SerializedName("SERIALNO")
    private String SERIALNO;

    @SerializedName("BWART")
    private String BWART;

    @SerializedName("GRUND")
    private String GRUND;

    @SerializedName("UMWRK")
    private String UMWRK;

    @SerializedName("UMLGO")
    private String UMLGO;

    @SerializedName("VBELN")
    private String VBELN;

    @SerializedName("POSNR")
    private String POSNR;

    @SerializedName("LFIMG")
    private String LFIMG;

    @SerializedName("SERNR")
    private String SERNR;

    @SerializedName("SERIAL_CHK")
    private String SERIAL_CHK;

    @SerializedName("NAME1")
    private String NAME1;

    @SerializedName("DELIV_TYPE")
    private String DELIV_TYPE;

    @SerializedName("WADAT")
    private String WADAT;


    @SerializedName("TOT_ITEMS")
    private String TOT_ITEMS;

    @SerializedName("TOT_QTY")
    private String TOT_QTY;


    public Load_Outbound_Delivery(String EBELN, String EBELP, String MATNR, String WERKS, String EAN11, String LGORT, String MENGE, String MEINS, String NETPR, String NETWR, String AEDAT, String RESWK, String MAKTX, String SERIALNO, String BWART, String GRUND, String UMWRK, String UMLGO, String VBELN, String POSNR, String LFIMG, String SERNR, String SERIAL_CHK, String NAME1, String DELIV_TYPE, String WADAT, String TOT_ITEMS, String TOT_QTY) {
        this.EBELN = EBELN;
        this.EBELP = EBELP;
        this.MATNR = MATNR;
        this.WERKS = WERKS;
        this.EAN11 = EAN11;
        this.LGORT = LGORT;
        this.MENGE = MENGE;
        this.MEINS = MEINS;
        this.NETPR = NETPR;
        this.NETWR = NETWR;
        this.AEDAT = AEDAT;
        this.RESWK = RESWK;
        this.MAKTX = MAKTX;
        this.SERIALNO = SERIALNO;
        this.BWART = BWART;
        this.GRUND = GRUND;
        this.UMWRK = UMWRK;
        this.UMLGO = UMLGO;
        this.VBELN = VBELN;
        this.POSNR = POSNR;
        this.LFIMG = LFIMG;
        this.SERNR = SERNR;
        this.SERIAL_CHK = SERIAL_CHK;
        this.NAME1 = NAME1;
        this.DELIV_TYPE = DELIV_TYPE;
        this.WADAT = WADAT;
        this.TOT_ITEMS = TOT_ITEMS;
        this.TOT_QTY = TOT_QTY;
    }

    public String getEBELN() {
        return EBELN;
    }

    public void setEBELN(String EBELN) {
        this.EBELN = EBELN;
    }

    public String getEBELP() {
        return EBELP;
    }

    public void setEBELP(String EBELP) {
        this.EBELP = EBELP;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getEAN11() {
        return EAN11;
    }

    public void setEAN11(String EAN11) {
        this.EAN11 = EAN11;
    }

    public String getLGORT() {
        return LGORT;
    }

    public void setLGORT(String LGORT) {
        this.LGORT = LGORT;
    }

    public String getMENGE() {
        return MENGE;
    }

    public void setMENGE(String MENGE) {
        this.MENGE = MENGE;
    }

    public String getMEINS() {
        return MEINS;
    }

    public void setMEINS(String MEINS) {
        this.MEINS = MEINS;
    }

    public String getNETPR() {
        return NETPR;
    }

    public void setNETPR(String NETPR) {
        this.NETPR = NETPR;
    }

    public String getNETWR() {
        return NETWR;
    }

    public void setNETWR(String NETWR) {
        this.NETWR = NETWR;
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

    public String getMAKTX() {
        return MAKTX;
    }

    public void setMAKTX(String MAKTX) {
        this.MAKTX = MAKTX;
    }

    public String getSERIALNO() {
        return SERIALNO;
    }

    public void setSERIALNO(String SERIALNO) {
        this.SERIALNO = SERIALNO;
    }

    public String getBWART() {
        return BWART;
    }

    public void setBWART(String BWART) {
        this.BWART = BWART;
    }

    public String getGRUND() {
        return GRUND;
    }

    public void setGRUND(String GRUND) {
        this.GRUND = GRUND;
    }

    public String getUMWRK() {
        return UMWRK;
    }

    public void setUMWRK(String UMWRK) {
        this.UMWRK = UMWRK;
    }

    public String getUMLGO() {
        return UMLGO;
    }

    public void setUMLGO(String UMLGO) {
        this.UMLGO = UMLGO;
    }

    public String getVBELN() {
        return VBELN;
    }

    public void setVBELN(String VBELN) {
        this.VBELN = VBELN;
    }

    public String getPOSNR() {
        return POSNR;
    }

    public void setPOSNR(String POSNR) {
        this.POSNR = POSNR;
    }

    public String getLFIMG() {
        return LFIMG;
    }

    public void setLFIMG(String LFIMG) {
        this.LFIMG = LFIMG;
    }

    public String getSERNR() {
        return SERNR;
    }

    public void setSERNR(String SERNR) {
        this.SERNR = SERNR;
    }


    public String getSERIAL_CHK() {
        return SERIAL_CHK;
    }

    public void setSERIAL_CHK(String SERIAL_CHK) {
        this.SERIAL_CHK = SERIAL_CHK;
    }

    public String getNAME1() {
        return NAME1;
    }

    public void setNAME1(String NAME1) {
        this.NAME1 = NAME1;
    }

    public String getDELIV_TYPE() {
        return DELIV_TYPE;
    }

    public void setDELIV_TYPE(String DELIV_TYPE) {
        this.DELIV_TYPE = DELIV_TYPE;
    }

    public String getWADAT() {
        return WADAT;
    }

    public void setWADAT(String WADAT) {
        this.WADAT = WADAT;
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


