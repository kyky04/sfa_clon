package com.pertaminalubricants.mysfa.model;

import io.realm.annotations.PrimaryKey;

/**
 * Created by nunu on 10/29/2016.
 */

public class OrderItem {

    @PrimaryKey
    private String id;

    private String orderId;

    private String product;

    private int qty;

    private double price;

    private double total;

//    @Ignore
//    private String isbn;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
