package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.IngredientDao;
import com.jiurui.purchase.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    public Ingredient selectByName(String name, long categoryId) {
        RowMapper<Ingredient> rm = BeanPropertyRowMapper.newInstance(Ingredient.class);
        String sql = "SELECT * FROM ingredient WHERE name = '"+ name + "' AND category_id = "+categoryId;
        Ingredient ingredient = null;
        try {
            ingredient = template.queryForObject(sql,rm);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public int create(String name, String unit, Long categoryId) {
        return template.update("INSERT INTO ingredient(name,unit,category_id) VALUES ('"+name+"','"+unit+"',"+categoryId+")");
    }

    @Override
    public Object selectById(long id) {
        RowMapper<Ingredient> rm = BeanPropertyRowMapper.newInstance(Ingredient.class);
        String sql = "SELECT * FROM ingredient WHERE id = "+ id;
        Ingredient ingredient = null;
        try {
            ingredient = template.queryForObject(sql,rm);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public int delete(long id) {
        return template.update("DELETE FROM ingredient WHERE id = "+id);
    }

    @Override
    public int update(long id, String name, String unit) {
        String sql = "UPDATE ingredient SET ";
        if(name != null) sql += "name = '"+name+"' ";
        if(name!=null&&unit!=null) sql += " , ";
        if(unit!=null) sql += "unit = '"+unit+"' ";
        sql += " WHERE id = "+id;
        return template.update(sql);
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

    @Override
    public List<Ingredient> findAllByCategoryId(long id) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredient WHERE category_id = "+id+" ORDER BY id ASC";
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
