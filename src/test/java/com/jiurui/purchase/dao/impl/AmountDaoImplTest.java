package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.AmountDao;
import com.jiurui.purchase.dao.BaseTestClass;
import com.jiurui.purchase.model.Amount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by mark on 15/9/17.
 */
public class AmountDaoImplTest extends BaseTestClass {

    @Autowired
    private AmountDao amountDao;

    @Test
    public void testGetTodayCount() throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date.getTime());
        int count = amountDao.getTodayCount(today, 2);
        assertEquals(2,count);
    }

    @Test
    public void testGet() throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date.getTime());
        int amount = amountDao.getListByIngredientAndUser(4,2,today);
        assertEquals(10,amount);
    }

    @Test
    public void testUpdate() throws Exception {
        Amount amount = new Amount();
        amount.setIngredientId(5L);
        amount.setUserId(2L);
        amount.setAmount(50);
        amountDao.update(amount);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date.getTime());
        assertEquals(50, amountDao.getListByIngredientAndUser(5,2,today));
    }
}