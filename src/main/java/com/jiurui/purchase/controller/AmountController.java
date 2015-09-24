package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.AmountRequest;
import com.jiurui.purchase.response.*;
import com.jiurui.purchase.service.AmountService;
import com.jiurui.purchase.service.ClosedService;
import com.jiurui.purchase.service.UserService;
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
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/branch/{date}", method = RequestMethod.GET)
    public ItemJsonResult<AmountResult> list(@PathVariable String date, HttpSession session){
        ItemJsonResult<AmountResult> result = new ItemJsonResult<>();
        AmountResult amountResult = new AmountResult();

        User user = (User) session.getAttribute("user");
        List<Category> list = amountService.list(user.getId(), date);

        if(null == list) result.setStatus(JsonResult.FAIL);

        amountResult.setCategories(list);
        int isClosed = closedService.isClosed(date)+1;
        if(isClosed>1){
            amountResult.setLock(true);
        }

        result.setItem(amountResult);
        return result;
    }


    @RequestMapping(value = "/admin/{date}", method = RequestMethod.GET)
    public ItemJsonResult<AggregateResponse> find(@PathVariable String date, HttpSession session,
                                                  HttpServletResponse httpServletResponse) throws Exception{
        User user = (User) session.getAttribute("user");
        if(user.getRoleId() == Role.BRANCH){
            httpServletResponse.addHeader("accessDenied", "NO PERMISSION");
            httpServletResponse.setStatus(403);
            ItemJsonResult<AggregateResponse> result = new ItemJsonResult<>();
            result.setStatus(JsonResult.FAIL);
            return result;
        }
        ItemJsonResult<AggregateResponse> result = new ItemJsonResult<>();
        List<CategoryResponse> list = amountService.find(date);
        AggregateResponse response = new AggregateResponse();
        response.setList(list);
        response.setUsers(userService.findBranches());
        int isClosed = closedService.isClosed(date)+1;
        if(isClosed>1){
            response.setLock(true);
        }
        result.setItem(response);
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
            response.setStatus(403);
            return JsonResult.Fail();
        }
        try {
            User user = (User) session.getAttribute("user");
            return amountService.input(request, user.getId())==1?JsonResult.Success():JsonResult.Fail();
        } catch (Exception e) {
            return JsonResult.Fail();
        }
    }

}
