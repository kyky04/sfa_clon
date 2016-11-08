package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 11/1/2016.
 */

public class StockResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_by")
    private int createdBy;
    @SerializedName("modified_by")
    private int modifiedBy;
    @SerializedName("qty")
    private int qty;
    @SerializedName("status")
    private int status;
    @SerializedName("id_distributor")
    private int idDistributor;
    @SerializedName("id_material")
    private int idMaterial;
    @SerializedName("id_customer")
    private int idCustomer;
    @SerializedName("id_site")
    private int idSite;

    public StockResponse(int id, String code, String name, String createdAt, String updatedAt, int createdBy, int modifiedBy, int qty, int status, int idDistributor, int idMaterial, int idCustomer, int idSite) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.qty = qty;
        this.status = status;
        this.idDistributor = idDistributor;
        this.idMaterial = idMaterial;
        this.idCustomer = idCustomer;
        this.idSite = idSite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
        this.idDistributor = idDistributor;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }
}
