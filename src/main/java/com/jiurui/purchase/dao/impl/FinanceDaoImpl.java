package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.FinanceDao;
import com.jiurui.purchase.model.Finance;
import com.jiurui.purchase.request.FinanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mark on 15/9/18.
 */
@Repository
public class FinanceDaoImpl implements FinanceDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public int getTodayCount(String today) {
        return template.queryForObject(
                "SELECT COUNT(*) FROM finance WHERE buy_time BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59'",Integer.class
        );
    }

    @Override
    public List<Finance> get(long categoryId, String today) {
        List<Finance> finances = new ArrayList<>();
        String sql = "SELECT * FROM ingredient a LEFT JOIN " +
                "(SELECT id AS finance_id, ingredient_id, need_buy, actual_buy,total_charge FROM finance " +
                "WHERE buy_time BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59') b " +
                "ON a.id = b.ingredient_id WHERE category_id = "+categoryId;
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map element : list) {
            Finance finance = new Finance();
            finance.setId((Long)element.get("id"));
            finance.setName((String)element.get("name"));
            finance.setUnit((String)element.get("unit"));
            finance.setCategoryId((Long)element.get("category_id"));
            finance.setFinanceId((Long)element.get("finance_id"));
            finance.setNeedBuy((Integer)element.get("need_buy"));
            finance.setActualBuy((Integer)element.get("actual_buy"));
            finance.setTotalCharge((BigDecimal)element.get("total_charge"));
            finances.add(finance);
        }
        return finances;
    }

    @Override
    public int deleteByDate(String date) {
        return template.update("DELETE FROM finance WHERE buy_time BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'");
    }

    @Override
    public int create(Finance finance, String today) {
        return template.update("INSERT INTO finance(ingredient_id,need_buy,actual_buy,total_charge,buy_time) "
                +"VALUES ("+finance.getId()+","+finance.getNeedBuy()+","+ finance.getActualBuy()+","
                + finance.getTotalCharge()+",'"+today+" ')");
    }
}
