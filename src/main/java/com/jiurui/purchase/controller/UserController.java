package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.JsonResult;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.CreateUserRequest;
import com.jiurui.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by mark on 15/9/12.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody CreateUserRequest request, Errors errors, HttpServletResponse response) throws Exception {
        if (errors.hasErrors()) {
            response.addHeader("USER_CREATE_ERROR", "parameter error");
            response.sendError(400);
            return JsonResult.ParameterError();
        }

        User user = userService.selectUserByUsername(request.getUsername());
        if(user != null) {
            response.setHeader("USER_CREATE_ERROR", "username exist");
            response.sendError(422);
            return new JsonResult(JsonResult.ERROR);
        }
        int result = userService.createUser(request);
        if(result==1) return JsonResult.Success();
        else return JsonResult.Fail();
    }
}
