/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item;

import com.mac.care_point.service.job_item.model.TJobCardActivities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 'Kasun Chamara'
 */
public interface JobCardActivitiesRepository extends JpaRepository<TJobCardActivities, Integer> {

    public void deleteByJobItem(Integer indexNo);

    @Query(value = "select\n"
            + "   t_job_activities.*\n"
            + "from \n"
            + "   t_job_activities,m_item,m_bay_main\n"
            + "where m_item.index_no = t_job_activities.item and \n"
            + "   (m_bay_main.index_no=m_item.bay or\n"
            + "   m_item.bay2=m_bay_main.index_no )and\n"
            + "	t_job_activities.job_card=:jobCard and \n"
            + "   m_bay_main.index_no=:bay", nativeQuery = true)
    public List<TJobCardActivities> findByJobCardAndBay(@Param("jobCard") Integer jobCard, @Param("bay") Integer bay);

    @Query(value = "select\n"
            + "	ifnull(SEC_TO_TIME(SUM( TIME_TO_SEC( t_job_activities.activity_time ))),\"00:00:00\") AS timeSum \n"
            + "from \n"
            + "	t_job_activities\n"
            + "WHERE t_job_activities.job_card=:job\n"
            + "	and t_job_activities.bay=:bay", nativeQuery = true)
    public String findByFlatRateFromJobCard(@Param("job") Integer jobIndex, @Param("bay") Integer bay);

    public List<TJobCardActivities> findByJobCard(Integer jobCard);

    @Query(value = "select ifnull(m_price_category_details.time ,'00:00:00')as time\n"
            + "from m_price_category_details\n"
            + "left JOIN m_item on m_item.index_no=m_price_category_details.item\n"
            + "left JOIN m_price_category on m_price_category.index_no=m_price_category_details.price_category\n"
            + "left JOIN t_job_card on t_job_card.price_category=m_price_category.index_no \n"
            + "where m_price_category_details.item=:item and t_job_card.index_no=:jobCard", nativeQuery = true)
    public String getTimeByPriceCategory(@Param("item") Integer item, @Param("jobCard") Integer jobCard);
}
