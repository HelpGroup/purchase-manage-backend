package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Amount;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.response.CategoryResponse;

import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
public interface AmountDao {
    List<Amount> getAmountsByIngredient(long ingredientId, String date);

    int create(Amount amount);

    int getTodayCount(String today,long userId);

    int getAmountByIngredientAndUser(long ingredientId, long userId, String today);

    int update(Amount amount);

    int getSum(long id, String date);
}
