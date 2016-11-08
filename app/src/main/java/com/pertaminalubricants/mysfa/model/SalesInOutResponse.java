package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 10/31/2016.
 */

public class SalesInOutResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("code")
    private String code;
    @SerializedName("trx_type")
    private String trxType;
    @SerializedName("plant")
    private String plant;
    @SerializedName("shipTo")
    private String shipto;
    @SerializedName("delivnumb")
    private String delivNumb;
    @SerializedName("actualgidate")
    private String actualGiDate;
    @SerializedName("invoiceno")
    private String invoiceNo;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("do_at")
    private String doAt;
    @SerializedName("pending_do")
    private int pendingDo;
    @SerializedName("sales_status")
    private String salesStatus;
    @SerializedName("do_status")
    private String doStatus;
    @SerializedName("is_secondary")
    private int isSecondary;
    @SerializedName("dist_type")
    private String distType;
    @SerializedName("id_distributor")
    private int idDistributor;
    @SerializedName("id_customer")
    private int idCustomer;
    @SerializedName("id_user")
    private int idUser;
    @SerializedName("id_secondary")
    private int idSecondary;
    @SerializedName("id_handler")
    private int idHandler;
    @SerializedName("id_followup")
    private int idFollowup;
    @SerializedName("id_parent")
    private int idParent;
    @SerializedName("id_salesman")
    private int idSalesman;
    @SerializedName("tot_volume")
    private int totVolume;
    @SerializedName("tot_sales")
    private Double totSales;

    public SalesInOutResponse(String id, String code, String trxType, String plant, String shipto, String delivNumb, String actualGiDate, String invoiceNo, String createdAt, String updatedAt, String doAt, int pendingDo, String salesStatus, String doStatus, int isSecondary, String distType, int idDistributor, int idCustomer, int idUser, int idSecondary, int idHandler, int idFollowup, int idParent, int idSalesman, int totVolume, Double totSales) {
        this.id = id;
        this.code = code;
        this.trxType = trxType;
        this.plant = plant;
        this.shipto = shipto;
        this.delivNumb = delivNumb;
        this.actualGiDate = actualGiDate;
        this.invoiceNo = invoiceNo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.doAt = doAt;
        this.pendingDo = pendingDo;
        this.salesStatus = salesStatus;
        this.doStatus = doStatus;
        this.isSecondary = isSecondary;
        this.distType = distType;
        this.idDistributor = idDistributor;
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.idSecondary = idSecondary;
        this.idHandler = idHandler;
        this.idFollowup = idFollowup;
        this.idParent = idParent;
        this.idSalesman = idSalesman;
        this.totVolume = totVolume;
        this.totSales = totSales;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public int getIdSalesman() {
        return idSalesman;
    }

    public void setIdSalesman(int idSalesman) {
        this.idSalesman = idSalesman;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getShipto() {
        return shipto;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public String getDelivNumb() {
        return delivNumb;
    }

    public void setDelivNumb(String delivNumb) {
        this.delivNumb = delivNumb;
    }

    public String getActualGiDate() {
        return actualGiDate;
    }

    public void setActualGiDate(String actualGiDate) {
        this.actualGiDate = actualGiDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public String getDoAt() {
        return doAt;
    }

    public void setDoAt(String doAt) {
        this.doAt = doAt;
    }

    public int getPendingDo() {
        return pendingDo;
    }

    public void setPendingDo(int pendingDo) {
        this.pendingDo = pendingDo;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public String getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(String doStatus) {
        this.doStatus = doStatus;
    }

    public int getIsSecondary() {
        return isSecondary;
    }

    public void setIsSecondary(int isSecondary) {
        this.isSecondary = isSecondary;
    }

    public String getDistType() {
        return distType;
    }

    public void setDistType(String distType) {
        this.distType = distType;
    }

    public int getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(int idDistributor) {
        this.idDistributor = idDistributor;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSecondary() {
        return idSecondary;
    }

    public void setIdSecondary(int idSecondary) {
        this.idSecondary = idSecondary;
    }

    public int getIdHandler() {
        return idHandler;
    }

    public void setIdHandler(int idHandler) {
        this.idHandler = idHandler;
    }

    public int getIdFollowup() {
        return idFollowup;
    }

    public void setIdFollowup(int idFollowup) {
        this.idFollowup = idFollowup;
    }

    public int getTotVolume() {
        return totVolume;
    }

    public void setTotVolume(int totVolume) {
        this.totVolume = totVolume;
    }

    public Double getTotSales() {
        return totSales;
    }

    public void setTotSales(Double totSales) {
        this.totSales = totSales;
    }
}
