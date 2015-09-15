package com.jiurui.purchase.service;

import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.request.IngredientRequest;
import com.jiurui.purchase.request.UpdateIngredientRequest;

import java.util.List;

/**
 * Created by mark on 15/9/15.
 */
public interface IngredientService {

    Ingredient selectOneByName(String name, long categoryId);

    int create(IngredientRequest request);

    Object selectOneById(long id);

    int delete(long id);

    int update(long id, UpdateIngredientRequest request);

    List<Ingredient> findAll();

    List<Ingredient> findAllByCategoryId(long id);
}
