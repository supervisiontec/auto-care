/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.items.items;

import com.mac.care_point.master.items.items.model.MItem;
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
 * @author Kavish Manjitha
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/master/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    final String STOCK = "STOCK";
    final String NON_STOCK = "NON STOCK";
    final String SERVICE = "SERVICE";
    final String PACKAGE = "PACKAGE";

    @RequestMapping(method = RequestMethod.GET)
    public List<MItem> findAllItems() {
        return itemService.findAllItems();
    }
    
    @RequestMapping(value = "/stock-item",method = RequestMethod.GET)
    public List<MItem> findItemsByTypeAndQty() {
        return itemService.findItemsByTypeAndQty(STOCK);
    }

    @RequestMapping(value = "/save-item", method = RequestMethod.POST)
    public MItem saveItem(@RequestBody MItem item) {
        return itemService.saveItem(item);
    }
    
    @RequestMapping(value = "/find-by-item-type/{type}", method = RequestMethod.GET)
    public List<MItem> findItemByItemType(@PathVariable String type) {
        return itemService.findItemByItemType(type);
    }

    @RequestMapping(value = "/delete-item/{indexNo}", method = RequestMethod.DELETE)
    public void deletItem(@PathVariable Integer indexNo) {
        itemService.deleteItem(indexNo);
    }
    
    @RequestMapping(value = "/stock-nonstock-item", method = RequestMethod.GET)
    public List<MItem> supplierItems() {
        return itemService.getItemTypeOrType(STOCK,NON_STOCK);
    }
   
    @RequestMapping(value = "/activity-package-item", method = RequestMethod.GET)
    public List<MItem> getActivtyAndPackage() {
        return itemService.getItemTypeOrType(SERVICE,PACKAGE);
    }

    @RequestMapping(value = "/find-item-by-category/{category}/{packageCategory}", method = RequestMethod.GET)
    public List<MItem> findByCategory(@PathVariable Integer category,@PathVariable Integer packageCategory) {
        return itemService.findByCategoryAndPriceCategory(category,packageCategory);
    }
}
