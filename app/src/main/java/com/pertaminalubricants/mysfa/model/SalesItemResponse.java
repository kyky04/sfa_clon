package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 11/1/2016.
 */

public class SalesItemResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;
    @SerializedName("qty")
    private int qty;
    @SerializedName("unit_price")
    private Double unitPrice;
    @SerializedName("uom")
    private String uom;
    @SerializedName("batch")
    private String batch;
    @SerializedName("created_by")
    private int createdBy;
    @SerializedName("modified_by")
    private int modifiedBy;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("modified_at")
    private String modifiedAt;
    @SerializedName("supply_by_secondary")
    private int supplyBySecondary;
    @SerializedName("id_sales_trx")
    private int idSalesTrx;
    @SerializedName("id_material")
    private int idMaterial;

    public SalesItemResponse(int id, String name, String code, int qty, Double unitPrice, String uom, String batch, int createdBy, int modifiedBy, String createdAt, String modifiedAt, int supplyBySecondary, int idSalesTrx, int idMaterial) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.uom = uom;
        this.batch = batch;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.supplyBySecondary = supplyBySecondary;
        this.idSalesTrx = idSalesTrx;
        this.idMaterial = idMaterial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getSupplyBySecondary() {
        return supplyBySecondary;
    }

    public void setSupplyBySecondary(int supplyBySecondary) {
        this.supplyBySecondary = supplyBySecondary;
    }

    public int getIdSalesTrx() {
        return idSalesTrx;
    }

    public void setIdSalesTrx(int idSalesTrx) {
        this.idSalesTrx = idSalesTrx;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }
}
