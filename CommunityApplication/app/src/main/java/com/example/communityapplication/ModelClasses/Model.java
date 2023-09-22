package com.example.communityapplication.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {


    @SerializedName("emailaddress")
    @Expose
    private String emailaddress;


    @SerializedName("password")
    @Expose
    private String password;

    public Model(String emailaddress, String password) {
        this.emailaddress = emailaddress;
        this.password = password;
    }
}
