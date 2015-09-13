package com.jiurui.purchase.model;

import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
public class Category {
    private Long id;
    private String name;
    private List<Ingredient> ingredientList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
