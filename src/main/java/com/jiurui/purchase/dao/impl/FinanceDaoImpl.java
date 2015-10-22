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
    public int deleteByDate(String date) {
        return template.update("DELETE FROM finance WHERE buy_time BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'");
    }

    @Override
    public int create(Finance finance, String today) {
        return template.update("INSERT INTO finance(amount_id,actual_buy,total_charge,buy_time) "
                +"VALUES ("+finance.getId()+","+finance.getActualBuy()+","
                + finance.getTotalCharge()+",'"+today+" 12:00:00')");
    }

    @Override
    public List<Finance> getToday(long id, String today) {
        List<Finance> finances = new ArrayList<>();
        String sql = "select a.* from user left join ( select amount.*,finance.actual_buy,finance.total_charge " +
                "from amount left join finance on finance.amount_id = amount.id where ingredient_id = "+ id +
                " and inputTime between '"+today+" 00:00:00' and '"+today+" 23:59:59' )  as a " +
                "on a.user_id = user.id where role_id != 1 order by user.id asc";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map element : list) {
            Finance finance = new Finance();
            finance.setId((Long)element.get("id"));
            finance.setIngredientId((Long) element.get("ingredient_id"));
            finance.setUserId((Long) element.get("user_id"));
            finance.setAmount((Integer) element.get("amount"));
            finance.setActualBuy((Integer) element.get("actual_buy"));
            finance.setTotalCharge((BigDecimal) element.get("total_charge"));
            finances.add(finance);
        }
        return finances;
    }
}
