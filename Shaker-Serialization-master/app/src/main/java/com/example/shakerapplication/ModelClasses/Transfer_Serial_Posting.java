package com.example.shakerapplication.ModelClasses;

import java.io.Serializable;

public class Transfer_Serial_Posting implements Serializable {
    private String MATNR,
    WERKS,
    LGORT,
    SERIALNO;

    public Transfer_Serial_Posting(String MATNR, String WERKS, String LGORT,  String SERIALNO) {
        this.MATNR = MATNR;
        this.WERKS = WERKS;
        this.LGORT = LGORT;
        this.SERIALNO = SERIALNO;
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


    public String getSERIALNO() {
        return SERIALNO;
    }

    public void setSERIALNO(String SERIALNO) {
        this.SERIALNO = SERIALNO;
    }
}
