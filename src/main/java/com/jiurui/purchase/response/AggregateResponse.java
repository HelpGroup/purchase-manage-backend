package com.jiurui.purchase.response;

import java.util.List;

/**
 * Created by mark on 15/9/17.
 */
public class AggregateResponse {

    List<CategoryResponse> list;

    public List<CategoryResponse> getList() {
        return list;
    }

    public void setList(List<CategoryResponse> list) {
        this.list = list;
    }
}
