package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.AmountDao;
import com.jiurui.purchase.model.Amount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mark on 15/9/16.
 */
@Repository
public class AmountDaoImpl implements AmountDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Amount> getAmountsByIngredient(long ingredientId, String date) {
        String beginTime = date+" 00:00:00";
        String endTime = date+" 23:59:59";
        List<Amount> amounts = new ArrayList<>();
        String sql = "SELECT * FROM amount WHERE ingredient_id = "+ingredientId+" AND inputTime BETWEEN '"
                +beginTime+"' AND '"+endTime+"' ORDER BY user_id ASC ";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map<String, Object> element : list){
            Amount amount = new Amount();
            amount.setId((Long)element.get("id"));
            amount.setIngredientId((Long)element.get("ingredient_id"));
            amount.setUserId((Long)element.get("user_id"));
            amount.setAmount((int)element.get("amount"));
            amounts.add(amount);
        }
        return amounts;
    }

    @Override
    public int create(Amount amount) {
        return template.update("INSERT INTO amount(ingredient_id,user_id,amount) VALUES ("
                +amount.getIngredientId()+","+amount.getUserId()+","+ amount.getAmount()+")");
    }

    @Override
    public int getTodayCount(String today, long userId) {
        return template.queryForObject(
                "SELECT COUNT(*) FROM amount WHERE inputTime BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59'"+
                        " AND user_id = " +userId,Integer.class
        );
    }

    @Override
    public int getAmountByIngredientAndUser(long ingredientId, long userId, String today) {
        return template.queryForObject(
                "SELECT amount FROM amount WHERE inputTime BETWEEN '"+today+" 00:00:00' AND '"+today+" 23:59:59' AND user_id = "
                        +userId+" AND ingredient_id = "+ingredientId,
                Integer.class
        );
    }

    @Override
    public int update(Amount amount) {
        int num = amount.getAmount();
        long ingredientId = amount.getIngredientId();
        long userId = amount.getUserId();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date.getTime()) + " 00:00:00";
        return template.update("UPDATE amount SET amount = "+num+",inputTime = CURRENT_TIMESTAMP WHERE inputTime BETWEEN '"
                +today+" 00:00:00' AND '"+today+" 23:59:59' AND user_id = "+userId+" AND ingredient_id = "+ingredientId);
    }

    @Override
    public int getSum(long id, String date) {
        return template.queryForObject(
                "SELECT sum(amount) FROM amount WHERE ingredient_id = "+id+" AND inputTime BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'",
                Integer.class
        );
    }
}
