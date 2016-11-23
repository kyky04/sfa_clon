package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 12/24/2015.
 */
public class CustomerResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("address_1")
    private String address1;
    @SerializedName("phone")
    private String phone;
    @SerializedName("fax")
    private String fax;
    @SerializedName("lat")
    private String latitude;
    @SerializedName("long")
    private String longitude;
    @SerializedName("active")
    private int active;
    @SerializedName("is_deleted")
    private int isDeleted;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("id_region")
    private int idRegion;
    @SerializedName("id_account")
    private int idAccount;

    public CustomerResponse(int id, String code, String name, String address1, String phone, String fax, String latitude, String longitude, int active, int isDeleted, String createdAt, String updatedAt, int idRegion, int idAccount) {
        this.id = id;
        this.code = (code == null) ? "":code;
        this.name = (name == null) ? "":name;
        this.address1 = (address1 == null) ? "":address1;
        this.phone = (phone == null) ? "":phone;
        this.fax = (fax == null) ? "":fax;
        this.latitude = (latitude == null) ? "":latitude;
        this.longitude = (longitude == null) ? "":longitude;
        this.active = active;
        this.isDeleted = isDeleted;
        this.createdAt = (createdAt == null) ? "":createdAt;
        this.updatedAt = (updatedAt == null) ? "":updatedAt;
        this.idRegion = idRegion;
        this.idAccount = idAccount;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
}
