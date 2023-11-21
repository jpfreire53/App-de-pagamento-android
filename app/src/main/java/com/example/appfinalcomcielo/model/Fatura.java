package com.example.appfinalcomcielo.model;

public class Fatura {


    private String userEmail;

    private String id;

    private String product;

    private String value;

    private String isCredit;

    private String parcel;

    public Fatura(String userEmail, String product, String value, String isCredit, String parcel, String id) {
        this.userEmail = userEmail;
        this.product = product;
        this.value = value;
        this.isCredit = isCredit;
        this.parcel = parcel;
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(String isCredit) {
        this.isCredit = isCredit;
    }

    public String getParcel() {
        return parcel;
    }

    public void setParcel(String parcel) {
        this.parcel = parcel;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
