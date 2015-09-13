package com.jiurui.purchase.request;

import javax.validation.constraints.NotNull;

/**
 * Created by mark on 15/9/13.
 */
public class CategoryRequest {
    @NotNull
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
