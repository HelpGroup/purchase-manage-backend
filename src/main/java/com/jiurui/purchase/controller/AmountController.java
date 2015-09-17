package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.AmountRequest;
import com.jiurui.purchase.response.AggregateResponse;
import com.jiurui.purchase.response.CategoryResponse;
import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.service.AmountService;
import com.jiurui.purchase.service.ClosedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
@Controller
@ResponseBody
@RequestMapping("/amount")
public class AmountController {

    @Autowired
    private AmountService amountService;
    @Autowired
    private ClosedService closedService;

    @RequestMapping(value = "/branch/{date}", method = RequestMethod.GET)
    public ItemJsonResult<List<Category>> list(@PathVariable String date, HttpSession session){
        ItemJsonResult<List<Category>> result = new ItemJsonResult<>();
        User user = (User) session.getAttribute("user");
        List<Category> list = amountService.list(user.getId(), date);
        result.setItem(list);
        int isClosed = closedService.isClosed(date)+1;
        result.setStatus(isClosed);
        if(isClosed>1) result.setMessage("本日已经截单");
        if(null == list) result.setStatus(JsonResult.FAIL);
        return result;
    }


    @RequestMapping(value = "/admin/{date}", method = RequestMethod.GET)
    public ItemJsonResult<AggregateResponse> find(@PathVariable String date, HttpSession session,
                                                  HttpServletResponse httpServletResponse) throws Exception{
        User user = (User) session.getAttribute("user");
        if(user.getRoleId() == Role.BRANCH){
            httpServletResponse.addHeader("accessDenied", "NO PERMISSION");
            httpServletResponse.sendError(403);
            return null;
        }
        ItemJsonResult<AggregateResponse> result = new ItemJsonResult<>();
        List<CategoryResponse> list = amountService.find(date);
        AggregateResponse response = new AggregateResponse();
        response.setList(list);
        result.setItem(response);
        int isClosed = closedService.isClosed(date)+1;
        result.setStatus(isClosed);
        if(isClosed>1) result.setMessage("本日已经截单");
        return result;
    }

    @RequestMapping(value = "/branch/today", method = RequestMethod.POST)
    public JsonResult input(@RequestBody AmountRequest request, HttpSession session,
                            HttpServletResponse response) throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        int isClosed = closedService.isClosed(today)+1;
        if(isClosed ==2) {
            response.addHeader("accessDenied", "NO PERMISSION");
            response.sendError(403);
            return null;
        }
        try {
            User user = (User) session.getAttribute("user");
            return amountService.input(request, user.getId())==1?JsonResult.Success():JsonResult.Fail();
        } catch (Exception e) {
            return JsonResult.Fail();
        }
    }

}
