package com.example.shakerapplication.ModelClasses;

import com.google.gson.annotations.SerializedName;

public class LoadSerialHistory {
    @SerializedName("MODEL")
    private String model;

    @SerializedName("MODEL_DES")
    private String modelDescription;

    @SerializedName("BRAND")
    private String brand;

    @SerializedName("GROUP")
    private String group;

    @SerializedName("CATEGORY")
    private String category;

    @SerializedName("RECIEVE_DAT")
    private String receiveDate;

    @SerializedName("CURR_PLANT")
    private String currentPlant;

    @SerializedName("CURR_STLOC")
    private String currentStockLocation;

    @SerializedName("LAST_PLANT")
    private String lastPlant;

    @SerializedName("LAST_STLOC")
    private String lastStockLocation;

    @SerializedName("SIZE_DIMEN")
    private String sizeDimension;

    @SerializedName("CUSTOMER")
    private String customer;

    @SerializedName("LAST_MOV")
    private String last_mov;


    public LoadSerialHistory(String model, String modelDescription, String brand, String group, String category, String receiveDate, String currentPlant, String currentStockLocation, String lastPlant, String lastStockLocation, String sizeDimension, String customer, String last_mov) {
        this.model = model;
        this.modelDescription = modelDescription;
        this.brand = brand;
        this.group = group;
        this.category = category;
        this.receiveDate = receiveDate;
        this.currentPlant = currentPlant;
        this.currentStockLocation = currentStockLocation;
        this.lastPlant = lastPlant;
        this.lastStockLocation = lastStockLocation;
        this.sizeDimension = sizeDimension;
        this.customer = customer;
        this.last_mov = last_mov;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getCurrentPlant() {
        return currentPlant;
    }

    public void setCurrentPlant(String currentPlant) {
        this.currentPlant = currentPlant;
    }

    public String getCurrentStockLocation() {
        return currentStockLocation;
    }

    public void setCurrentStockLocation(String currentStockLocation) {
        this.currentStockLocation = currentStockLocation;
    }

    public String getLastPlant() {
        return lastPlant;
    }

    public void setLastPlant(String lastPlant) {
        this.lastPlant = lastPlant;
    }

    public String getLastStockLocation() {
        return lastStockLocation;
    }

    public void setLastStockLocation(String lastStockLocation) {
        this.lastStockLocation = lastStockLocation;
    }

    public String getSizeDimension() {
        return sizeDimension;
    }

    public void setSizeDimension(String sizeDimension) {
        this.sizeDimension = sizeDimension;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public String getLast_mov() {
        return last_mov;
    }

    public void setLast_mov(String last_mov) {
        this.last_mov = last_mov;
    }
}
