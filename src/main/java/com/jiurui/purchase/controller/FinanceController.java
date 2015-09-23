package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.FinanceCategory;
import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.FinanceRequest;
import com.jiurui.purchase.response.*;
import com.jiurui.purchase.service.ClosedService;
import com.jiurui.purchase.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
@Controller
@ResponseBody
@RequestMapping("/charge/admin/{date}")
public class FinanceController {

    @Autowired
    private FinanceService financeService;
    @Autowired
    private ClosedService closedService;

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<FinanceResult> charge(@PathVariable String date, HttpSession session,
                                                   HttpServletResponse response){
        User user = (User) session.getAttribute("user");
        ItemJsonResult<FinanceResult> result = new ItemJsonResult<>(null);
        if(user.getRoleId() == Role.BRANCH){
            response.addHeader("accessDenied", "NO PERMISSION");
            response.setStatus(403);
            result.setStatus(JsonResult.FAIL);
            return result;
        }
        FinanceResult financeResult = new FinanceResult();
        if(closedService.isClosed(date)==0){
            financeResult.setStatus(2);
            financeResult.setMessage("本日还未截单");
        }
        boolean isCharged = financeService.getTodayCount(date);
        List<FinanceCategory> list = financeService.find(date, isCharged);
        if(isCharged) {
            financeResult.setStatus(3);
            financeResult.setMessage("本日金额已经录入完成");
        }else{
            financeResult.setStatus(1);
        }
        financeResult.setChargeList(list);
        result.setItem(financeResult);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult save(@PathVariable String date, @RequestBody FinanceRequest request,
                           HttpSession session, HttpServletResponse response){
        User user = (User) session.getAttribute("user");
        if(user.getRoleId() == Role.BRANCH){
            ItemJsonResult<List<FinanceCategory>> result = new ItemJsonResult<>(null);
            response.addHeader("accessDenied", "NO PERMISSION");
            response.setStatus(403);
            result.setStatus(JsonResult.FAIL);
            return result;
        }
        if(financeService.save(request,date) == 1) {
            return JsonResult.Success();
        } else {
            return JsonResult.Fail();
        }
    }
}
