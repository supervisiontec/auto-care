/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicleAssignment;

import com.mac.care_point.master.vehicleAssignment.model.MTimePersentage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface TimePersentageRepository extends JpaRepository<MTimePersentage, Integer>{

    public MTimePersentage findByEmployeeCount(Integer empCount);
    
    
}
