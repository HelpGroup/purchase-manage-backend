package com.jiurui.purchase.model;

import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
public class Ingredient {
    private Long id;
    private String name;
    private String unit;//单位
    private Long categoryId;
    private List<Amount> amount;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Amount> getAmount() {
        return amount;
    }

    public void setAmount(List<Amount> amount) {
        this.amount = amount;
    }
}
