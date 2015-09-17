package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.ClosedDao;
import com.jiurui.purchase.service.ClosedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mark on 15/9/17.
 */
@Service
public class ClosedServiceImpl implements ClosedService {

    @Autowired
    private ClosedDao closedDao;
    @Override
    public int isClosed(String date) {
        return closedDao.isClosed(date);
    }
}
