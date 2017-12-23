/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicleAssignment;

import com.mac.care_point.master.bay.BayRepository;
import com.mac.care_point.master.bay.model.Bay;
import com.mac.care_point.master.employee.EmployeeRepository;
import com.mac.care_point.master.employee.model.Employee;
import com.mac.care_point.master.vehicleAssignment.model.MTimePersentage;
import com.mac.care_point.master.vehicleAssignment.model.TVehicleAssignment;
import com.mac.care_point.master.vehicleAssignment.model.VehicleAssignmentMix;
import com.mac.care_point.service.common.Constant;
import com.mac.care_point.service.employee_assignment.EmployeeAssignmentRepository;
import com.mac.care_point.service.employee_bay_detail.TEmployeeBayRepository;
import com.mac.care_point.service.employee_bay_detail.model.TEmployeeBayDetail;
import com.mac.care_point.service.job_card.JobCardRepository;
import com.mac.care_point.service.job_card.model.JobCard;
import com.mac.care_point.service.job_item.JobCardActivitiesRepository;
import com.mac.care_point.service.job_item.model.TJobCardActivities;
import com.mac.care_point.zutil.SecurityUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private EmployeeRepository employeeRepository;

    @Autowired
    private TEmployeeBayRepository employeeBayDetailRepository;

    @Autowired
    private EmployeeAssignmentRepository employeeAssignmentRepository;

    @Autowired
    private BayRepository bayRepository;

    @Autowired
    private JobCardActivitiesRepository activitiesRepository;

    @Autowired
    private TimePersentageRepository timePersentageRepository;

    @Autowired
    private JobCardActivitiesRepository jobCardActivitiesRepository;

    public List<TVehicleAssignment> findAll() {
        return vehicleAssignmentRepository.findAll();
    }

    @Transactional
    public TVehicleAssignment saveDetail(VehicleAssignmentMix mix) {
        JobCard findOne = jobCardRepository.findOne(mix.getJobAssignment().getJobCard());
        mix.getJobAssignment().setInTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
        findOne.setBay(mix.getJobAssignment().getBay());
        jobCardRepository.save(findOne);
        List<TVehicleAssignment> updatedObjects = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(mix.getJobAssignment().getJobCard());

        if (!updatedObjects.isEmpty()) {
            TVehicleAssignment updateVehicleAssignment = updatedObjects.get(0);
            if (updateVehicleAssignment.getOutTime() == null) {
                updateVehicleAssignment.setOutTime(mix.getJobAssignment().getInTime());
            }
            vehicleAssignmentRepository.save(updateVehicleAssignment);
        }
        mix.getJobAssignment().setIndexNo(0);
        mix.getJobAssignment().setTime(mix.getTime());
        mix.getJobAssignment().setPercentage(mix.getPercentage());
        mix.getJobAssignment().setEmployeeCount(mix.getEmployeeCount());
        Bay bay = bayRepository.findOne(mix.getJobAssignment().getBay());
        if (!bay.isAssignEmployee()) {
            mix.getJobAssignment().setTime("00:00:00");
            mix.getJobAssignment().setPercentage(0);
            mix.getJobAssignment().setEmployeeCount(0);
            mix.getActivityList().clear();
            
        }

        TVehicleAssignment save = vehicleAssignmentRepository.save(mix.getJobAssignment());
//        update Employee Bay Details
        List<TEmployeeBayDetail> list = employeeBayDetailRepository.findByJobCardAndStatusAndBranch(save.getJobCard(),
                Constant.PENDING_STATUS, SecurityUtil.getCurrentUser().getBranch());
        System.out.println("updated employee detail count "+list.size());
        for (TEmployeeBayDetail employeeBay : list) {
            if (employeeBay.getOutTime() == null) {
                employeeBay.setStatus(Constant.FINISHE_STATUS);
//                employeeBay.setVehicleAssignment(vehicleAssignment.getIndexNo());
                employeeBay.setOutTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                employeeBayDetailRepository.save(employeeBay);
            }
        }

        saveEmployeeBayDetails(save, mix.getEmployeeList());

        for (TJobCardActivities activities : mix.getActivityList()) {
            activities.setVehicleAssignment(save.getIndexNo());
            jobCardActivitiesRepository.save(activities);

        }
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

    private void saveEmployeeBayDetails(TVehicleAssignment vehicleAssignment, List<Employee> employeeList) {
        Bay bay = bayRepository.findOne(vehicleAssignment.getBay());

        if (bay.getEmployeeIsView() == 1) {
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            TEmployeeBayDetail employeeBayDetail = new TEmployeeBayDetail();
            employeeBayDetail.setInTime(dt1.format(new Date()));
            employeeBayDetail.setIndexNo(0);//auto increment
            employeeBayDetail.setVehicleAssignment(vehicleAssignment.getIndexNo());
            employeeBayDetail.setOutTime(null);
            employeeBayDetail.setStatus("PENDING");
            employeeBayDetail.setType(null);
            employeeBayDetail.setTypeDesc(null);

            for (Employee employee : employeeList) {
                employeeBayDetail.setEmployee(employee.getIndexNo());
                employeeBayDetailRepository.save(employeeBayDetail);
            }
        }
    }

    public Integer checkEmployeAssign(Integer bay, Integer branch, Date date) {
        return employeeAssignmentRepository.checkEmployeAssign(bay, branch, new SimpleDateFormat("yyyy-MM-dd").format(date), Constant.PENDING_STATUS);
    }

    public Integer checkEmployeAssignAndGetPersentage(Integer count) {
        MTimePersentage timePersentage = timePersentageRepository.findByEmployeeCount(count);
        if (timePersentage != null) {
            return timePersentage.getPercentage();
        }
        return 0;
    }

    public TVehicleAssignment setStopTime(JobCard jobCard) {

        List<TVehicleAssignment> vehicleAssignmentList
                = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(jobCard.getIndexNo());
        if (vehicleAssignmentList.isEmpty()) {
            throw new RuntimeException("Can't find job card to process !");
        }
        if (vehicleAssignmentList.get(0).getTimeStop()) {
            throw new RuntimeException("This job is already exists");
        }
        vehicleAssignmentList.get(0).setOutTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
        vehicleAssignmentRepository.save(vehicleAssignmentList.get(0));

        //set employee job finished
        List<TEmployeeBayDetail> detailList = employeeBayDetailRepository.findByVehicleAssignmentAndStatusAndOutTime(vehicleAssignmentList.get(0).getIndexNo(), Constant.PENDING_STATUS, null);
        for (TEmployeeBayDetail tEmployeeBayDetail : detailList) {
            tEmployeeBayDetail.setOutTime(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()));
            tEmployeeBayDetail.setType("STOP TIME");
            tEmployeeBayDetail.setStatus(Constant.FINISHE_STATUS);
            employeeBayDetailRepository.save(tEmployeeBayDetail);

        }
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

    public boolean getJobIsStop(Integer jobIndex) {
        List<TVehicleAssignment> vehicleAssignmentList
                = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(jobIndex);
        if (vehicleAssignmentList.isEmpty()) {
            return false;
        }
        return vehicleAssignmentList.get(0).getTimeStop();
    }

    public List<Employee> getBayDefaultEmplyee(Integer bay) {
        String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        List<Integer> empIdList = vehicleAssignmentRepository.getBayDefaultEmplyee(formatDate, bay);
        for (Integer empId : empIdList) {
            employeeList.add(employeeRepository.findOne(empId));
        }
        return employeeList;
    }

    public TVehicleAssignment getBayInTime(Integer jobIndex, Integer bay) {
        TVehicleAssignment tVehicleAssignment = new TVehicleAssignment();
        List<TVehicleAssignment> vehicleAssignmentList
                = vehicleAssignmentRepository.findTop1ByJobCardOrderByInTimeDesc(jobIndex);
        if (vehicleAssignmentList.isEmpty()) {
            tVehicleAssignment.setInTime(null);
        } else {
            tVehicleAssignment.setInTime(vehicleAssignmentList.get(0).getInTime());
        }
        String flatRate = activitiesRepository.findByFlatRateFromJobCard(jobIndex, bay);
        tVehicleAssignment.setOutTime(flatRate);
        return tVehicleAssignment;

    }
}
