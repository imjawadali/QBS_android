package com.example.communityapplication.ModelClasses;

public class Signup_ModelClass_Payload {

     String fullname;
    String gender;
    String dateofbirth;
    String contactnumber;
    String emailaddress;
    String addressmalwai;
    String addresspakaistan;
    String password;

    public Signup_ModelClass_Payload(String fullname, String gender, String dateofbirth, String contactnumber, String emailaddress, String addressmalwai, String addresspakaistan, String password) {
        this.fullname = fullname;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.contactnumber = contactnumber;
        this.emailaddress = emailaddress;
        this.addressmalwai = addressmalwai;
        this.addresspakaistan = addresspakaistan;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getAddressmalwai() {
        return addressmalwai;
    }

    public void setAddressmalwai(String addressmalwai) {
        this.addressmalwai = addressmalwai;
    }

    public String getAddresspakaistan() {
        return addresspakaistan;
    }

    public void setAddresspakaistan(String addresspakaistan) {
        this.addresspakaistan = addresspakaistan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
