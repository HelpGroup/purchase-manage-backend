package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.CategoryDao;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.request.CategoryRequest;
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
 * Created by mark on 15/9/13.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY id ASC";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map element : list) {
            Category category = new Category();
            category.setId((Long)element.get("id"));
            category.setName((String) element.get("name"));
            categories.add(category);
        }
        return categories;
    }

    @Override
    public int update(long id, CategoryRequest request) {
        String name = request.getName();
        String sql = "UPDATE category SET name = '"+name+"' WHERE id = "+id;
        return template.update(sql);
    }

    @Override
    public Category selectCategoryById(long id) {
        RowMapper<Category> rm = BeanPropertyRowMapper.newInstance(Category.class);
        String sql = "SELECT * FROM category WHERE id = "+ id;
        Category category = null;
        try {
            category = template.queryForObject(sql,rm);
        } catch (EmptyResultDataAccessException e) {
            category = null;
        }
        return category;
    }

    @Override
    public int delete(long id) {
        return template.update("DELETE FROM category WHERE id = "+id+" CASCADE ");
    }

    @Override
    public Category selectCategoryByName(String name) {
        RowMapper<Category> rm = BeanPropertyRowMapper.newInstance(Category.class);
        String sql = "SELECT * FROM category WHERE name = '"+ name + "'";
        Category category = null;
        try {
            category = template.queryForObject(sql,rm);
        } catch (EmptyResultDataAccessException e) {
            category = null;
        }
        return category;
    }

    @Override
    public int create(CategoryRequest request) {
        return template.update("INSERT INTO category(name) VALUES ('"+request.getName()+"')");
    }
}
