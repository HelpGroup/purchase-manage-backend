package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Finance;

import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
public interface FinanceDao {
    int getTodayCount(String date);

    List<Finance> get(long categoryId, String date);
}
