package com.jiurui.purchase.controller;

import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.LoginRequest;
import com.jiurui.purchase.response.UserLoginResult;
import com.jiurui.purchase.service.TokenService;
import com.jiurui.purchase.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by mark on 15/9/12.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    /**
     * 用户名 或者 密码 错误
     */
    public static final int USERNAME_OR_PASSWORD_ERROR = -1;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ItemJsonResult<UserLoginResult> submit( @Valid @RequestBody LoginRequest loginParam, Errors errors,
                              HttpServletResponse response) throws Exception{
        if (errors.hasErrors()) {
            response.addHeader("loginStatus", "parameter error");
            response.sendError(400);
            return null;
        }

        //用户名，密码验证
        User user = userService.selectUserByUsername(loginParam.getUsername());

        if (user == null || !StringUtils.equals(loginParam.getPassword(), user.getPassword())) {
            response.addHeader("loginStatus", "password error");
            response.sendError(422);
            ItemJsonResult<UserLoginResult> result = new ItemJsonResult<>(null);
            result.setStatus(JsonResult.FAIL);
            result.setMessage("username or password error");
            return result;
        }

        String token = tokenService.createToken();
        int r = tokenService.persistence(user.getId(),token);
        if(r==1) {
            UserLoginResult userLoginResult = new UserLoginResult();
            userLoginResult.setId(user.getId());
            userLoginResult.setUsername(user.getUsername());
            userLoginResult.setRoleId(user.getRoleId());
            userLoginResult.setToken(token);
            ItemJsonResult<UserLoginResult> result = new ItemJsonResult<>(userLoginResult);
            result.setStatus(JsonResult.SUCCESS);
            return result;
        } else {
            ItemJsonResult<UserLoginResult> result = new ItemJsonResult<>(null);
            result.setStatus(JsonResult.FAIL);
            return result;
        }
    }
}
