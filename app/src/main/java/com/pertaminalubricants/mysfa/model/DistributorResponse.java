package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 11/1/2016.
 */

public class DistributorResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("address1")
    private String address1;
    @SerializedName("tlp1")
    private String phone;
    @SerializedName("fax")
    private String fax;
    @SerializedName("npwp")
    private String npwp;
    @SerializedName("is_deleted")
    private int isDeleted;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("id_region")
    private int idRegion;
    @SerializedName("id_regency")
    private int idRegency;
    @SerializedName("id_kelurahan")
    private int idKelurahan;
    @SerializedName("id_district")
    private int idDistrict;
    @SerializedName("id_province")
    private int idProvince;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("modified_by")
    private String modifiedBy;

    public DistributorResponse(int id, String code, String name, String address1, String phone, String fax, String npwp, int isDeleted, String createdAt, String updatedAt, int idRegion, int idRegency, int idKelurahan, int idDistrict, int idProvince, String createdBy, String modifiedBy) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address1 = address1;
        this.phone = phone;
        this.fax = fax;
        this.npwp = npwp;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idRegion = idRegion;
        this.idRegency = idRegency;
        this.idKelurahan = idKelurahan;
        this.idDistrict = idDistrict;
        this.idProvince = idProvince;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
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

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
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

    public int getIdRegency() {
        return idRegency;
    }

    public void setIdRegency(int idRegency) {
        this.idRegency = idRegency;
    }

    public int getIdKelurahan() {
        return idKelurahan;
    }

    public void setIdKelurahan(int idKelurahan) {
        this.idKelurahan = idKelurahan;
    }

    public int getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(int idDistrict) {
        this.idDistrict = idDistrict;
    }

    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
