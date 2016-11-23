package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nunu on 10/29/2016.
 */

public class OrderInOut extends RealmObject {

    @PrimaryKey
    @SerializedName("order_id")
    private String id;

    @SerializedName("order_code")
    private String code;

    @SerializedName("customer_code")
    private String custId;

    @SerializedName("order_tax")
    private String tax;

    @SerializedName("order_date")
    private Date date;

    @SerializedName("order_volume")
    private long totalVolume;

    @SerializedName("order_order")
    private long totalOrder;

    @SerializedName("order_status")
    private String status;

    @SerializedName("id_salesman")
    private int idSalesman;

    @SerializedName("id_parent")
    private int idParent;

//    @Ignore
//    private String isbn;


    public OrderInOut() {
    }

    public OrderInOut(String id, String code, String custId, String tax, Date date, long totalVolume, long totalOrder, String status, int idSalesman, int idParent) {
        this.id = id;
        this.code = code;
        this.custId = custId;
        this.tax = tax;
        this.date = date;
        this.totalVolume = totalVolume;
        this.totalOrder = totalOrder;
        this.status = status;
        this.idSalesman = idSalesman;
        this.idParent = idParent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIdSalesman() {
        return idSalesman;
    }

    public void setIdSalesman(int idSalesman) {
        this.idSalesman = idSalesman;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

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

    public long getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(long totalVolume) {
        this.totalVolume = totalVolume;
    }

    public long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(long totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
