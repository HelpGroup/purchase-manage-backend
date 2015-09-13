package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Category;

import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
public interface CategoryDao {
    List<Category> findAll();
}
