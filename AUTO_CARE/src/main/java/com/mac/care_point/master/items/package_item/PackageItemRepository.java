/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.items.package_item;

import com.mac.care_point.master.items.package_item.model.MPackageItem;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nidura Prageeth
 */
public interface PackageItemRepository extends JpaRepository<MPackageItem, Serializable> {

    public List<MPackageItem> findByPackages(Integer indexNo);

    @Query(value = "select m_item.name,\n"
            + "	m_price_category_details.normal_price\n"
            + "from m_item,r_package_item,m_price_category_details\n"
            + "where r_package_item.item=m_item.index_no\n"
            + "and m_price_category_details.item=m_item.index_no\n"
            + "and r_package_item.package=:packageItem \n"
            + "and m_price_category_details.price_category=:priceCategory ", nativeQuery = true)
    public List<Object[]> findByPackageSub(
            @Param("packageItem") Integer packageItem,
            @Param("priceCategory") Integer priceCategory);

}
