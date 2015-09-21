package com.jiurui.purchase.response;

import com.jiurui.purchase.model.Category;

import java.util.List;

/**
 * Created by mark on 15/9/21.
 */
public class AmountResult {
    private boolean lock = false;
    private List<Category> categories;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
