package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 11/10/2016.
 */

public class MaterialResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("material")
    private String material;
    @SerializedName("materialdesc")
    private String materialDesc;
    @SerializedName("uom")
    private String uom;
    @SerializedName("gross_weight")
    private String grossWeight;
    @SerializedName("gross_weight_uom")
    private String grossWeightUom;
    @SerializedName("intensif_year")
    private String intensifYear;
    @SerializedName("packaging")
    private String packaging;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public MaterialResponse(int id, String code, String name, String material, String materialDesc, String uom, String grossWeight, String grossWeightUom, String intensifYear, String packaging, String createdAt, String updatedAt) {
        this.id = id;
        this.code = (code == null) ? "":code;
        this.name = (name == null) ? "":name;
        this.material = (material == null) ? "":material;
        this.materialDesc = (materialDesc == null) ? "":materialDesc;
        this.uom = (uom == null) ? "":uom;
        this.grossWeight = (grossWeight == null) ? "":grossWeight;
        this.grossWeightUom = (grossWeightUom == null) ? "":grossWeightUom;
        this.intensifYear = (intensifYear == null) ? "":intensifYear;
        this.packaging = (packaging == null) ? "":packaging;
        this.createdAt = (createdAt == null) ? "":createdAt;
        this.updatedAt = (updatedAt == null) ? "":updatedAt;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getGrossWeightUom() {
        return grossWeightUom;
    }

    public void setGrossWeightUom(String grossWeightUom) {
        this.grossWeightUom = grossWeightUom;
    }

    public String getIntensifYear() {
        return intensifYear;
    }

    public void setIntensifYear(String intensifYear) {
        this.intensifYear = intensifYear;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
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
}
