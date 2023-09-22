package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post_Good_Issue_Model_Class {
    @SerializedName("I_VBELN")
    private String vbeln;
    @SerializedName("pGI")
    private List<PgiItem> pgiList;

    public Post_Good_Issue_Model_Class(String vbeln, List<PgiItem> pgiList) {
        this.vbeln = vbeln;
        this.pgiList = pgiList;
    }

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public List<PgiItem> getPgiList() {
        return pgiList;
    }

    public void setPgiList(List<PgiItem> pgiList) {
        this.pgiList = pgiList;
    }
}