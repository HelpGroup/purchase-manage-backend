package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.IngredientDao;
import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.request.IngredientRequest;
import com.jiurui.purchase.request.UpdateIngredientRequest;
import com.jiurui.purchase.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mark on 15/9/15.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDao ingredientDao;

    @Override
    public Ingredient selectOneByName(String name) {
        return ingredientDao.selectByName(name);
    }

    @Override
    public int create(IngredientRequest request) {
        return ingredientDao.create(request.getName(),request.getUnit(), request.getCategoryId());
    }

    @Override
    public Object selectOneById(long id) {
        return ingredientDao.selectById(id);
    }

    @Override
    public int delete(long id) {
        return ingredientDao.delete(id);
    }

    @Override
    public int update(long id, UpdateIngredientRequest request) {
        return ingredientDao.update(id, request.getName(), request.getUnit());
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientDao.findAll();
    }

    @Override
    public List<Ingredient> findAllByCategoryId(long id) {
        return ingredientDao.findAllByCategoryId(id);
    }
}
