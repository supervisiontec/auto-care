/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.bay;

import com.mac.care_point.master.bay.model.BayMain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface BayMainRepository extends JpaRepository<BayMain, Integer>{
    
}
