package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.ClosedDao;
import com.jiurui.purchase.service.ClosedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public int close(String date) {
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String today = sdf.format(d);
//        try {
//            long dateLong = sdf.parse(date).getTime();
//            long todayLong = sdf.parse(today).getTime();
//            if(todayLong-dateLong != 0) return -1;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return closedDao.close(date+" 12:00:00");
    }

    @Override
    public int open(String date) {
        return closedDao.open(date+" 12:00:00");
    }
}
