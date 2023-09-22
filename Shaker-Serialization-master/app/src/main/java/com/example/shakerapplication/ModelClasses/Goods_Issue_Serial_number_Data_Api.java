package com.example.shakerapplication.ModelClasses;

public class Goods_Issue_Serial_number_Data_Api {
    String SERNR;
    String MATNR;
    String EAN11;

    public Goods_Issue_Serial_number_Data_Api(String SERNR, String MATNR, String EAN11) {
        this.SERNR = SERNR;
        this.MATNR = MATNR;
        this.EAN11 = EAN11;
    }

    public String getSERNR() {
        return SERNR;
    }

    public void setSERNR(String SERNR) {
        this.SERNR = SERNR;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getEAN11() {
        return EAN11;
    }

    public void setEAN11(String EAN11) {
        this.EAN11 = EAN11;
    }
}
