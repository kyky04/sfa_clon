package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 10/30/2016.
 */

public class Stock {

    @SerializedName("id")
    private int id;

    @SerializedName("code")
    private String code;

    @SerializedName("created_by")
    private int createdBy;

    @SerializedName("modified_by")
    private int modifiedBy;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("modified_at")
    private String modifiedAt;

    @SerializedName("name")
    private String name;

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

}
