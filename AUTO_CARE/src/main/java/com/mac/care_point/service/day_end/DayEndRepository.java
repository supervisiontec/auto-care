/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.day_end;

import com.mac.care_point.service.job_card.model.JobCard;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface DayEndRepository extends JpaRepository<JobCard, Integer> {

    @Query(value = "select ifnull(count(t_job_card.index_no),0) as noOfVehicle\n"
            + "from t_job_card \n"
            + "where t_job_card.date=:date and t_job_card.branch=:branch", nativeQuery = true)
    public Integer getNoOfVehicle(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(count(t_job_card.index_no),0) as noOfVehicle\n"
            + "from t_job_card \n"
            + "where t_job_card.date=:date and t_job_card.status='PENDING' and t_job_card.branch=:branch", nativeQuery = true)
    public Integer getNoOfVehiclePending(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(count(t_job_card.index_no),0) as noOfVehicle\n"
            + "from t_job_card \n"
            + "where t_job_card.date=:date and t_job_card.status='FINISHED' and t_job_card.branch=:branch", nativeQuery = true)
    public Integer getNoOfVehicleFinished(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(count(t_employee_assingment.index_no),0) as worker_count\n"
            + "from t_employee_assingment \n"
            + "left JOIN m_bay on m_bay.index_no=t_employee_assingment.bay\n"
            + "where t_employee_assingment.date=:date and m_bay.branch=:branch", nativeQuery = true)
    public Integer getWorkerCount(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(count(t_job_card.index_no),0) as saticfactionDown\n"
            + "from t_job_card \n"
            + "where t_job_card.date=:date and t_job_card.branch=:branch and"
            + " t_job_card.rate<=13 and t_job_card.rate!= 0", nativeQuery = true)
    public Integer getSaticfactionDown(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(count(t_job_card.index_no),0) as saticfactionUp\n"
            + "from t_job_card \n"
            + "where t_job_card.date=:date and t_job_card.branch=:branch and"
            + " t_job_card.rate>=14", nativeQuery = true)
    public Integer getSaticfactionUp(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(count(t_job_card.index_no),0) as saticfactionPending\n"
            + "from t_job_card \n"
            + "where t_job_card.date=:date and t_job_card.branch=:branch and"
            + " t_job_card.rate = 0", nativeQuery = true)
    public Integer getSaticfactionPending(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_job_item.value),0.00) as value\n"
            + "from t_job_card\n"
            + "left JOIN t_job_item on t_job_item.job_card=t_job_card.index_no\n"
            + "where t_job_card.branch=:branch and t_job_card.date=:date", nativeQuery = true)
    public BigDecimal getGrossSales(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_invoice.discount_amount),0.00) as discount\n"
            + "from t_invoice\n"
            + "left JOIN t_job_card on t_job_card.index_no=t_invoice.job_card\n"
            + "where t_job_card.branch=:branch and t_job_card.date=:date", nativeQuery = true)
    public BigDecimal getDiscount(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_payment.cash_amount),0.00)as cash\n"
            + "from t_payment\n"
            + "left  JOIN t_customer_ledger on t_customer_ledger.payment =t_payment.index_no\n"
            + "left JOIN t_invoice on t_invoice.index_no= t_customer_ledger.invoice\n"
            + "where t_customer_ledger.date=:date and t_invoice.branch =:branch", nativeQuery = true)
    public BigDecimal getCashReceived(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_payment.card_amount),0.00) as card\n"
            + "from t_payment\n"
            + "left  JOIN t_customer_ledger on t_customer_ledger.payment =t_payment.index_no\n"
            + "left JOIN t_invoice on t_invoice.index_no= t_customer_ledger.invoice\n"
            + "where t_customer_ledger.date=:date and t_invoice.branch =:branch", nativeQuery = true)
    public BigDecimal getCardReceived(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_payment.cheque_amount),0.00) as cheque\n"
            + "from t_payment\n"
            + "left  JOIN t_customer_ledger on t_customer_ledger.payment =t_payment.index_no\n"
            + "left JOIN t_invoice on t_invoice.index_no= t_customer_ledger.invoice\n"
            + "where t_customer_ledger.date=:date and t_invoice.branch =:branch", nativeQuery = true)
    public BigDecimal getChequeReceived(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_customer_ledger.credit_amount)-sum(t_customer_ledger.debit_amount),0.00) as debit_amount\n"
            + "from t_customer_ledger\n"
            + "left JOIN t_invoice on t_invoice.index_no=t_customer_ledger.invoice\n"
            + "where t_invoice.branch=:branch and t_customer_ledger.date=:date", nativeQuery = true)
    public BigDecimal getDebitAmount(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_job_item.value),0.00) other_income\n"
            + "from t_job_item\n"
            + "left JOIN t_job_card on t_job_card.index_no=t_job_item.job_card\n"
            + "left JOIN t_invoice on t_invoice.job_card=t_job_card.index_no\n"
            + "where t_job_item.item_type='SERVICE_CHARGERS' and t_invoice.date=:date "
            + "and t_invoice.branch=:branch", nativeQuery = true)
    public BigDecimal getOtherIncome(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_payment.total_amount-\n"
            + "	t_customer_ledger.debit_amount),0.00) as over_payment\n"
            + "from t_customer_ledger\n"
            + "left JOIN t_payment on t_payment.index_no=t_customer_ledger.payment\n"
            + "left JOIN t_invoice on t_invoice.index_no=t_customer_ledger.invoice  \n"
            + "where t_customer_ledger.date=:date and t_invoice.branch=:branch\n"
            + "HAVING over_payment>0", nativeQuery = true)
    public BigDecimal getOverPayment(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_job_item.value),0.00) other_income\n"
            + "from t_job_item\n"
            + "      left JOIN t_job_card on t_job_card.index_no=t_job_item.job_card\n"
            + "      left JOIN t_invoice on t_invoice.job_card=t_job_card.index_no\n"
            + "where t_job_item.item_type='SERVICE_ITEM' and t_invoice.date=:date\n"
            + "      and t_invoice.branch=:branch", nativeQuery = true)
    public BigDecimal getTodatActivity(@Param("date") String date, @Param("branch") Integer branch);

    @Query(value = "select ifnull(sum(t_job_item.value),0.00) other_income\n"
            + "from t_job_item\n"
            + "      left JOIN t_job_card on t_job_card.index_no=t_job_item.job_card\n"
            + "      left JOIN t_invoice on t_invoice.job_card=t_job_card.index_no\n"
            + "where t_job_item.item_type='SERVICE_ITEM' and t_invoice.date BETWEEN :firstDate and :date\n"
            + "      and t_invoice.branch=:branch", nativeQuery = true)
    public BigDecimal getUptoDate(@Param("date") String date, @Param("branch") Integer branch,@Param("firstDate") String firstDate);

//    public Boolean save(String date);

}
