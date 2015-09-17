package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.AmountRequest;
import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.service.AmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<List<Category>> list(HttpSession session){
        ItemJsonResult<List<Category>> result = new ItemJsonResult<>();
        User user = (User) session.getAttribute("user");
        List<Category> list = amountService.list(user.getId());
        result.setItem(list);
        if(null == list) result.setStatus(JsonResult.FAIL);
        return result;
    }


    public ItemJsonResult<List<Category>> find(String date){
        ItemJsonResult<List<Category>> result = new ItemJsonResult<>();
        List<Category> list = amountService.find(date);
        result.setItem(list);
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
