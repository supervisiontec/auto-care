/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicleAssignment.model;

import com.mac.care_point.master.employee.model.Employee;
import com.mac.care_point.service.job_item.model.TJobCardActivities;
import java.util.List;

/**
 *
 * @author kasun
 */
public class VehicleAssignmentMix {
    private TVehicleAssignment jobAssignment;
    private List<TJobCardActivities> activityList;
    private String time;
    private Integer percentage;
    private Integer employeeCount;
    private List<Employee> employeeList;
    

    public VehicleAssignmentMix() {
    }

    public VehicleAssignmentMix(TVehicleAssignment jobAssignment, List<TJobCardActivities> activityList, String time, Integer percentage, Integer employeeCount, List<Employee> employeeList) {
        this.jobAssignment = jobAssignment;
        this.activityList = activityList;
        this.time = time;
        this.percentage = percentage;
        this.employeeCount = employeeCount;
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public TVehicleAssignment getJobAssignment() {
        return jobAssignment;
    }

    public void setJobAssignment(TVehicleAssignment jobAssignment) {
        this.jobAssignment = jobAssignment;
    }

    public List<TJobCardActivities> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<TJobCardActivities> activityList) {
        this.activityList = activityList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }
    
    
}
