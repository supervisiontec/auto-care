/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.sub_branch.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "m_sub_branch")
public class MSubBranch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "address")
    private String address;

    @Column(name = "is_item_branch")
    private Boolean isItemBranch;

    public MSubBranch() {
    }

    public MSubBranch(Integer indexNo, Integer branch, String name, String type, String code, String telephoneNo, String address, Boolean isItemBranch) {
        this.indexNo = indexNo;
        this.branch = branch;
        this.name = name;
        this.type = type;
        this.code = code;
        this.telephoneNo = telephoneNo;
        this.address = address;
        this.isItemBranch = isItemBranch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsItemBranch() {
        return isItemBranch;
    }

    public void setIsItemBranch(Boolean isItemBranch) {
        this.isItemBranch = isItemBranch;
    }

}
