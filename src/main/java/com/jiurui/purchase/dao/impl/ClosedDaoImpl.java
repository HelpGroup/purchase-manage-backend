package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.ClosedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by mark on 15/9/17.
 */
@Repository
public class ClosedDaoImpl implements ClosedDao {

    @Autowired
    private JdbcTemplate template;
    @Override
    public int isClosed(String date) {
        return template.queryForObject(
                "SELECT count(*) FROM closed WHERE close_time > '"+date+" 00:00:00'",
                Integer.class
        );
    }
}
