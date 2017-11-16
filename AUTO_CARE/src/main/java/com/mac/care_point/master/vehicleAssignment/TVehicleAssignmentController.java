/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicleAssignment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mac.care_point.master.vehicleAssignment.model.TVehicleAssignment;
import com.mac.care_point.service.job_card.model.JobCard;
import com.mac.care_point.zutil.SecurityUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Supervision
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/vehicle-assignment")
public class TVehicleAssignmentController {

    @Autowired
    private VehicleAssignmentService vehicleAssignmentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TVehicleAssignment> findAll() {
        return vehicleAssignmentService.findAll();
    }

    @RequestMapping(value = "/insert-detail", method = RequestMethod.POST)
    public TVehicleAssignment insertDetail(@RequestBody TVehicleAssignment vehicleAssignment) {
        vehicleAssignment.setBranch(SecurityUtil.getCurrentUser().getBranch());
        return vehicleAssignmentService.saveDetail(vehicleAssignment);
    }

    @RequestMapping(value = "/job-finished", method = RequestMethod.POST)
    public TVehicleAssignment jobFinished(@RequestBody TVehicleAssignment vehicleAssignment) {
        return vehicleAssignmentService.jobFinished(vehicleAssignment);
    }

    @RequestMapping(value = "/check-employe-assign/{bay}", method = RequestMethod.GET)
    public boolean checkEmployeAssign(@PathVariable Integer bay) {
        return vehicleAssignmentService.checkEmployeAssign(bay, SecurityUtil.getCurrentUser().getBranch(),new Date());
    }

    @RequestMapping(value = "/delete-detail/{indexNo}", method = RequestMethod.DELETE)
    public Integer deleteDetail(@PathVariable Integer indexNo) {
        vehicleAssignmentService.deleteDetail(indexNo);
        return indexNo;
    }

    @RequestMapping(value = "/find-by-job-card/{indexNo}", method = RequestMethod.GET)
    public List<TVehicleAssignment> findByJobCard(@PathVariable Integer indexNo) {
        return vehicleAssignmentService.findByJobCard(indexNo);
    }
    
    @RequestMapping(value = "/set-stop-time", method = RequestMethod.POST)
    public TVehicleAssignment setStopTime(@RequestBody JobCard jobCard) {
        return vehicleAssignmentService.setStopTime(jobCard);
    }
    
    @RequestMapping(value = "/get-job-is-stop/{jobIndex}", method = RequestMethod.GET)
    public boolean getJobIsStop(@PathVariable Integer jobIndex) {
        return vehicleAssignmentService.getJobIsStop(jobIndex);
    }
    @RequestMapping(value = "get-bay-in-time/{jobIndex}/{bay}", method = RequestMethod.GET)
    public TVehicleAssignment getBayInTime(@PathVariable Integer jobIndex,@PathVariable Integer bay) {
        return vehicleAssignmentService.getBayInTime(jobIndex,bay);
    }
    @RequestMapping(value = "get-system-time", method = RequestMethod.GET)
    public TVehicleAssignment getSystemDate() {
        String nowTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date());
        return new TVehicleAssignment(nowTime);
        
    }

}
