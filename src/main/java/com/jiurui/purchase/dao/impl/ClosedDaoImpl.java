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
                "SELECT count(*) FROM closed WHERE close_time BETWEEN '"+date+" 00:00:00' AND '"+date+" 23:59:59'",
                Integer.class
        );
    }

    @Override
    public int close(String now) {
        return template.update("INSERT INTO closed(close_time) VALUES ('"+now+"')");
    }

    @Override
    public int open(String today) {
        return template.update("DELETE FROM closed WHERE close_time = '"+today+"'");
    }
}
