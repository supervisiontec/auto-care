/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kavish Manjitha
 */
@Entity
@Table(name = "m_item")
public class MItemL implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    @Size(max = 25)
    @Column(name = "barcode")
    private String barcode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "type")
    private String type;

    @Basic(optional = false)
    @Column(name = "category")
    private Integer category;

    @Basic(optional = false)
    @Column(name = "item_category")
    private Integer itemCategory;

    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_price_normal")
    private BigDecimal salePriceNormal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_price_register")
    private BigDecimal salePriceRegister;

    @Column(name = "price_category")
    private Integer priceCategory;

    @Column(name = "qty_wise")
    private Boolean qtyWise;
   
    @Column(name = "bay2")
    private Integer bay2;
    
    @Column(name = "bay")
    private Integer bay;
    
    @Column(name = "`account`")
    private Integer account;
    
    @Column(name = "master_item")
    private Integer masterItem;
    public MItemL() {
    }

    public MItemL(Integer indexNo, String name, String barcode, String type, Integer category, Integer itemCategory, BigDecimal salePriceNormal, BigDecimal salePriceRegister, Integer priceCategory, Boolean qtyWise, Integer bay2, Integer bay, Integer account, Integer masterItem) {
        this.indexNo = indexNo;
        this.name = name;
        this.barcode = barcode;
        this.type = type;
        this.category = category;
        this.itemCategory = itemCategory;
        this.salePriceNormal = salePriceNormal;
        this.salePriceRegister = salePriceRegister;
        this.priceCategory = priceCategory;
        this.qtyWise = qtyWise;
        this.bay2 = bay2;
        this.bay = bay;
        this.account = account;
        this.masterItem = masterItem;
    }

   

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Integer itemCategory) {
        this.itemCategory = itemCategory;
    }

    public BigDecimal getSalePriceNormal() {
        return salePriceNormal;
    }

    public void setSalePriceNormal(BigDecimal salePriceNormal) {
        this.salePriceNormal = salePriceNormal;
    }

    public BigDecimal getSalePriceRegister() {
        return salePriceRegister;
    }

    public void setSalePriceRegister(BigDecimal salePriceRegister) {
        this.salePriceRegister = salePriceRegister;
    }

    public Integer getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(Integer priceCategory) {
        this.priceCategory = priceCategory;
    }

    public Boolean getQtyWise() {
        return qtyWise;
    }

    public void setQtyWise(Boolean qtyWise) {
        this.qtyWise = qtyWise;
    }

    public Integer getBay2() {
        return bay2;
    }

    public void setBay2(Integer bay2) {
        this.bay2 = bay2;
    }

    public Integer getBay() {
        return bay;
    }

    public void setBay(Integer bay) {
        this.bay = bay;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getMasterItem() {
        return masterItem;
    }

    public void setMasterItem(Integer masterItem) {
        this.masterItem = masterItem;
    }

    

    

}
