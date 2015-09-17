package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.BaseTestClass;
import com.jiurui.purchase.response.CategoryResponse;
import com.jiurui.purchase.service.AmountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mark on 15/9/17.
 */
public class AmountServiceImplTest extends BaseTestClass {

    @Autowired
    private AmountService amountService;
    @Test
    public void testFind() throws Exception {
        List<CategoryResponse> responses = amountService.find("2015-09-17");
        assertEquals(2,responses.size());
    }
}