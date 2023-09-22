package com.example.shakerapplication.ModelClasses;

public class Transfer_Posting_Model_Class {
    private String matnr;     // Material number
    private String werks;     // Plant code
    private String lgort;     // Storage location
    private String umlgo;        // Quantity in stock
    private String serialNo;  // Serial number (if applicable)


    public Transfer_Posting_Model_Class(String matnr, String werks, String lgort, String umlgo, String serialNo) {
        this.matnr = matnr;
        this.werks = werks;
        this.lgort = lgort;
        this.umlgo = umlgo;
        this.serialNo = serialNo;
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

    public String getUmlgo() {
        return umlgo;
    }

    public void setUmlgo(String umlgo) {
        this.umlgo = umlgo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
