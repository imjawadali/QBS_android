package com.example.communityapplication.ModelClasses;

public class AddDependent_ModelClass_Payload {

    String fullname;
    String gender;
    String dateofbirth;
    String emailaddress;

    public AddDependent_ModelClass_Payload(String fullname, String gender, String dateofbirth, String emailaddress) {
        this.fullname = fullname;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.emailaddress = emailaddress;
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

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }
}
