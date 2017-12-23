/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item.model;

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
 * @author 'Kasun Chamara'
 */
@Entity
@Table(name = "t_job_activities")
public class TJobCardActivities implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "activity_time")
    private String activityTime;

    @Column(name = "item")
    private Integer item;

    @Column(name = "job_card")
    private Integer jobCard;

    @Column(name = "job_item")
    private Integer jobItem;

    @Column(name = "used")
    private Boolean used;

    @Column(name = "vehicle_assignment")
    private Integer vehicleAssignment;

    public TJobCardActivities() {
    }

    public TJobCardActivities(Integer indexNo, String activityTime, Integer item, Integer jobCard, Integer jobItem, Boolean used, Integer vehicleAssignment) {
        this.indexNo = indexNo;
        this.activityTime = activityTime;
        this.item = item;
        this.jobCard = jobCard;
        this.jobItem = jobItem;
        this.used = used;
        this.vehicleAssignment = vehicleAssignment;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getJobCard() {
        return jobCard;
    }

    public void setJobCard(Integer jobCard) {
        this.jobCard = jobCard;
    }

    public Integer getJobItem() {
        return jobItem;
    }

    public void setJobItem(Integer jobItem) {
        this.jobItem = jobItem;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Integer getVehicleAssignment() {
        return vehicleAssignment;
    }

    public void setVehicleAssignment(Integer vehicleAssignment) {
        this.vehicleAssignment = vehicleAssignment;
    }

}
