package com.jiurui.purchase.service;

import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.request.IngredientRequest;

/**
 * Created by mark on 15/9/15.
 */
public interface IngredientService {

    Ingredient selectOneByName(String name);

    int create(IngredientRequest request);
}
