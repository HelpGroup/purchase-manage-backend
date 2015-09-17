package com.jiurui.purchase.dao;

/**
 * Created by mark on 15/9/17.
 */
public interface ClosedDao {
    int isClosed(String date);

    int close(String now);
}
