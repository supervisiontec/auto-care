/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicleAssignment;

import com.mac.care_point.master.bay.BayRepository;
import com.mac.care_point.master.bay.model.Bay;
import com.mac.care_point.master.vehicleAssignment.model.TVehicleAssignment;
import com.mac.care_point.service.common.Constant;
import com.mac.care_point.service.employee_assignment.EmployeeAssignmentRepository;
import com.mac.care_point.service.employee_assignment.model.TEmployeeAssingment;
import com.mac.care_point.service.employee_bay_detail.TEmployeeBayRepository;
import com.mac.care_point.service.employee_bay_detail.model.TEmployeeBayDetail;
import com.mac.care_point.service.job_card.JobCardRepository;
import com.mac.care_point.service.job_card.model.JobCard;
import com.mac.care_point.service.job_item.JobCardActivitiesRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Supervision
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VehicleAssignmentService {

    @Autowired
    private VehicleAssignmentRepository vehicleAssignmentRepository;

    @Autowired
    private JobCardRepository jobCardRepository;

    @Autowired
    private TEmployeeBayRepository employeeBayRepository;

    @Autowired
    private EmployeeAssignmentRepository employeeAssignmentRepository;

    @Autowired
    private BayRepository bayRepository;
    
    @Autowired
    private JobCardActivitiesRepository activitiesRepository;

    private final Integer branch = 1;

    public List<TVehicleAssignment> findAll() {
        return vehicleAssignmentRepository.findAll();
    }

    public TVehicleAssignment saveDetail(TVehicleAssignment vehicleAssignment) {
        JobCard findOne = jobCardRepository.findOne(vehicleAssignment.getJobCard());
        vehicleAssignment.setInTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
        findOne.setBay(vehicleAssignment.getBay());
        jobCardRepository.save(findOne);
        List<TVehicleAssignment> updatedObjects = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(vehicleAssignment.getJobCard());

        updateEmployeeBayDetails(vehicleAssignment);

        saveEmployeeBayDetails(vehicleAssignment);

        if (!updatedObjects.isEmpty()) {
            TVehicleAssignment updateVehicleAssignment = updatedObjects.get(0);
            if (updateVehicleAssignment.getOutTime() == null) {
                updateVehicleAssignment.setOutTime(vehicleAssignment.getInTime());
            }
            vehicleAssignmentRepository.save(updateVehicleAssignment);
        }
        vehicleAssignment.setIndexNo(0);

        TVehicleAssignment save = vehicleAssignmentRepository.save(vehicleAssignment);
        return save;
    }

    public void deleteDetail(Integer indexNo) {
        vehicleAssignmentRepository.delete(indexNo);
    }

    public List<TVehicleAssignment> findByJobCard(Integer indexNo) {
        return vehicleAssignmentRepository.findByJobCardOutTimeAsc(indexNo);
    }

    public TVehicleAssignment jobFinished(TVehicleAssignment vehicleAssignment) {
        List<TVehicleAssignment> list = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(vehicleAssignment.getJobCard());
        if (list.isEmpty()) {
            return null;
        }
        TVehicleAssignment finishObject = list.get(0);
        if (finishObject.getOutTime() == null) {
            finishObject.setOutTime(vehicleAssignment.getInTime());
            TVehicleAssignment save = vehicleAssignmentRepository.save(finishObject);
            return save;
        }
        return null;
    }

    private void saveEmployeeBayDetails(TVehicleAssignment vehicleAssignment) {
        Bay bay = bayRepository.findOne(vehicleAssignment.getBay());

        if (bay.getEmployeeIsView() == 1) {
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            TEmployeeBayDetail employeeBayDetail = new TEmployeeBayDetail();
            employeeBayDetail.setBay(vehicleAssignment.getBay());
            employeeBayDetail.setDate(new Date());
            employeeBayDetail.setInTime(dt1.format(new Date()));
            employeeBayDetail.setIndexNo(0);//auto increment
            employeeBayDetail.setJobCard(vehicleAssignment.getJobCard());
            employeeBayDetail.setOutTime(null);
            employeeBayDetail.setStatus("PENDING");
            employeeBayDetail.setType(null);
            employeeBayDetail.setTypeDesc(null);
            employeeBayDetail.setBranch(branch);

            List<TEmployeeAssingment> employeeList = employeeAssignmentRepository.findByBayAssignEmployee(vehicleAssignment.getBay(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            for (TEmployeeAssingment employee : employeeList) {
                employeeBayDetail.setEmployee(employee.getEmployee());
                employeeBayRepository.save(employeeBayDetail);
            }
        } 
    }

    boolean checkEmployeAssign(Integer bay, Integer branch, Date date) {
        Integer empCount = employeeAssignmentRepository.checkEmployeAssign(bay, branch, new SimpleDateFormat("yyyy-MM-dd").format(date), Constant.PENDING_STATUS);
        return empCount != 0;
    }

    private void updateEmployeeBayDetails(TVehicleAssignment vehicleAssignment) {
        List<TEmployeeBayDetail> list = employeeBayRepository.findByJobCardAndStatusAndBranch(vehicleAssignment.getJobCard(), Constant.PENDING_STATUS, branch);
        for (TEmployeeBayDetail employeeBay : list) {
            employeeBay.setStatus(Constant.FINISHE_STATUS);
            employeeBay.setOutTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            employeeBayRepository.save(employeeBay);
        }

    }

    public TVehicleAssignment setStopTime(JobCard jobCard) {

        List<TVehicleAssignment> vehicleAssignmentList
                = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(jobCard.getIndexNo());
        if (vehicleAssignmentList.isEmpty()) {
            throw  new RuntimeException("Can't find job card to process !");
        }
        if (vehicleAssignmentList.get(0).getTimeStop()) {
            throw  new RuntimeException("This job is already exists");
        }
        vehicleAssignmentList.get(0).setOutTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
        vehicleAssignmentRepository.save(vehicleAssignmentList.get(0));

        TVehicleAssignment vehicleAssignment = new TVehicleAssignment();
        vehicleAssignment.setBay(jobCard.getBay());
        vehicleAssignment.setBranch(jobCard.getBranch());
        vehicleAssignment.setDate(new Date());
        vehicleAssignment.setInTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
//            vehicleAssignment.setIndexNo(0);//auto increment
        vehicleAssignment.setJobCard(jobCard.getIndexNo());
        vehicleAssignment.setOutTime(null);
        vehicleAssignment.setTimeStop(true);
        return vehicleAssignmentRepository.save(vehicleAssignment);
    }

    boolean getJobIsStop(Integer jobIndex) {
        List<TVehicleAssignment> vehicleAssignmentList
                = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(jobIndex);
        if (vehicleAssignmentList.isEmpty()) {
            return false;
        }
        return vehicleAssignmentList.get(0).getTimeStop();
    }

    public TVehicleAssignment getBayInTime(Integer jobIndex ,Integer bay) {
        TVehicleAssignment tVehicleAssignment = new TVehicleAssignment();
        List<TVehicleAssignment> vehicleAssignmentList
                = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(jobIndex);
        if (vehicleAssignmentList.isEmpty()) {
            tVehicleAssignment.setInTime(null);
        }else{
            tVehicleAssignment.setInTime(vehicleAssignmentList.get(0).getInTime());
        }
        String flatRate=activitiesRepository.findByFlatRateFromJobCard(jobIndex,bay);
        tVehicleAssignment.setOutTime(flatRate);
        return tVehicleAssignment;
        
    }
}
