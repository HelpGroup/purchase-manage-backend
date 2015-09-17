package com.jiurui.purchase.response;

import com.jiurui.purchase.model.Amount;
import com.jiurui.purchase.model.Ingredient;

import java.util.List;

/**
 * Created by mark on 15/9/17.
 */
public class IngredientResponse extends Ingredient {
    List<Amount> amounts;

    public List<Amount> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Amount> amounts) {
        this.amounts = amounts;
    }
}
