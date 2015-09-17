package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Amount;
import com.jiurui.purchase.model.Category;

import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
public interface AmountDao {
    List<Category> find(String date);

    int create(Amount amount);

    int getTodayCount(String today,long userId);

    int getListByIngredientAndUser(long ingredientId, long userId, String today);

    int update(Amount amount);
}
