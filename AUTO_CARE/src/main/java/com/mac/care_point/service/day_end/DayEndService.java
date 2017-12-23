/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.day_end;

import com.mac.care_point.zutil.SecurityUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DayEndService {

    @Autowired
    private DayEndRepository dayEndRepository;

    public HashMap<String, Object> loadNumberMap(String date) {
        Integer branch = SecurityUtil.getCurrentUser().getBranch();
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        String[] split = date.split("-");
        String firstDate="";
        for (int i = 0; i < 2; i++) {
            firstDate+=split[i];
            firstDate+="-";
        }
        firstDate+="01";
        
        map.put("noOfVehicle", getNoOfVehicle(date,branch));
        map.put("noOfVehiclePending", getNoOfVehiclePending(date,branch));
        map.put("noOfVehicleFinished", getNoOfVehicleFinished(date,branch));
        map.put("workerCount", getWorkerCount(date,branch));
        map.put("saticfactionAll", getNoOfVehicle(date,branch));
        map.put("saticfactionDown", getSaticfactionDown(date,branch));
        map.put("saticfactionUp", getSaticfactionUp(date,branch));
        map.put("saticfactionPending", getSaticfactionPending(date,branch));
        map.put("grossSales", getGrossSales(date,branch));
        map.put("discount", getDiscount(date,branch));
        map.put("cashReceived", getCashReceived(date,branch));
        map.put("cardReceived", getCardReceived(date,branch));
        map.put("chequeReceived", getChequeReceived(date,branch));
        map.put("debitAmount", getDebitAmount(date,branch));
        map.put("otherIncome", getOtherIncome(date,branch));
        map.put("overPayment", getOverPayment(date,branch));
        map.put("todayActivity", getTodayActivity(date,branch));
        map.put("uptoDate", getUptoDate(date,branch,firstDate));
        return map;
    };

    private Integer getNoOfVehicle(String date,Integer branch) {
        return dayEndRepository.getNoOfVehicle(date,branch);
    };

    private Integer getNoOfVehiclePending(String date,Integer branch) {
        return dayEndRepository.getNoOfVehiclePending(date,branch);
    }

    private Integer getWorkerCount(String date, Integer branch) {
        return dayEndRepository.getWorkerCount(date,branch);
        
    }

    private Integer getSaticfactionDown(String date, Integer branch) {
        return dayEndRepository.getSaticfactionDown(date,branch);
    }

    private Integer getSaticfactionUp(String date, Integer branch) {
        return dayEndRepository.getSaticfactionUp(date,branch);
    }

    private Integer getSaticfactionPending(String date, Integer branch) {
        return dayEndRepository.getSaticfactionPending(date,branch);
    }

    private Integer getNoOfVehicleFinished(String date, Integer branch) {
        return dayEndRepository.getNoOfVehicleFinished(date,branch);
    }

    private BigDecimal getGrossSales(String date, Integer branch) {
        return dayEndRepository.getGrossSales(date,branch);
    }

    private BigDecimal getDiscount(String date, Integer branch) {
        return dayEndRepository.getDiscount(date,branch);
    }

    private BigDecimal getCashReceived(String date, Integer branch) {
        return dayEndRepository.getCashReceived(date,branch);
    }

    private BigDecimal getCardReceived(String date, Integer branch) {
        return dayEndRepository.getCardReceived(date,branch);
    }
    private BigDecimal getChequeReceived(String date, Integer branch) {
        return dayEndRepository.getChequeReceived(date,branch);
    }

    private BigDecimal getDebitAmount(String date, Integer branch) {
        return dayEndRepository.getDebitAmount(date,branch);
    }

    private BigDecimal getOtherIncome(String date, Integer branch) {
        return dayEndRepository.getOtherIncome(date,branch);
    }

    private BigDecimal getOverPayment(String date, Integer branch) {
        return dayEndRepository.getOverPayment(date,branch);
    }
    private BigDecimal getTodayActivity(String date, Integer branch) {
        return dayEndRepository.getTodatActivity(date,branch);
    }

    private BigDecimal getUptoDate(String date, Integer branch,String firstDate) {
        return dayEndRepository.getUptoDate(date,branch,firstDate);
    }

//    private Boolean save(String date) {
//        
//        return dayEndRepository.save(date);
//    }

}
