package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ordertmp extends RealmObject {

    @PrimaryKey
    @SerializedName("order_code")
    private String id;

    @SerializedName("customer_code")
    private String custId;

    @SerializedName("order_tax")
    private String tax;

    @SerializedName("order_date")
    private Date date;

    @SerializedName("order_volume")
    private int totalVolume;

    @SerializedName("order_order")
    private double totalOrder;

    @SerializedName("order_status")
    private String status;

//    @Ignore
//    private String isbn;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(int totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}