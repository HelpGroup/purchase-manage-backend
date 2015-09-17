package com.jiurui.purchase.response;

import java.util.List;

/**
 * Created by mark on 15/9/17.
 */
public class CategoryResponse {
    private Long id;
    private String name;
    private List<IngredientResponse> ingredientList;

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

    public List<IngredientResponse> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientResponse> ingredientList) {
        this.ingredientList = ingredientList;
    }
}