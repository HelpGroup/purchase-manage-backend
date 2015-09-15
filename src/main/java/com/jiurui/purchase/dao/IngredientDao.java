package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Ingredient;

/**
 * Created by mark on 15/9/15.
 */
public interface IngredientDao {
    Ingredient selectByName(String name);

    int create(String name, String unit, Long categoryId);

    Object selectById(long id);

    int delete(long id);

    int update(long id, String name, String unit);
}
