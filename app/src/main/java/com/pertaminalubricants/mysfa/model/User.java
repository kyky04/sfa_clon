package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 10/29/2016.
 */

public class User {
//    @SerializedName("name")
//    private String name;

    @SerializedName("is_deleted")
    private int isDeleted;

    @SerializedName("level")
    private String level;

    @SerializedName("realm")
    private String realm;

    @SerializedName("username")
    private String username;

    @SerializedName("credentials")
    private Object credentials;

    @SerializedName("challenges")
    private Object challenges;

    @SerializedName("email")
    private String email;

    @SerializedName("emailVerified")
    private boolean emailVerified;

    @SerializedName("status")
    private String status;

    @SerializedName("created")
    private String created;

    @SerializedName("lastUpdated")
    private String lastUpdated;

    @SerializedName("id_region")
    private String idRegion;

    @SerializedName("id_distributor")
    private String idDistributor;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getCredentials() {
        return credentials;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public Object getChallenges() {
        return challenges;
    }

    public void setChallenges(Object challenges) {
        this.challenges = challenges;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    public String getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(String idDistributor) {
        this.idDistributor = idDistributor;
    }
}
