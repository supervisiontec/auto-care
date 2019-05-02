/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.invoice.invoice;

import com.mac.care_point.service.invoice.invoice.model.TInvoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Kavish Manjitha
 */
public interface InvoiceRepository extends JpaRepository<TInvoice, Integer> {

    public List<TInvoice> findByJobCard(Integer jobCard);

    @Query(value = "SELECT MAX(number)FROM t_invoice WHERE branch=:branch", nativeQuery = true)
    public Integer getMaximumNumberByBranch(@Param("branch") Integer branch);

    public List<TInvoice> findByNumberAndBranch(Integer invoiceNumber, Integer branch);

    @Query(value = "select count(t_invoice.index_no)+1 as number \n"
            + "from t_invoice\n"
            + "where year(t_invoice.date) = :year and  MONTH(t_invoice.date) = :month \n"
            + "and t_invoice.branch=:branch \n"
            + "and (:subBranch is null or t_invoice.sub_branch=:subBranch)", nativeQuery = true)
    public Integer getMaximumNumberByBranchAndDate(@Param("branch") Integer branch,
            @Param("year") String year,
            @Param("month") String month,
            @Param("subBranch") Integer subBranch
    );

    @Modifying
    @Query(value = "UPDATE t_job_item SET t_job_item.is_invoice='1' \n"
            + "WHERE t_job_item.item_type=:type and t_job_item.job_card=:jobIndex", nativeQuery = true)
    public Integer updateJobItem(@Param("type") String type, @Param("jobIndex") Integer jobIndex);

    @Modifying
    @Query(value = "UPDATE t_job_item SET t_job_item.is_invoice='1' \n"
            + "WHERE t_job_item.item_type <> :type and t_job_item.job_card=:jobIndex", nativeQuery = true)
    public Integer updateJobItemNot(@Param("type") String type, @Param("jobIndex") Integer jobIndex);

    @Modifying
    @Query(value = "UPDATE t_job_item SET t_job_item.is_invoice='1' \n"
            + "WHERE t_job_item.job_card=:jobIndex", nativeQuery = true)
    public Integer updateJobItemAll(@Param("jobIndex") Integer jobIndex);

}
