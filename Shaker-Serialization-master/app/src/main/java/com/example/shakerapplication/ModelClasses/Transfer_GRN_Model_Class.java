package com.example.shakerapplication.ModelClasses;

import java.io.Serializable;

public class Transfer_GRN_Model_Class implements Serializable {

private String EBELN;
private String EBELP;
private String MATNR;
private String WERKS;
private String LGORT;
private String MENGE;
private String AEDAT;
private String RESWK;
private String MAKTX;
private String SERIALNO;

    public Transfer_GRN_Model_Class(String EBELN, String EBELP, String MATNR, String WERKS, String LGORT, String MENGE, String AEDAT, String RESWK, String MAKTX, String SERIALNO) {
        this.EBELN = EBELN;
        this.EBELP = EBELP;
        this.MATNR = MATNR;
        this.WERKS = WERKS;
        this.LGORT = LGORT;
        this.MENGE = MENGE;
        this.AEDAT = AEDAT;
        this.RESWK = RESWK;
        this.MAKTX = MAKTX;
        this.SERIALNO = SERIALNO;
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

}
