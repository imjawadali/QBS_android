package com.example.shakerapplication.ModelClasses;

import java.io.Serializable;

public class Transfer_GI_Model_Class implements Serializable {
    private String vbeln, posnr, sernr, material, materialdescript, seq_num, UII;

    public String getUII() {
        return UII;
    }

    public void setUII(String UII) {
        this.UII = UII;
    }

    public Transfer_GI_Model_Class(String vbeln, String posnr, String sernr, String material, String materialdescript, String seq_num, String UII) {
        this.vbeln = vbeln;
        this.posnr = posnr;
        this.sernr = sernr;
        this.material = material;
        this.materialdescript = materialdescript;
        this.seq_num = seq_num;
        this.UII = UII;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialdescript() {
        return materialdescript;
    }

    public void setMaterialdescript(String materialdescript) {
        this.materialdescript = materialdescript;
    }

    public String getSeq_num() {
        return seq_num;
    }

    public void setSeq_num(String seq_num) {
        this.seq_num = seq_num;
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

    public String getSernr() {
        return sernr;
    }

    public void setSernr(String sernr) {
        this.sernr = sernr;
    }
}
