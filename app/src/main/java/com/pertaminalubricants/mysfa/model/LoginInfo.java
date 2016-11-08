package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 10/31/2016.
 */

public class LoginInfo {
    @SerializedName("id")
    private String id;

    @SerializedName("ttl")
    private String ttl;

    @SerializedName("created")
    private String created;

    @SerializedName("userId")
    private String userId;

    public LoginInfo(String id, String ttl, String created, String userId) {
        this.id = id;
        this.ttl = ttl;
        this.created = created;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
