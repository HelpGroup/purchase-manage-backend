package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.IngredientDao;
import com.jiurui.purchase.model.Ingredient;
import org.springframework.stereotype.Repository;

/**
 * Created by mark on 15/9/15.
 */
@Repository
public class IngredientDaoImpl implements IngredientDao {
    @Override
    public Ingredient selectByName(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("基围虾");
        ingredient.setUnit("g");
        ingredient.setCategoryId(2L);
        return name.equals("基围虾")?ingredient:null;
    }

    @Override
    public int create(String name, String unit, Long categoryId) {
        return 1;
    }
}
