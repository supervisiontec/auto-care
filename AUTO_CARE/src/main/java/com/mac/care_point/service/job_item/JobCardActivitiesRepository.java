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
            + "	t_job_activities.*\n"
            + "from \n"
            + "	t_job_activities\n"
            + "	left JOIN m_item on m_item.index_no = t_job_activities.item\n"
            + "	left JOIN m_bay_main on m_bay_main.index_no=m_item.bay\n"
            + "	\n"
            + "	left JOIN m_bay on m_bay.main_bay=m_bay_main.index_no\n"
            + "WHERE t_job_activities.job_card=:jobCard\n"
            + "	and m_bay.index_no=:bay", nativeQuery = true)
    public List<TJobCardActivities> findByJobCardAndBay(@Param("jobCard") Integer jobCard, @Param("bay") Integer bay);
   
   

    @Query(value = "select\n"
            + "	ifnull(SEC_TO_TIME(SUM( TIME_TO_SEC( t_job_activities.activity_time ))),\"00:00:00\") AS timeSum \n"
            + "from \n"
            + "	t_job_activities\n"
            + "WHERE t_job_activities.job_card=:job\n"
            + "	and t_job_activities.bay=:bay", nativeQuery = true)
    public String findByFlatRateFromJobCard(@Param("job") Integer jobIndex,@Param("bay") Integer bay);

    public List<TJobCardActivities> findByJobCard(Integer jobCard);
}
