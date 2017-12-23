/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.employee_bay_detail;

import com.mac.care_point.service.employee_bay_detail.model.TEmployeeBayDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author L T430
 */
public interface TEmployeeBayRepository extends JpaRepository<TEmployeeBayDetail, Integer> {

    @Query(value = "SELECT t_employee_bay_detail.*\n"
            + "from t_employee_bay_detail\n"
            + "LEFT JOIN t_vehicle_assignment on t_vehicle_assignment.index_no=t_employee_bay_detail.vehicle_assignment\n"
            + "where t_vehicle_assignment.job_card=:jobCard\n"
            + "	and t_vehicle_assignment.branch=:branch\n"
            + "	and t_employee_bay_detail.`status`=:status", nativeQuery = true)
    public List<TEmployeeBayDetail> findByJobCardAndStatusAndBranch(@Param("jobCard") Integer jobCard, @Param("status") String status, @Param("branch") Integer branch);

    public List<TEmployeeBayDetail>  findByVehicleAssignmentAndStatusAndOutTime(Integer indexNo, String PENDING_STATUS, Object object);

}
