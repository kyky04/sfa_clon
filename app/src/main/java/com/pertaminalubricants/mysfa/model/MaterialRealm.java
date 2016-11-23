package com.pertaminalubricants.mysfa.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nunu on 11/1/2016.
 */

public class MaterialRealm extends RealmObject {

//    @PrimaryKey
    private int id;
    private String code;
    private String name;
    private String material;
    private String materialDesc;
    private String uom;
    private String grossWeight;
    private String grossWeightUom;
    private String intensifYear;
    private String packaging;
    private String createdAt;
    private String updatedAt;

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
