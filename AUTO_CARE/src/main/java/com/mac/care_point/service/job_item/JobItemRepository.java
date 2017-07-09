/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item;

import com.mac.care_point.service.job_item.model.TJobItem;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Kavish Manjitha
 */
public interface JobItemRepository extends JpaRepository<TJobItem, Integer> {

    public List<TJobItem> findByJobCardOrderByIndexNoDesc(Integer jobCardIndexNo);

    @Query(value = "select m_item.index_no,\n"
            + "ifnull((select sum(t_stock_ledger.in_qty) - sum(t_stock_ledger.out_qty) from t_stock_ledger where t_stock_ledger.branch = :branch and t_stock_ledger.item = m_item.index_no), 0.0) \n"
            + " - ifnull((select sum(t_job_item.stock_remove_qty) from t_job_item where t_job_item.order_status = \"PENDING\" and \n"
            + " t_job_item.item_type = \"STOCK_ITEM\" and \n"
            + " t_job_item.item = m_item.index_no), 0.0) as stock_qty,\n"
            + " m_item.name\n"
            + "from\n"
            + " m_item\n"
            + "where\n"
            + " m_item.type = \"STOCK\" and \n"
            + " m_item.sub_category = :subCategory \n"
            + "group by\n"
            + " m_item.index_no\n"
            + "having stock_qty > 0", nativeQuery = true)
    public List<Object[]> getItemQtyByStockLeger(@Param("subCategory") Integer subCategory, @Param("branch") Integer branch);

    @Query(value = "select m_item.index_no,\n"
            + " ifnull((select sum(t_stock_ledger.in_qty) - sum(t_stock_ledger.out_qty) from t_stock_ledger where t_stock_ledger.branch = :branch and t_stock_ledger.item = m_item.index_no), 0.0) \n"
            + " - ifnull((select sum(t_bay_issue.stock_remove_qty) from t_bay_issue where t_bay_issue.order_status = \"PENDING\" and t_bay_issue.item = m_item.index_no), 0.0) as stock_qty\n"
            + "from\n"
            + " m_item\n"
            + "where\n"
            + " m_item.type = \"NON-STOCK\"\n"
            + "group by\n"
            + " m_item.index_no\n"
            + "having stock_qty > 0  ", nativeQuery = true)
    public List<Object[]> getNonStockItemQtyByStockLeger(@Param("branch") Integer branch);

    @Query(value = "select\n"
            + "  m_item.index_no,\n"
            + "  ifnull((select sum(t_stock_ledger.in_qty) - sum(t_stock_ledger.out_qty) from t_stock_ledger where t_stock_ledger.branch = :branch and t_stock_ledger.item = m_item.index_no), 0.0) as stock,\n"
            + "  ifnull((select sum(t_job_item.stock_remove_qty) from t_job_item where t_job_item.order_status = \"PENDING\" and t_job_item.item_type = \"STOCK_ITEM\"  and t_job_item.item = m_item.index_no), 0.0) as pending\n"
            + "from\n"
            + "   m_item\n"
            + "   where\n"
            + "   m_item.type = \"STOCK\"\n"
            + "   and \n"
            + "   m_item.index_no  = :item\n"
            + "   group by\n"
            + "m_item.index_no", nativeQuery = true)
    public List<Object[]> getItemQtyByStock(@Param("branch") Integer branch, @Param("item") Integer item);

    public List<TJobItem> findByJobCardAndItemType(Integer jobCard, String itemType);

    @Query(value = "select\n"
            + "	IFNULL((sum(t_stock_ledger.avarage_price_in)-sum(t_stock_ledger.avarage_price_out)) /\n"
            + "	(sum(t_stock_ledger.in_qty)-sum(t_stock_ledger.out_qty)),0.00) \n"
            + "	 as avarage_price\n"
            + "from\n"
            + "	t_stock_ledger\n"
            + "where t_stock_ledger.item=:item and t_stock_ledger.branch=:branch", nativeQuery = true)
    public BigDecimal getItemAvaragePrice(@Param("branch") Integer branch, @Param("item") Integer item);

    //final check list
    @Query(value = "select \n"
            + "t_job_item.*\n"
            + "from\n"
            + "t_job_item\n"
            + "inner join t_job_item_check\n"
            + "on t_job_item_check.job_item = t_job_item.index_no\n"
            + "where \n"
            + "t_job_item_check.job_card = :jobCard\n"
            + "group by job_item", nativeQuery = true)
    public List<TJobItem> findByJobCardGetJobItemCheck(@Param("jobCard") Integer jobCard);

    public TJobItem findByIndexNo(Integer indexNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "delete from t_job_item where index_no = :indexNo", nativeQuery = true)
    public void deleteFromIndex(@Param("indexNo") Integer indexNo);
}
