package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.AmountDao;
import com.jiurui.purchase.model.Amount;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.request.AmountRequest;
import com.jiurui.purchase.response.CategoryResponse;
import com.jiurui.purchase.response.IngredientResponse;
import com.jiurui.purchase.service.AmountService;
import com.jiurui.purchase.service.CategoryService;
import com.jiurui.purchase.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
@Service
public class AmountServiceImpl implements AmountService {

    @Autowired
    private AmountDao amountDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IngredientService ingredientService;

    @Override
    public List<CategoryResponse> find(String date) {
        List<Category> categories = categoryService.findAll();
        List<CategoryResponse> responses = new ArrayList<>();
        for(Category category : categories){
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            List<Ingredient> ingredients = ingredientService.findAllByCategoryId(category.getId());
            List<IngredientResponse> iResponses = new ArrayList<>();
            for(Ingredient ingredient : ingredients){
                IngredientResponse ingredientResponse = new IngredientResponse();
                ingredientResponse.setId(ingredient.getId());
                ingredientResponse.setName(ingredient.getName());
                ingredientResponse.setCategoryId(category.getId());
                ingredientResponse.setUnit(ingredient.getUnit());
                int sum = amountDao.getSum(ingredient.getId(), date);
                ingredientResponse.setAmount(sum);
                List<Amount> amounts = amountDao.getAmountsByIngredient(ingredient.getId(),date);
                ingredientResponse.setAmounts(amounts);
                iResponses.add(ingredientResponse);
            }
            response.setIngredientList(iResponses);
            responses.add(response);
        }
        return responses;
    }

    @Override
    @Transactional
    public int input(AmountRequest request, long userId) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date.getTime());
        int count = amountDao.getTodayCount(today, userId);
        List<Category> categories = request.getCategories();
        if(count == 0) {
            return create(categories, userId);
        } else if(count > 0){
            return update(categories, userId);
        }
        return 0;
    }

    public int create(List<Category> categories, long userId){
        try {
            for (Category category : categories) {
                List<Ingredient> ingredients = category.getIngredientList();
                for (Ingredient ingredient : ingredients) {
                    Amount amount = new Amount();
                    amount.setIngredientId(ingredient.getId());
                    amount.setUserId(userId);
                    amount.setAmount(ingredient.getAmount());
                    int result = amountDao.create(amount);
                    if(result != 1) return result;
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(List<Category> categories, long userId){
        try {
            for (Category category : categories) {
                List<Ingredient> ingredients = category.getIngredientList();
                for (Ingredient ingredient : ingredients) {
                    Amount amount = new Amount();
                    amount.setIngredientId(ingredient.getId());
                    amount.setUserId(userId);
                    amount.setAmount(ingredient.getAmount());
                    int result = amountDao.update(amount);
                    if(result != 1) return result;
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Category> list(long userId, String date) {
        boolean isBeforeToday = isBeforeToday(date);
        int count = amountDao.getTodayCount(date, userId);
        List<Category> categories = categoryService.findAll();
        for(Category category : categories){
            List<Ingredient> ingredients = ingredientService.findAllByCategoryId(category.getId());
            if(count > 0){
                for(Ingredient ingredient : ingredients) {
                    int amount = amountDao.getAmountByIngredientAndUser(ingredient.getId(), userId, date);
                    ingredient.setAmount(amount);
                }
            }
            category.setIngredientList(ingredients);
        }
        if(count == 0 && !isBeforeToday){
            int r = create(categories, userId);
            if(r!=1) return null;
        }
        return categories;
    }

    @Override
    public int getSum(Long ingredientId, String date) {
        return amountDao.getSum(ingredientId, date);
    }

    private boolean isBeforeToday(String day) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        try {
            long dayLong = sdf.parse(day).getTime();
            long todayLong = sdf.parse(today).getTime();
            return todayLong-dayLong>0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
