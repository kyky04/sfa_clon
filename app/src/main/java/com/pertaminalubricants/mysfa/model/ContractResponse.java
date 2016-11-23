package com.pertaminalubricants.mysfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nunu on 12/24/2015.
 */
public class ContractResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("contract_date")
    private String contractDate;
    @SerializedName("status")
    private int status;
    @SerializedName("active_start_date")
    private String activeStartDate;
    @SerializedName("active_end_date")
    private String activeEndDate;
    @SerializedName("approved_by")
    private String approvedBy;
    @SerializedName("approved_date")
    private String approvedDate;
    @SerializedName("created_by")
    private String createdBy;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("sites")
    private String sites;
    @SerializedName("code_customer")
    private String codeCustomer;
    @SerializedName("name_customer")
    private String nameCustomer;
    @SerializedName("code_distributor")
    private String codeDistributor;
    @SerializedName("name_distributor")
    private String nameDistributor;
    @SerializedName("code_salesman")
    private String codeSalesman;
    @SerializedName("name_salesman")
    private String nameSalesman;
    @SerializedName("id_distributor")
    private int idDistributor;
    @SerializedName("id_customer")
    private int idCustomer;
    @SerializedName("id_region")
    private int idRegion;
    @SerializedName("id_salesman")
    private int idSalesman;
    @SerializedName("customer")
    private CustomerResponse customer;

    public ContractResponse(int id, String code, String name, String description, String contractDate, int status, String activeStartDate, String activeEndDate, String approvedBy, String approvedDate, String createdBy, String createdAt, String sites, String codeCustomer, String nameCustomer, String codeDistributor, String nameDistributor, String codeSalesman, String nameSalesman, int idDistributor, int idCustomer, int idRegion, int idSalesman, CustomerResponse customer) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.contractDate = contractDate;
        this.status = status;
        this.activeStartDate = activeStartDate;
        this.activeEndDate = activeEndDate;
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.sites = sites;
        this.codeCustomer = codeCustomer;
        this.nameCustomer = nameCustomer;
        this.codeDistributor = codeDistributor;
        this.nameDistributor = nameDistributor;
        this.codeSalesman = codeSalesman;
        this.nameSalesman = nameSalesman;
        this.idDistributor = idDistributor;
        this.idCustomer = idCustomer;
        this.idRegion = idRegion;
        this.idSalesman = idSalesman;
        this.customer = customer;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(String activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public String getActiveEndDate() {
        return activeEndDate;
    }

    public void setActiveEndDate(String activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getCodeDistributor() {
        return codeDistributor;
    }

    public void setCodeDistributor(String codeDistributor) {
        this.codeDistributor = codeDistributor;
    }

    public String getNameDistributor() {
        return nameDistributor;
    }

    public void setNameDistributor(String nameDistributor) {
        this.nameDistributor = nameDistributor;
    }

    public String getCodeSalesman() {
        return codeSalesman;
    }

    public void setCodeSalesman(String codeSalesman) {
        this.codeSalesman = codeSalesman;
    }

    public String getNameSalesman() {
        return nameSalesman;
    }

    public void setNameSalesman(String nameSalesman) {
        this.nameSalesman = nameSalesman;
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

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public int getIdSalesman() {
        return idSalesman;
    }

    public void setIdSalesman(int idSalesman) {
        this.idSalesman = idSalesman;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }
}
