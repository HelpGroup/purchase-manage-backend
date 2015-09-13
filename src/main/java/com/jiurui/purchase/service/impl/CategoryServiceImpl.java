package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.CategoryDao;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

}
