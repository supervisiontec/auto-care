/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.gl.master.service.itemUnit;

import com.mac.gl.master.model.itemUnit.MItemUnit;
import com.mac.gl.master.repository.itemUnit.ItemUnitRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nidura Prageeth
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemUnitService {
    
    @Autowired
    private ItemUnitRepository itemUnitRepository;

    public List<MItemUnit> getAllItemUnits() {
        return itemUnitRepository.findAll();
    }

    public MItemUnit saveItemUnits(MItemUnit unit) {
        return itemUnitRepository.save(unit);
    }

    public void deleteItemUnits(Integer indexNo) {
        itemUnitRepository.delete(indexNo);
    }
    
}