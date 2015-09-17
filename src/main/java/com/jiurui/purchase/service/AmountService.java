package com.jiurui.purchase.service;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.request.AmountRequest;

import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
public interface AmountService {

    List<Category> find(String date);

    int input(AmountRequest request, long UserId);

    List<Category> list(long userId);
}
