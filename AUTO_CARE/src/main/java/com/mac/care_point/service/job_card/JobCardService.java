/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_card;

import com.mac.care_point.master.vehicleAssignment.VehicleAssignmentRepository;
import com.mac.care_point.master.vehicleAssignment.model.TVehicleAssignment;
import com.mac.care_point.service.common.Constant;
import com.mac.care_point.service.final_check_list.MFinalCheckListItemRepostory;
import com.mac.care_point.service.final_check_list.TJobFinalCheckListRepository;
import com.mac.care_point.service.final_check_list.model.MFinalCheckListItem;
import com.mac.care_point.service.final_check_list.model.TJobFinalCheckList;
import com.mac.care_point.service.job_card.model.JobCard;
import com.mac.care_point.service.job_card.model.TPriceCategoryChangeDetails;
import com.mac.care_point.service.job_item.JobItemRepository;
import com.mac.care_point.service.job_item.model.TJobItem;
import com.mac.care_point.service.vehicle_attenctions.MVehicleAttenctionRepository;
import com.mac.care_point.service.vehicle_attenctions.TJobVehicleAttenctionsRepository;
import com.mac.care_point.service.vehicle_attenctions.model.MVehicleAttenctions;
import com.mac.care_point.service.vehicle_attenctions.model.TJobVehicleAttenctions;
import com.mac.care_point.service.zmaster.vehicle.SVVehicleRepository;
import com.mac.care_point.service.zmaster.vehicle.model.MVehicle;
import com.mac.care_point.system.exception.DuplicateEntityException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class JobCardService {

    @Autowired
    private JobCardRepository jobCardRepository;

    @Autowired
    private JobItemRepository jobItemRepository;

    @Autowired
    private MVehicleAttenctionRepository attenctionRepository;

    @Autowired
    private TJobVehicleAttenctionsRepository jobVehicleAttenctionsRepository;

    @Autowired
    private MFinalCheckListItemRepostory finalCheckListItemRepostory;

    @Autowired
    private TJobFinalCheckListRepository tJobFinalCheckListRepository;

    @Autowired
    private VehicleAssignmentRepository vehicleAssignmentRepository;

    @Autowired
    private SVVehicleRepository sVVehicleRepository;

    @Autowired
    private TPriceCategoryChangeDetailsRepository tPriceCategoryChangeDetailsRepository;

    public List<JobCard> getClientHistory(Integer indexNo) {
        return jobCardRepository.findJobCardByClient(indexNo);
    }

    public JobCard saveJobCard(JobCard jobCard) {
        JobCard getSaveData = new JobCard();
        List<JobCard> findByVehicleAndStatus = jobCardRepository.findByVehicleAndStatus(jobCard.getVehicle(), Constant.PENDING_STATUS);
        if (findByVehicleAndStatus.isEmpty()) {
            if (jobCard.getIndexNo() == null) {
                Integer maxNo = jobCardRepository.getMaximumNumberByBranch(jobCard.getBranch());
                if (maxNo == null) {
                    maxNo = 0;
                }
                jobCard.setNumber(maxNo + 1);
            }
            String inTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            jobCard.setInTime(inTime);
            jobCard.setDate(new Date());
            jobCard.setBay(2);//branch default bay

            getSaveData = jobCardRepository.save(jobCard);
            
            

            //check allrady exsist data
            List<TJobVehicleAttenctions> getJobCardData = jobVehicleAttenctionsRepository.findByJobCard(getSaveData.getIndexNo());
            if (getJobCardData.isEmpty()) {
                List< MVehicleAttenctions> vehicleAttenctionsList = attenctionRepository.findAll();
                for (MVehicleAttenctions mVehicleAttenctions : vehicleAttenctionsList) {

                    TJobVehicleAttenctions jobVehicleAttenctions = new TJobVehicleAttenctions();
                    jobVehicleAttenctions.setJobCard(getSaveData.getIndexNo());
                    jobVehicleAttenctions.setVehicleAttenctions(mVehicleAttenctions.getIndexNo());
                    jobVehicleAttenctions.setVehicleAttenctionsCategory(mVehicleAttenctions.getCategory());
                    jobVehicleAttenctionsRepository.save(jobVehicleAttenctions);
                }
            } else {
                throw new DuplicateEntityException("Duplicate Data");
            }

            //save final check list data
            List<TJobFinalCheckList> getVehicleFinalCheckListData = tJobFinalCheckListRepository.findByJobCard(getSaveData.getIndexNo());
            if (getVehicleFinalCheckListData.isEmpty()) {
                List<MFinalCheckListItem> vehicleAttenctionsList = finalCheckListItemRepostory.findAll();
                for (MFinalCheckListItem mFinalCheckListItem : vehicleAttenctionsList) {

                    TJobFinalCheckList jobFinalCheckList = new TJobFinalCheckList();
                    jobFinalCheckList.setFinalCheckListItem(mFinalCheckListItem.getIndexNo());
                    jobFinalCheckList.setVehicle(getSaveData.getVehicle());
                    jobFinalCheckList.setJobCard(getSaveData.getIndexNo());
                    jobFinalCheckList.setCheck(Constant.PENDING_STATUS);
                    tJobFinalCheckListRepository.save(jobFinalCheckList);

                }
            } else {
                throw new DuplicateEntityException("Duplicate Data");
            }

            //save vehicle assignment wating bay -  set default washing bay
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            TVehicleAssignment tVehicleAssignment = new TVehicleAssignment();
            tVehicleAssignment.setBay(2); //branch default bay
            tVehicleAssignment.setOutTime(null);//no out time yet 
            tVehicleAssignment.setBranch(jobCard.getBranch());
            tVehicleAssignment.setDate(new Date());
            tVehicleAssignment.setInTime(currentTime);
            tVehicleAssignment.setJobCard(getSaveData.getIndexNo());
            vehicleAssignmentRepository.save(tVehicleAssignment);

        } else {
            throw new DuplicateEntityException("This vehicle pending jobCard is exsist!");
        }

        return getSaveData;
    }

    public JobCard getJobCard(Integer indexNo) {
        return jobCardRepository.findOne(indexNo);
    }

    @Transactional
    public JobCard setServiceChargers(Integer jobCard, Boolean status) {
        //jobcard set service chargers
        JobCard getJobCardData = jobCardRepository.findOne(jobCard);
        getJobCardData.setServiceChagers(status);

        //job item insert row service chargers
        if (getJobCardData.getServiceChagers()) {

            TJobItem jobItem = new TJobItem();
            jobItem.setJobCard(getJobCardData.getIndexNo());
            jobItem.setItemType(Constant.SERVICE_CHARGERS);
            jobItem.setPrice(new BigDecimal("500.00"));
            jobItem.setQuantity(BigDecimal.ONE);
            jobItem.setValue(new BigDecimal("500.00"));
            jobItem.setJobStatus(Constant.PENDING_STATUS);
            jobItem.setOrderStatus(Constant.PENDING_STATUS);
            jobItemRepository.save(jobItem);
            jobCardRepository.save(getJobCardData);

        } else {

            //service chargers remove
            List<TJobItem> getItemDataList = jobItemRepository.findByJobCardAndItemType(getJobCardData.getIndexNo(), Constant.SERVICE_CHARGERS);
            for (TJobItem tJobItem : getItemDataList) {
                jobItemRepository.deleteFromIndex(tJobItem.getIndexNo());
            }
        }

        return getJobCardData;
    }

    public List<JobCard> getNotFinishedJobCard(Integer branch) {
        return jobCardRepository.findByBranchAndStatusNotIn(branch, Constant.FINISHE_STATUS);
    }

    //service selection or stock selection
    public List<JobCard> findByBranchAndStatusAndInvoiceAndDefaultFinalCheckOrderByIndexNoDesc(Integer branch) {
        return jobCardRepository.findByBranchAndStatusAndInvoiceAndDefaultFinalCheckOrderByIndexNoDesc(branch, Constant.PENDING_STATUS, false, false);
    }

    //invoice
    public List<JobCard> findByBranchAndStatusAndInvoiceOrderByIndexNoDesc(Integer branch) {
        return jobCardRepository.findByBranchAndStatusAndInvoiceOrderByIndexNoDesc(branch, Constant.PENDING_STATUS, false);
    }

    //final check list
    public List<JobCard> findByBranchAndStatusAndDefaultFinalCheckOrderByIndexNoDesc(Integer branch) {
        return jobCardRepository.findByBranchAndStatusAndDefaultFinalCheckOrderByIndexNoDesc(branch, Constant.PENDING_STATUS, false);
    }

    public List<JobCard> getJobCardByVehicleNo(String vehicleNo) {
        List<MVehicle> vehiclelist = sVVehicleRepository.findByVehicleNo(vehicleNo);
        Integer indexNo = vehiclelist.get(0).getIndexNo();
//        Integer indexNo = vehiclelist.get(1).getIndexNo();
        System.out.println(indexNo);
        String status = "PENDING";
        return jobCardRepository.findByVehicleAndStatus(indexNo, status);
    }

    public List<JobCard> findJobHistory(String vehicleNo) {
        MVehicle vehicle = sVVehicleRepository.findVehicleByVehicleNo(vehicleNo);
        return jobCardRepository.findJobHistory(vehicle.getIndexNo());
    }

    @Transactional
    public JobCard updateJobCardDetailsAndVehicleDetails(JobCard jobCard, Integer employee) {

        Integer newPriceCategory = jobCard.getPriceCategory();
        Integer oldPriceCategory = jobCardRepository.findOne(jobCard.getIndexNo()).getPriceCategory();
        String vehicleType = "";

        //save price category change details
        TPriceCategoryChangeDetails priceCategoryChangeDetails = new TPriceCategoryChangeDetails();
        priceCategoryChangeDetails.setJobCard(jobCard.getIndexNo());
        priceCategoryChangeDetails.setNewPriceCategory(newPriceCategory);
        priceCategoryChangeDetails.setOldPriceCategory(oldPriceCategory);
        priceCategoryChangeDetails.setReponcebleEmployee(employee);
        priceCategoryChangeDetails.setVehicel(jobCard.getVehicle());
        tPriceCategoryChangeDetailsRepository.save(priceCategoryChangeDetails);

        //update vehicle details
        MVehicle mVehicleDetails = sVVehicleRepository.getOne(jobCard.getVehicle());
        vehicleType = mVehicleDetails.getType();
        mVehicleDetails.setPriceCategory(newPriceCategory);
        sVVehicleRepository.save(mVehicleDetails);

        List<TJobItem> findByJobCardOrderByIndexNoDesc = jobItemRepository.findByJobCardOrderByIndexNoDesc(jobCard.getIndexNo());
        for (TJobItem tJobItem : findByJobCardOrderByIndexNoDesc) {
            if (!"STOCK_ITEM".equals(tJobItem.getItemType())) {
                List<Object[]> itemPriceDetails = jobCardRepository.getItemPriceDetails(tJobItem.getItem(), newPriceCategory);
                if (itemPriceDetails.size() > 0) {
                    BigDecimal price;
                    if ("NORMAL".equals(vehicleType)) {
                        price = (BigDecimal) itemPriceDetails.get(0)[0];
                    } else {
                        price = (BigDecimal) itemPriceDetails.get(0)[1];
                    }
                    tJobItem.setPrice(price);
                    tJobItem.setValue(price.multiply(tJobItem.getQuantity()));
                    tJobItem.setIsChange(Boolean.TRUE);
                    jobItemRepository.save(tJobItem);
                }
            }
        }
        return jobCardRepository.save(jobCard);
    }

}
