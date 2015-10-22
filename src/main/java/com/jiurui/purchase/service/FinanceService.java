package com.jiurui.purchase.service;

import com.jiurui.purchase.model.FinanceCategory;
import com.jiurui.purchase.request.FinanceRequest;

import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
public interface FinanceService {
    List<FinanceCategory> find(String date);

    int save(FinanceRequest request, String date);

    boolean getTodayCount(String date);
}
