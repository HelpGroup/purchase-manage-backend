package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.IngredientDao;
import com.jiurui.purchase.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mark on 15/9/15.
 */
@Repository
public class IngredientDaoImpl implements IngredientDao {

    @Autowired
    private JdbcTemplate template;

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

    @Override
    public Object selectById(long id) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("基围虾");
        ingredient.setUnit("g");
        ingredient.setCategoryId(2L);
        return id==1?ingredient:null;
    }

    @Override
    public int delete(long id) {
        return 1;
    }

    @Override
    public int update(long id, String name, String unit) {
        return 1;
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredient ORDER BY id ASC";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map element : list) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId((Long)element.get("id"));
            ingredient.setName((String) element.get("name"));
            ingredient.setUnit((String) element.get("unit"));
            ingredient.setCategoryId((Long)element.get("category_id"));
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
