/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.bay;

import com.mac.care_point.master.bay.model.Bay;
import com.mac.care_point.master.bay.model.BayMain;
import com.mac.care_point.zutil.SecurityUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Supervision
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/bay")
public class BayController {
    
    @Autowired
    private BayService bayService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bay> findAll() {
        return bayService.findAll();
    }
   
    @RequestMapping(value = "/get-bays-by-branch-is-view",method = RequestMethod.GET)
    public List<Bay> findByBranchIsView() {
        return bayService.findByBranchAndBayIsView(SecurityUtil.getCurrentUser().getBranch(),1);
    }
    
    @RequestMapping(value = "/get-bay-main",method = RequestMethod.GET)
    public List<BayMain> getBayMain() {
        return bayService.getBayMain();
    }
   
    @RequestMapping(value = "/get-bays-by-branch-employee-is-view",method = RequestMethod.GET)
    public List<Bay> findByBranchEmployeeIsView() {
        return bayService.findByBranchAndEmployeeIsView(SecurityUtil.getCurrentUser().getBranch(),1);
    }

    @RequestMapping(value = "/insert-detail", method = RequestMethod.POST)
    public Bay insertBay(@RequestBody Bay bay) {
        return bayService.saveBay(bay);
    }

    @RequestMapping(value = "/delete-detail/{indexNo}", method = RequestMethod.DELETE)
    public Integer deleteBay(@PathVariable Integer indexNo) {
        bayService.deleteBay(indexNo);
        return indexNo;
    }
}
