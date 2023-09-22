package com.example.communityapplication.ModelClasses;

public class Login_ModelClass_Payload {
    String emailaddress;
    String password;

    public Login_ModelClass_Payload(String emailaddress, String password) {
        this.emailaddress = emailaddress;
        this.password = password;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
