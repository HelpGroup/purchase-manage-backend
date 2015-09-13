package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.CategoryDao;
import com.jiurui.purchase.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        Category category = new Category();
        category.setId(1L);
        category.setName("蔬菜");
        list.add(category);
        category = new Category();
        category.setId(2L);
        category.setName("海产");
        list.add(category);
        return list;
    }
}
