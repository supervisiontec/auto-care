/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.sub_branch;

import com.mac.care_point.master.sub_branch.model.MSubBranch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface SubBranchRepository extends JpaRepository<MSubBranch, Integer>{

    public MSubBranch findByBranchAndType(Integer branchIndex, String type);
    
}
