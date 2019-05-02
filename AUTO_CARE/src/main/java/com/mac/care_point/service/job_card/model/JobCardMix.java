/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_card.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kasun
 */
public class JobCardMix implements Serializable{
   
    private Integer indexNo;
    private String date;
    private Integer priceCategory;
    private Integer client;
    private Integer vehicle;
    private String vehicleNo;
    private Boolean serviceChagers;
    private String itemType;
    private String color;
    private Boolean isDivide;

    public JobCardMix() {
    }

    public Boolean getIsDivide() {
        return isDivide;
    }

    public void setIsDivide(Boolean isDivide) {
        this.isDivide = isDivide;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(Integer priceCategory) {
        this.priceCategory = priceCategory;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getVehicle() {
        return vehicle;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Boolean getServiceChagers() {
        return serviceChagers;
    }

    public void setServiceChagers(Boolean serviceChagers) {
        this.serviceChagers = serviceChagers;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}
