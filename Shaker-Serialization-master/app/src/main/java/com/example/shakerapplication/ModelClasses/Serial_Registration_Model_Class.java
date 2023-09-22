package com.example.shakerapplication.ModelClasses;

public class Serial_Registration_Model_Class {
    private String matnr;
    private String barcode;
    private String plant;
    private String stloc;
    private String serial;

    public Serial_Registration_Model_Class(String matnr, String barcode, String plant, String stloc, String serial) {
        this.matnr = matnr;
        this.barcode = barcode;
        this.plant = plant;
        this.stloc = stloc;
        this.serial = serial;
    }


    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getStloc() {
        return stloc;
    }

    public void setStloc(String stloc) {
        this.stloc = stloc;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
