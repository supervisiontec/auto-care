/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item;

import com.mac.care_point.service.stock.transfer.model.MStore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author L T430
 */
public interface JobItemStockRepository extends JpaRepository<MStore, Integer> {

    public List<MStore> findByBranchAndType(int fromBranch, String MAIN_STOCK);

}
