package com.pertaminalubricants.mysfa.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nunu on 10/26/2016.
 */

public class OrderDetailTmp extends RealmObject {

    @PrimaryKey
    private String id;

    private String orderId;

    private String productName;

    private String productCode;

    private String productId;

    private String productMaterial;

    private int productMaterialId;

    private String productGrossWeightUom;

    private int productSupplyBySecondary;

    private int qty;

    private long price;

    private long total;

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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    public String getProductGrossWeightUom() {
        return productGrossWeightUom;
    }

    public void setProductGrossWeightUom(String productGrossWeightUom) {
        this.productGrossWeightUom = productGrossWeightUom;
    }

    public int getProductSupplyBySecondary() {
        return productSupplyBySecondary;
    }

    public void setProductSupplyBySecondary(int productSupplyBySecondary) {
        this.productSupplyBySecondary = productSupplyBySecondary;
    }

    public int getProductMaterialId() {
        return productMaterialId;
    }

    public void setProductMaterialId(int productMaterialId) {
        this.productMaterialId = productMaterialId;
    }
}
