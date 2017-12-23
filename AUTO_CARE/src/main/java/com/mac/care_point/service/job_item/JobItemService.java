/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item;

import com.mac.care_point.master.items.package_item.PackageItemRepository;
import com.mac.care_point.master.items.package_item.model.MPackageItem;
import com.mac.care_point.master.vehicleAssignment.model.TVehicleAssignment;
import com.mac.care_point.service.common.Constant;
import com.mac.care_point.service.final_check_list.MItemCheckDetailRepository;
import com.mac.care_point.service.final_check_list.TJobItemCheckRepository;
import com.mac.care_point.service.final_check_list.model.MItemCheckDetail;
import com.mac.care_point.service.final_check_list.model.TJobItemCheck;
import com.mac.care_point.service.grn.StockLedgerRepository;
import com.mac.care_point.service.grn.model.TStockLedger;
import com.mac.care_point.service.job_item.model.TJobCardActivities;
import com.mac.care_point.service.job_item.model.MItemL;
import com.mac.care_point.service.job_item.model.TJobItem;
import com.mac.care_point.service.stock.transfer.model.MStore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
public class JobItemService {

    @Autowired
    private JobItemRepository jobItemRepository;

    @Autowired
    private StockLedgerRepository stockLedgerRepository;

    @Autowired
    private JobItemStockRepository storeRepository;

    @Autowired
    private TJobItemCheckRepository itemCheckRepository;

    @Autowired
    private PackageItemRepository packageItemRepository;

    @Autowired
    private MItemCheckDetailRepository itemCheckDetailRepository;

    @Autowired
    private MItemLRepository mItemLRepository;

    @Autowired
    private JobCardActivitiesRepository jobCardActivitiesRepository;

    public List<MItemL> findAllMItemL() {
        return mItemLRepository.findAll();
    }

    public List<MItemL> findByItemCategory(Integer itemCategory) {
        return mItemLRepository.findByItemCategory(itemCategory);
    }

    public List<MItemL> getQuickSeacrhItem(String itemKey, Integer priceCategory) {
        List<Object[]> getItemData = mItemLRepository.getQuickSeacrhItem(itemKey, priceCategory);
        List<MItemL> returnItemData = new ArrayList<>();
        for (Object[] objects : getItemData) {
            MItemL itemModifyOb = mItemLRepository.findOne(Integer.parseInt(objects[0].toString()));
            itemModifyOb.setSalePriceNormal((BigDecimal) objects[1]);
            itemModifyOb.setSalePriceRegister((BigDecimal) objects[2]);
            returnItemData.add(itemModifyOb);
        }
        return returnItemData;
    }

    public List<Object[]> getQuickSeacrhItemStockItem(String itemKey) {
        return jobItemRepository.getQuickSeacrhItemStockItem(itemKey);
    }

    @Transactional
    public TJobItem saveJobItem(TJobItem jobItem) {
        jobItem.setIsChange(Boolean.FALSE);
        TJobItem getSaveData = jobItemRepository.save(jobItem);
        List<MItemCheckDetail> getFindJobItemList = itemCheckDetailRepository.findByItem(jobItem.getItem());

        MItemL mItemL = mItemLRepository.findOne(jobItem.getItem());
        if ("SERVICE".equals(mItemL.getType())) {
            TJobCardActivities tJobCardActivities = new TJobCardActivities();
            String time=jobCardActivitiesRepository.getTimeByPriceCategory(jobItem.getItem(),jobItem.getJobCard());
            tJobCardActivities.setActivityTime(time);
            tJobCardActivities.setItem(jobItem.getItem());
            tJobCardActivities.setJobCard(jobItem.getJobCard());
            tJobCardActivities.setUsed(false);
            tJobCardActivities.setVehicleAssignment(null);
            tJobCardActivities.setJobItem(getSaveData.getIndexNo());
            jobCardActivitiesRepository.save(tJobCardActivities);

        } else if ("PACKAGE".equals(mItemL.getType())) {

            List<MPackageItem> packageList = packageItemRepository.findByPackages(jobItem.getItem());
            for (MPackageItem mPackageItem : packageList) {
                TJobCardActivities tJobCardActivities = new TJobCardActivities();
                String time=jobCardActivitiesRepository.getTimeByPriceCategory(mPackageItem.getItem(),jobItem.getJobCard());
                tJobCardActivities.setActivityTime(time);
                tJobCardActivities.setItem(mPackageItem.getItem());
                tJobCardActivities.setJobCard(jobItem.getJobCard());
                tJobCardActivities.setUsed(false);
                tJobCardActivities.setVehicleAssignment(null);
                tJobCardActivities.setJobItem(getSaveData.getIndexNo());
                jobCardActivitiesRepository.save(tJobCardActivities);

            }
        }

        if (!getFindJobItemList.isEmpty()) {
            //save data from final check list
            for (MItemCheckDetail mItemCheckDetail : getFindJobItemList) {
                TJobItemCheck jobItemCheck = new TJobItemCheck();
                jobItemCheck.setJobItem(getSaveData.getIndexNo());
                jobItemCheck.setJobCard(getSaveData.getJobCard());
                jobItemCheck.setDate(new Date());
                jobItemCheck.setItemCheckDetail(mItemCheckDetail.getIndexNo());
                jobItemCheck.setStatus(Constant.NOT_CHECK_STATUS);
                itemCheckRepository.save(jobItemCheck);
            }
        }

        return getSaveData;
    }

    public List<TJobItem> findByJobCard(Integer jobCard) {
        return jobItemRepository.findByJobCardGetJobItemCheck(jobCard);
    }

    @Transactional
    public void deleteJobItem(Integer indexNo) {
        List<TJobItemCheck> getFindJobItemList = itemCheckRepository.findByJobItem(indexNo);
        if (!getFindJobItemList.isEmpty()) {
            itemCheckRepository.delete(getFindJobItemList);
        }
        jobItemRepository.delete(indexNo);
        jobCardActivitiesRepository.deleteByJobItem(indexNo);

    }

    public List<TJobItem> findByJobCardItems(Integer jobCardIndexNo) {
        return jobItemRepository.findByJobCardOrderByIndexNoDesc(jobCardIndexNo);
    }

    //for final check list
    public TJobItem checkItem(Integer item, Integer branch, Integer jobCard, String status) {

        TJobItem jobItem = jobItemRepository.getOne(item);
        if ("COMPLITED".equals(status)) {

            //stock "COMPLITED"
            TStockLedger stockLedger = new TStockLedger();
            stockLedger.setBranch(branch);
            stockLedger.setDate(new Date());
            stockLedger.setFormIndexNo(jobCard);
            stockLedger.setForm(Constant.STOCK_FORM);
            stockLedger.setInQty(BigDecimal.ZERO);
            stockLedger.setItem(jobItem.getItem());
            stockLedger.setOutQty(jobItem.getStockRemoveQty());
            stockLedger.setItem(jobItem.getItem());
            stockLedger.setStore(1);
            stockLedger.setAvaragePriceIn(new BigDecimal(0));

            //set main store for branch
            List<MStore> storeList = storeRepository.findByBranchAndType(branch, Constant.MAIN_STOCK);
            if (!storeList.isEmpty()) {
                stockLedger.setStore(storeList.get(0).getIndexNo());
            }

            //calculat avarage price
            BigDecimal itemAvaragePrice = jobItemRepository.getItemAvaragePrice(branch, jobItem.getItem());
            stockLedger.setAvaragePriceOut(itemAvaragePrice.multiply(jobItem.getStockRemoveQty()));
            stockLedgerRepository.save(stockLedger);

        } else {
            //stock "PENDING"
            List<TStockLedger> stockData = stockLedgerRepository.findByItemAndFormIndexNo(jobItem.getItem(), jobCard);
            stockLedgerRepository.delete(stockData.get(0));
        }

        jobItem.setOrderStatus(status);
        return jobItemRepository.save(jobItem);
    }

    public List<Object[]> getItemStockItemAndNonStockItem(Integer itemCategory) {
        return jobItemRepository.getItemStockItemAndNonStockItem(itemCategory);
    }

    public BigDecimal findByItemStockItem(Integer branch, Integer item) {
        return jobItemRepository.getItemQtyByStock(branch, item);
    }

    public TJobItem findTJobItemByIndexNo(Integer indexNo) {
        return jobItemRepository.findByIndexNo(indexNo);
    }

    public List<TJobItem> changeVehiclePriceCategory(Integer jobCard) {
        return jobItemRepository.findByJobCardOrderByIndexNoDesc(jobCard);
    }

    public List<TJobCardActivities> getJobActivities(Integer jobCard,Integer bay) {
        return jobCardActivitiesRepository.findByJobCardAndBay(jobCard,bay);
    }
    public List<TJobCardActivities> getJobAllActivities(Integer jobCard) {
        return jobCardActivitiesRepository.findByJobCard(jobCard);
        
    }

    public List<TJobCardActivities> saveActivity(List<TJobCardActivities> jobActivityList) {
        System.out.println(jobActivityList.size()+" size");
        return jobCardActivitiesRepository.save(jobActivityList);
    }
}
