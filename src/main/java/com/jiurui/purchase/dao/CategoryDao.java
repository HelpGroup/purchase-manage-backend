package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.request.CategoryRequest;

import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
public interface CategoryDao {
    List<Category> findAll();

    int update(long id, CategoryRequest request);

    Category selectCategoryById(long id);

    int delete(long id);

    Category selectCategoryByName(String name);

    int create(CategoryRequest request);
}
