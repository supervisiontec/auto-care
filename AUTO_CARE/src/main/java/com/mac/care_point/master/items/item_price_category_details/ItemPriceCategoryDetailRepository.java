/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.items.item_price_category_details;

import com.mac.care_point.master.items.item_price_category_details.model.MPriceCategoryDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kavish manjitha
 */
public interface ItemPriceCategoryDetailRepository extends JpaRepository<MPriceCategoryDetails, Integer> {

    public List<MPriceCategoryDetails> findByItem(Integer item);

    @Query(value = "select \n"
            + "	ifnull(SEC_TO_TIME(sum((TIME_TO_SEC(m_price_category_details.time)))),'00:00:00')as sumTime\n"
            + "from m_price_category_details\n"
            + "	left JOIN m_item on m_item.index_no=m_price_category_details.item\n"
            + "	left JOIN r_package_item on r_package_item.item=m_item.index_no\n"
            + "where r_package_item.package=:packageId and m_price_category_details.price_category=:priceCategoryId", nativeQuery = true)
    public String getTotalTimeByPriceCategory(@Param("packageId")Integer packageId,@Param("priceCategoryId") Integer priceCategoryId);

}
