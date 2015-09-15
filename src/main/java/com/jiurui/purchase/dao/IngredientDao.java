package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Ingredient;

import java.util.List;

/**
 * Created by mark on 15/9/15.
 */
public interface IngredientDao {
    Ingredient selectByName(String name);

    int create(String name, String unit, Long categoryId);

    Object selectById(long id);

    int delete(long id);

    int update(long id, String name, String unit);

    List<Ingredient> findAll();

    List<Ingredient> findAllByCategoryId(long id);
}
