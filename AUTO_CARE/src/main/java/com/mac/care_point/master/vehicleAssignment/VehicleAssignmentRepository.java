/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.vehicleAssignment;

import com.mac.care_point.master.employee.model.Employee;
import com.mac.care_point.master.vehicleAssignment.model.TVehicleAssignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Supervision
 */
public interface VehicleAssignmentRepository extends JpaRepository<TVehicleAssignment, Integer> {

    public List<TVehicleAssignment> findTop1ByJobCardOrderByInTimeDesc(Integer jobCardIndexNo);

    public List<TVehicleAssignment> findByBranchAndBayAndOutTime(Integer branch, Integer bay, Object object);

    @Query(value = "select COUNT(t_vehicle_assignment.index_no) as count\n"
            + "from t_vehicle_assignment,t_job_card\n"
            + "where t_job_card.index_no=t_vehicle_assignment.job_card\n"
            + "	and t_vehicle_assignment.branch=:branch \n"
            + "	and t_vehicle_assignment.bay=:bay \n"
            + "	and t_job_card.`status` != :status\n", nativeQuery = true)
    public Integer getBayAssignVehicleCount(@Param("branch") Integer branch, @Param("status") String status, @Param("bay") Integer bay);

    @Query(value = "select * from t_vehicle_assignment where job_card = :jobCard", nativeQuery = true)
    public List<TVehicleAssignment> findByJobCardOutTimeAsc(@Param("jobCard") Integer jobCard);

    public List<TVehicleAssignment> findByJobCard(Integer jobCard);

    @Query(value = "select m_employee.index_no\n"
            + "from m_employee\n"
            + "left JOIN t_employee_assingment on t_employee_assingment.employee=m_employee.index_no\n"
            + "where t_employee_assingment.date=:date\n"
            + "	and t_employee_assingment.bay=:bay\n"
            + " 	and t_employee_assingment.`status`='PENDING'\n"
            + " 	and t_employee_assingment.is_out=0", nativeQuery = true)
    public List<Integer> getBayDefaultEmplyee(@Param("date")String date,@Param("bay") Integer bay);
}
