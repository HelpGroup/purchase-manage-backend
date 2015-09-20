package com.jiurui.purchase.request;

import javax.validation.constraints.NotNull;

/**
 * Created by mark on 15/9/15.
 */
public class IngredientRequest {
    @NotNull
    private String name;
    @NotNull
    private String unit;

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

}
