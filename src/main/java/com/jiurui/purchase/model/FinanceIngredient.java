package com.jiurui.purchase.model;

import java.util.List;

/**
 * Created by mark on 15/10/20.
 */
public class FinanceIngredient extends Ingredient {
    List<Finance> finances;

    public List<Finance> getFinances() {
        return finances;
    }

    public void setFinances(List<Finance> finances) {
        this.finances = finances;
    }
}
