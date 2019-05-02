/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.invoice.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kavish Manjitha
 */
@Entity
@Table(name = "t_invoice")
public class TInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;
    
    @NotNull
    @Column(name = "invoice_no")
    private String invoiceNo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "discount_rate")
    private BigDecimal discountRate;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "branch")
    private Integer branch;
    
    @Column(name = "sub_branch")
    private Integer subBranch;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "status")
    private String status;

    @Column(name = "jobCard")
    private Integer jobCard;

    public TInvoice() {
    }

    public TInvoice(Integer indexNo, Date date, int number, String invoiceNo, BigDecimal amount, BigDecimal discountRate, BigDecimal discountAmount, BigDecimal netAmount, Integer branch, Integer subBranch, String status, Integer jobCard) {
        this.indexNo = indexNo;
        this.date = date;
        this.number = number;
        this.invoiceNo = invoiceNo;
        this.amount = amount;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.netAmount = netAmount;
        this.branch = branch;
        this.subBranch = subBranch;
        this.status = status;
        this.jobCard = jobCard;
    }

    public Integer getSubBranch() {
        return subBranch;
    }

    public void setSubBranch(Integer subBranch) {
        this.subBranch = subBranch;
    }

   
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getJobCard() {
        return jobCard;
    }

    public void setJobCard(Integer jobCard) {
        this.jobCard = jobCard;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

}