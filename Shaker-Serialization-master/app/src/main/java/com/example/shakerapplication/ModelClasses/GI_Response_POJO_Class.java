package com.example.shakerapplication.ModelClasses;

public class GI_Response_POJO_Class {
    private String CHARG;
    private String VRKME;
    private String LFIMG;
    private String ARKTX;
    private String MATNR;
    private String VBELN;
    private String POSNR;
    private String MSGV1;
    private String MSGV2;
    private String MSGV3;
    private String MSGV4;
    private String REMARK;
    private Object SERNR;
    private Object RFPOS;
    private Object RFBEL;

    public GI_Response_POJO_Class(String CHARG, String VRKME, String LFIMG, String ARKTX, String MATNR, String VBELN, String POSNR, String MSGV1, String MSGV2, String MSGV3, String MSGV4, String REMARKS, Object SERNR, Object RFPOS, Object RFBEL) {
        this.CHARG = CHARG;
        this.VRKME = VRKME;
        this.LFIMG = LFIMG;
        this.ARKTX = ARKTX;
        this.MATNR = MATNR;
        this.VBELN = VBELN;
        this.POSNR = POSNR;
        this.MSGV1 = MSGV1;
        this.MSGV2 = MSGV2;
        this.MSGV3 = MSGV3;
        this.MSGV4 = MSGV4;
        this.REMARK = REMARKS;
        this.SERNR = SERNR;
        this.RFPOS = RFPOS;
        this.RFBEL = RFBEL;
    }

    public String getREMARKS() {
        return REMARK;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARK = REMARKS;
    }

    public String getCHARG() {
        return CHARG;
    }

    public void setCHARG(String CHARG) {
        this.CHARG = CHARG;
    }

    public String getVRKME() {
        return VRKME;
    }

    public void setVRKME(String VRKME) {
        this.VRKME = VRKME;
    }

    public String getLFIMG() {
        return LFIMG;
    }

    public void setLFIMG(String LFIMG) {
        this.LFIMG = LFIMG;
    }

    public String getARKTX() {
        return ARKTX;
    }

    public void setARKTX(String ARKTX) {
        this.ARKTX = ARKTX;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
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

    public String getMSGV1() {
        return MSGV1;
    }

    public void setMSGV1(String MSGV1) {
        this.MSGV1 = MSGV1;
    }

    public String getMSGV2() {
        return MSGV2;
    }

    public void setMSGV2(String MSGV2) {
        this.MSGV2 = MSGV2;
    }

    public String getMSGV3() {
        return MSGV3;
    }

    public void setMSGV3(String MSGV3) {
        this.MSGV3 = MSGV3;
    }

    public String getMSGV4() {
        return MSGV4;
    }

    public void setMSGV4(String MSGV4) {
        this.MSGV4 = MSGV4;
    }

    public Object getSERNR() {
        return SERNR;
    }

    public void setSERNR(Object SERNR) {
        this.SERNR = SERNR;
    }

    public Object getRFPOS() {
        return RFPOS;
    }

    public void setRFPOS(Object RFPOS) {
        this.RFPOS = RFPOS;
    }

    public Object getRFBEL() {
        return RFBEL;
    }

    public void setRFBEL(Object RFBEL) {
        this.RFBEL = RFBEL;
    }
}
