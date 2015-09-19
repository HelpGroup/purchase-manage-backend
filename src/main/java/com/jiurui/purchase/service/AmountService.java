package com.jiurui.purchase.service;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.request.AmountRequest;
import com.jiurui.purchase.response.CategoryResponse;

import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
public interface AmountService {

    List<CategoryResponse> find(String date);

    int input(AmountRequest request, long UserId);

    List<Category> list(long userId, String date);

    int getSum(Long ingredientId, String date);
}
