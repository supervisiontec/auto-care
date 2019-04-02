/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_card;

import com.mac.care_point.service.job_card.model.JobCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Kavish Manjitha
 */
public interface JobCardRepository extends JpaRepository<JobCard, Integer> {

    public List<JobCard> findByBay(Integer indexNo);

    public List<JobCard> findByBranchAndStatusAndInvoiceOrderByIndexNoDesc(Integer branch, String status, Boolean invoice);

    public List<JobCard> findByBranchAndStatusOrderByIndexNoDesc(Integer branch, String status);

    public List<JobCard> findByBranchAndStatusAndInvoiceAndDefaultFinalCheckOrderByIndexNoDesc(Integer branch, String status, Boolean invoice, Boolean defaultInvoice);

    @Query(value = "SELECT MAX(number)FROM t_job_card WHERE branch=:branch", nativeQuery = true)
    public Integer getMaximumNumberByBranch(@Param("branch") Integer branch);

    public List<JobCard> findByStatusNotIn(String FINISHED_STATUS);

    @Query(value = "select t_job_card.price_category from t_job_card where t_job_card.vehicle=:vehicle LIMIT 1", nativeQuery = true)
    public Integer getPriceCategory(@Param("vehicle") Integer vehicle);

    public List<JobCard> findByBranchAndStatusNotIn(Integer branch, String FINISHED_STATUS);

    public List<JobCard> findByVehicleAndStatus(Integer indexNo, String status);

    public List<JobCard> findJobCardByClient(Integer indexNo);

    @Query(value = "select * from t_job_card job where job.vehicle =:vehicleIndexNo order by job.date desc", nativeQuery = true)
    public List<JobCard> findJobHistory(@Param("vehicleIndexNo") Integer vehicleIndexNo);

    @Query(value = "select\n"
            + "  price_category_details.normal_price,\n"
            + "  price_category_details.register_price\n"
            + "from\n"
            + "  m_item item\n"
            + "inner join\n"
            + "  m_price_category_details price_category_details\n"
            + "on\n"
            + "  item.index_no = price_category_details.item\n"
            + "where\n"
            + "  item.index_no = :item \n"
            + "and\n"
            + "  price_category_details.price_category = :priceCategory", nativeQuery = true)
    public List<Object[]> getItemPriceDetails(@Param("item") Integer item, @Param("priceCategory") Integer priceCategory);

    @Query(value = "select t_job_card.index_no,\n"
            + "	m_branch.color,\n"
            + "	t_job_card.date,\n"
            + "	(select GROUP_CONCAT(inv.invoice_no) \n"
            + "		from t_invoice inv where inv.job_card=t_job_card.index_no\n"
            + "	)as inv_no,\n"
            + "	sum(t_invoice.amount) as amount,\n"
            + "	t_job_card.number \n"
            + "from t_job_card\n"
            + "left join t_invoice on t_invoice.job_card=t_job_card.index_no\n"
            + "left join m_vehicle on m_vehicle.index_no=t_job_card.vehicle   \n"
            + "left join m_branch on m_branch.index_no=t_job_card.branch\n"
            + "where m_vehicle.vehicle_no =:vehicleNo\n"
            + "group by t_job_card.index_no \n"
            + "order by t_job_card.index_no desc limit 7", nativeQuery = true)
    public List<Object[]> getJobHistory(@Param("vehicleNo") String vehicleNo);

    @Query(value = "select m_item.name\n"
            + "from t_job_item\n"
            + "left join m_item on m_item.index_no =t_job_item.item\n"
            + "where t_job_item.job_card=:jobNo", nativeQuery = true)
    public String[] getJobDetail(@Param("jobNo") Integer jobNo);

    @Query(value = "select t_job_card.index_no,\n"
            + "	t_job_card.vehicle,\n"
            + "	m_vehicle.vehicle_no,\n"
            + "	t_job_card.`client`,\n"
            + "	t_job_card.date,\n"
            + "	t_job_card.price_category,\n"
            + "	t_job_card.service_chagers,\n"
            + "	t_job_item.item_type,\n"
            + "	if (t_job_item.item_type='SERVICE_ITEM','label-danger','label-primary') as color\n"
            + "from t_job_card\n"
            + "left join m_vehicle on m_vehicle.index_no=t_job_card.vehicle\n"
            + "left join t_job_item on t_job_item.job_card=t_job_card.index_no \n"
            + "where t_job_card.branch=:branch and t_job_card.`status`='PENDING' \n"
            + "and t_job_card.invoice=false and t_job_item.is_invoice=false\n"
            + "group by t_job_card.index_no desc ,  t_job_item.item_type\n"
            + "HAVING t_job_item.item_type<>'SERVICE_CHARGERS'", nativeQuery = true)
    public List<Object[]> devideJobCard(@Param("branch") Integer branch);
}
