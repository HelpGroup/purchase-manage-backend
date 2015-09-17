package com.jiurui.purchase.request;

import com.jiurui.purchase.model.Category;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
public class AmountRequest {
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
