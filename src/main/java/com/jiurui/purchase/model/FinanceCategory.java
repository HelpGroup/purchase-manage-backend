package com.jiurui.purchase.model;

import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
public class FinanceCategory {
    private Long categoryId;
    private String categoryName;
    private List<FinanceIngredient> ingredients;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<FinanceIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<FinanceIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
