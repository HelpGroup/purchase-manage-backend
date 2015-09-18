package com.jiurui.purchase.controller;

import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.service.ClosedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by mark on 15/9/17.
 */
@Controller
@RequestMapping("/status/admin/{date}")
@ResponseBody
public class CloseController {

    @Autowired
    private ClosedService closedService;

    @RequestMapping(method = RequestMethod.GET)
    public JsonResult get(@PathVariable String date){
        JsonResult result = new JsonResult();
        int isClosed = closedService.isClosed(date)+1;
        result.setStatus(isClosed);
        if(isClosed>1) result.setMessage("本日已经截单");
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult change(@PathVariable String date, HttpServletResponse response) throws Exception{
        int isClosed = closedService.isClosed(date)+1;
        if(isClosed>1) {
            JsonResult result = new JsonResult();
            result.setStatus(isClosed);
            result.setMessage("本日已经截单");
            return result;
        }
        isClosed = closedService.close(date);
        if(isClosed == -1){
            response.addHeader("accessDenied", "NO PERMISSION");
            response.setStatus(403);
            return JsonResult.Fail();
        }
        if(isClosed == 1) {
            JsonResult result = JsonResult.Success();
            result.setMessage("截单成功");
            return result;
        }
        else return JsonResult.Fail();
    }
}
