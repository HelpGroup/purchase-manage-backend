package com.jiurui.purchase.controller;

import com.jiurui.purchase.request.CloseRequest;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.service.ClosedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by mark on 15/9/17.
 */
@Controller
@RequestMapping("/amount/admin/{date}")
@ResponseBody
public class CloseController {

    @Autowired
    private ClosedService closedService;

//    @RequestMapping(method = RequestMethod.GET)
//    public JsonResult get(@PathVariable String date){
//        JsonResult result = new JsonResult();
//        int isClosed = closedService.isClosed(date)+1;
//        result.setStatus(isClosed);
//        if(isClosed>1) result.setMessage("本日已经截单");
//        return result;
//    }

    @RequestMapping(method = RequestMethod.PATCH)
    public JsonResult change(@PathVariable String date, @RequestBody @Valid CloseRequest request,
                             Errors errors, HttpServletResponse response) throws Exception{
        if (errors.hasErrors()) {
            response.setStatus(400);
            JsonResult result = JsonResult.ParameterError();
            result.setMessage("请求参数有误");
            return result;
        }
        int isClosed = closedService.isClosed(date)+1;
        if(isClosed>1 && request.isLock()) {
            JsonResult result = new JsonResult();
            result.setStatus(isClosed);
            result.setMessage("本日已经截单");
            return result;
        }
        isClosed = request.isLock()?closedService.close(date):closedService.open(date);
//        暂时取消对截单日期的限制
//        if(isClosed == -1){
//            response.addHeader("accessDenied", "NO PERMISSION");
//            response.setStatus(403);
//            JsonResult result = JsonResult.Fail();
//            result.setMessage("只能修改当天截单状态");
//            return result;
//        }
        if(isClosed == 1) {
            JsonResult result = JsonResult.Success();
            result.setMessage("修改截单状态成功");
            return result;
        }
        else return JsonResult.Fail();
    }
}
