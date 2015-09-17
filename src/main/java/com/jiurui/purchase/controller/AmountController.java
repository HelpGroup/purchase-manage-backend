package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
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

import javax.servlet.http.HttpSession;
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

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<List<Category>> list(HttpSession session){
        ItemJsonResult<List<Category>> result = new ItemJsonResult<>();
        User user = (User) session.getAttribute("user");
        List<Category> list = amountService.list(user.getId());
        result.setItem(list);
        if(null == list) result.setStatus(JsonResult.FAIL);
        return result;
    }


    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public ItemJsonResult<AggregateResponse> find(@PathVariable String date){
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

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult input(@RequestBody AmountRequest request, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            return amountService.input(request, user.getId())==1?JsonResult.Success():JsonResult.Fail();
        } catch (Exception e) {
            return JsonResult.Fail();
        }
    }

}
