package com.jiurui.purchase.service;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.request.CategoryRequest;

import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
public interface CategoryService {

    List<Category> findAll();

    int update(long id, CategoryRequest request);

    Category selectOne(long id);
}
