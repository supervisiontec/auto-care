/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.item;

import com.mac.care_point.master.item.model.MItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kavish Manjitha
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    
    private final String ITEM_TYPE = "PACKAGE_ITEM";
    
    public List<MItem> findAllItems() {
        return itemRepository.findAll();
    }

    public List<MItem> findByType() {
        return itemRepository.findByType(ITEM_TYPE);
    }

    public MItem saveItem(MItem item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Integer indexNo) {
        try {
            itemRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("cannot delete this item because there are details in other transaction");
        }
    }
}