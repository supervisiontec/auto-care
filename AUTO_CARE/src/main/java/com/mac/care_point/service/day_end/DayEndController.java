/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.day_end;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/api/care-point/transaction/dat-end")
public class DayEndController {
    
    @Autowired
    private DayEndService dayEndService;
    
    @RequestMapping(value = "/load-number-map/{date}", method = RequestMethod.GET)
    public HashMap<String,Object> loadNumberMap(@PathVariable String date) {
        return dayEndService.loadNumberMap(date);
    }
    
//    @RequestMapping(value = "/save/{date}", method = RequestMethod.GET)
//    public Boolean save(@PathVariable String date) {
//        return dayEndService.save(date);
//    }
}
