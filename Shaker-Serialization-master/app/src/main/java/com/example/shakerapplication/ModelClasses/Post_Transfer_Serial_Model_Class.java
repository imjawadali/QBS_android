package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class Post_Transfer_Serial_Model_Class {
    @SerializedName("MATNR")
    private String MATNR;

    @SerializedName("WERKS")
    private String WERKS;

    @SerializedName("LGORT")
    private String LGORT;

    @SerializedName("UMLGO")
    private String UMLGO;

    @SerializedName("SERIALNO")
    private String SERIALNO;




    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }



    public Post_Transfer_Serial_Model_Class(String MATNR, String WERKS, String LGORT, String UMLGO, String SERIALNO) {

        this.MATNR = MATNR;
        this.WERKS = WERKS;
        this.LGORT = LGORT;
        this.UMLGO = UMLGO;
        this.SERIALNO = SERIALNO;
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

    public String getUMLGO() {
        return UMLGO;
    }

    public void setUMLGO(String UMLGO) {
        this.UMLGO = UMLGO;
    }

    public String getSERIALNO() {
        return SERIALNO;
    }

    public void setSERIALNO(String SERIALNO) {
        this.SERIALNO = SERIALNO;
    }
}
