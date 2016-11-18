/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.gl.master.controller.vehicleAssignment;

import com.mac.gl.master.service.vehicleAssignment.VehicleAssignmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mac.gl.master.model.vehicleAssignment.TVehicleAssignment;
import org.springframework.web.bind.annotation.PathVariable;


/**
 *
 * @author Supervision
 */
@CrossOrigin
@RestController
@RequestMapping("/api/green-leaves/transaction/vehicle-assignment")
public class TVehicleAssignmentController {

    @Autowired
    private VehicleAssignmentService vehicleAssignmentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TVehicleAssignment> findAll() {
        return vehicleAssignmentService.findAll();
    }
    @RequestMapping(value = "/findBy-branch-date",method = RequestMethod.GET)
    public List<TVehicleAssignment> findByBranchAndDate() {
        return vehicleAssignmentService.findAll();
    }

    @RequestMapping(value = "/insert-detail", method = RequestMethod.POST)
    public TVehicleAssignment insertDetail(@RequestBody TVehicleAssignment vehicleAssignment) {
        return vehicleAssignmentService.saveDetail(vehicleAssignment);
    }

    @RequestMapping(value = "/delete-detail/{indexNo}", method = RequestMethod.DELETE)
    public Integer deleteDetail(@PathVariable Integer indexNo) {
        vehicleAssignmentService.deleteDetail(indexNo);
        return indexNo;
    }
    
}
