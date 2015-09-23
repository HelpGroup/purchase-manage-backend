package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.FinanceDao;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Finance;
import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.model.FinanceCategory;
import com.jiurui.purchase.request.FinanceRequest;
import com.jiurui.purchase.service.AmountService;
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
    private AmountService amountService;

    @Override
    public List<FinanceCategory> find(String date, boolean isCharged) {
        List<Category> categories = categoryService.findAll();
        List<FinanceCategory> results = new ArrayList<>();
        for(Category category : categories){
            FinanceCategory result = new FinanceCategory();
            result.setCategoryId(category.getId());
            result.setCategoryName(category.getName());
            List<Finance> finances;
            if(isCharged){
                finances = financeDao.get(category.getId(), date);
            } else {
                finances = new ArrayList<>();
                List<Ingredient> ingredients = ingredientService.findAllByCategoryId(category.getId());
                for (Ingredient ingredient : ingredients) {
                    Finance finance = new Finance();
                    finance.setId(ingredient.getId());
                    finance.setName(ingredient.getName());
                    finance.setCategoryId(category.getId());
                    finance.setUnit(ingredient.getUnit());
                    finance.setNeedBuy(amountService.getSum(ingredient.getId(), date));
                    finance.setActualBuy(0);
                    finance.setTotalCharge(new BigDecimal(0.00));
                    finances.add(finance);
                }
            }
            result.setFinances(finances);
            results.add(result);
        }
        return results;
    }

    @Override
    public int save(FinanceRequest request, String date) {
        int result = 1;
        if(getTodayCount(date)) financeDao.deleteByDate(date);
        List<FinanceCategory> list = request.getFinanceList();
        for(FinanceCategory financeCategory : list) {
            List<Finance> finances = financeCategory.getFinances();
            for(Finance finance : finances){
                result = result&financeDao.create(finance,date);
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
