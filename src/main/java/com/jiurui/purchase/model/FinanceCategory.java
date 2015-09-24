package com.jiurui.purchase.model;

import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
public class FinanceCategory {
    private Long categoryId;
    private String categoryName;
    private List<Finance> finances;

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

    public List<Finance> getFinances() {
        return finances;
    }

    public void setFinances(List<Finance> finances) {
        this.finances = finances;
    }
}
