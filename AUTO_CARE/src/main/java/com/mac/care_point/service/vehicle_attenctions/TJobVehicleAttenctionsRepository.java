/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.vehicle_attenctions;

import com.mac.care_point.service.vehicle_attenctions.model.TJobVehicleAttenctions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kavish manjitha
 */
public interface TJobVehicleAttenctionsRepository extends JpaRepository<TJobVehicleAttenctions, Integer>{

    public List<TJobVehicleAttenctions> findByJobCard(Integer jobCard);
    
}