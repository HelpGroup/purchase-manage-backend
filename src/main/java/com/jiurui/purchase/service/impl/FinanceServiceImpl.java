package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.AmountDao;
import com.jiurui.purchase.dao.FinanceDao;
import com.jiurui.purchase.model.*;
import com.jiurui.purchase.request.FinanceRequest;
import com.jiurui.purchase.response.CategoryResponse;
import com.jiurui.purchase.service.CategoryService;
import com.jiurui.purchase.service.FinanceService;
import com.jiurui.purchase.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceDao financeDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private AmountDao amountDao;

    @Override
    public List<FinanceCategory> find(String date) {
        List<Category> categories = categoryService.findAll();
        List<FinanceCategory> results = new ArrayList<>();
        for(Category category : categories){
            FinanceCategory result = new FinanceCategory();
            result.setCategoryId(category.getId());
            result.setCategoryName(category.getName());
            List<Ingredient> ingredients = ingredientService.findAllByCategoryId(category.getId());
            List<FinanceIngredient> financeIngredients = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                FinanceIngredient financeIngredient = new FinanceIngredient();
                financeIngredient.setId(ingredient.getId());
                financeIngredient.setName(ingredient.getName());
                financeIngredient.setCategoryId(category.getId());
                financeIngredient.setUnit(ingredient.getUnit());
                financeIngredients.add(financeIngredient);
                financeIngredient.setFinances(financeDao.getToday(ingredient.getId(), date));
            }
            result.setIngredients(financeIngredients);
            results.add(result);
        }
        return results;
    }

    @Override
    public int save(FinanceRequest request, String date) {
        int result = 1;
        if(getTodayCount(date)) financeDao.deleteByDate(date);
        List<FinanceCategory> list = request.getChargeList();
        for(FinanceCategory financeCategory : list) {
            List<FinanceIngredient> ingredients = financeCategory.getIngredients();
            for(FinanceIngredient financeIngredient : ingredients){
                List<Finance> finances = financeIngredient.getFinances();
                for (Finance finance : finances ){
                    result = result&financeDao.create(finance,date);
                }
            }
        }
        return result;
    }

    @Override
    public boolean getTodayCount(String date) {
        int isCharged = financeDao.getTodayCount(date);
        return isCharged > 0;
    }
}
