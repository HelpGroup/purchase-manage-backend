package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.AmountDao;
import com.jiurui.purchase.model.Amount;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
@Repository
public class AmountDaoImpl implements AmountDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Category> find(String date) {
//        List<Category> list = new ArrayList<>();
//        Category category = new Category();
//        List<Ingredient> list1 = new ArrayList<>();
//        Ingredient ingredient = new Ingredient();
//        List<Amount> list2 = new ArrayList<>();
//        Amount amount = new Amount();
//        list.add(category);
//        list.add(category);
//        category.setIngredientList(list1);
//        list1.add(ingredient);
//        list1.add(ingredient);
//        ingredient.setAmount(list2);
//        list2.add(amount);
//        list2.add(amount);
        return null;
    }

    @Override
    public int create(Amount amount) {
        return template.update("INSERT INTO amount(ingredient_id,user_id,amount) VALUES ("+amount.getIngredientId()+","+amount.getUserId()+","+ amount.getAmount()+")");
    }

    @Override
    public int getTodayCount(String today, long userId) {
        return template.queryForObject(
                "SELECT COUNT(*) FROM amount WHERE inputTime > '"+today+" 00:00:00' AND user_id = "+userId,Integer.class
        );
    }

    @Override
    public int getListByIngredientAndUser(long ingredientId, long userId, String today) {
        return template.queryForObject(
                "SELECT amount FROM amount WHERE inputTime > '"+today+" 00:00:00' AND user_id = "+userId+" AND ingredient_id = "+ingredientId,
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
        return template.update("UPDATE amount SET amount = "+num+",inputTime = CURRENT_TIMESTAMP WHERE inputTime > '"+today+" 00:00:00' AND user_id = "+userId+" AND ingredient_id = "+ingredientId);
    }
}
