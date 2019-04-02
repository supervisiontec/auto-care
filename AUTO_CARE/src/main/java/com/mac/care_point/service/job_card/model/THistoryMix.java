/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_card.model;

/**
 *
 * @author kasun
 */
public class THistoryMix {
    private Integer jobIndex;   
    private Integer jobNo;   
    private String invNo;   
    private String color;   
    private String date;   
    private String amount;
    private String[] jobDetail;

    public THistoryMix() {
    }

    public Integer getJobNo() {
        return jobNo;
    }

    public void setJobNo(Integer jobNo) {
        this.jobNo = jobNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getJobIndex() {
        return jobIndex;
    }

    public void setJobIndex(Integer jobIndex) {
        this.jobIndex = jobIndex;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String[] getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String[] jobDetail) {
        this.jobDetail = jobDetail;
    }
    
    
    
}
