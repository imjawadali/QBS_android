package com.example.shakerapplication.ModelClasses;

public class Floor_Audit_Model_Class {

    private String SERIAL_NUMBER;
    private String MODELNO;
    private String MATNR;
    private String LGORT;
    private String WERKS;
    private String ZUSER;
    private String AUDIT_DATE;
    private String AUDIT_TIME;


    public Floor_Audit_Model_Class(String SERIAL_NUMBER, String MODELNO, String MATNR, String LGORT, String WERKS, String ZUSER, String AUDIT_DATE, String AUDIT_TIME) {
        this.SERIAL_NUMBER = SERIAL_NUMBER;
        this.MODELNO = MODELNO;
        this.MATNR = MATNR;
        this.LGORT = LGORT;
        this.WERKS = WERKS;
        this.ZUSER = ZUSER;
        this.AUDIT_DATE = AUDIT_DATE;
        this.AUDIT_TIME = AUDIT_TIME;
    }


    public String getSERIAL_NUMBER() {
        return SERIAL_NUMBER;
    }

    public void setSERIAL_NUMBER(String SERIAL_NUMBER) {
        this.SERIAL_NUMBER = SERIAL_NUMBER;
    }

    public String getMODELNO() {
        return MODELNO;
    }

    public void setMODELNO(String MODELNO) {
        this.MODELNO = MODELNO;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getLGORT() {
        return LGORT;
    }

    public void setLGORT(String LGORT) {
        this.LGORT = LGORT;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getZUSER() {
        return ZUSER;
    }

    public void setZUSER(String ZUSER) {
        this.ZUSER = ZUSER;
    }

    public String getAUDIT_DATE() {
        return AUDIT_DATE;
    }

    public void setAUDIT_DATE(String AUDIT_DATE) {
        this.AUDIT_DATE = AUDIT_DATE;
    }

    public String getAUDIT_TIME() {
        return AUDIT_TIME;
    }

    public void setAUDIT_TIME(String AUDIT_TIME) {
        this.AUDIT_TIME = AUDIT_TIME;
    }
}
