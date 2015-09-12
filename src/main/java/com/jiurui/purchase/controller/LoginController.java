package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.JsonResult;
import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.LoginRequest;
import com.jiurui.purchase.security.LoginUser;
import com.jiurui.purchase.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by mark on 15/9/12.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户名 或者 密码 错误
     */
    public static final int USERNAME_OR_PASSWORD_ERROR = -1;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult submit( @Valid @RequestBody LoginRequest loginParam, Errors errors,
                              HttpSession session, HttpServletResponse response)
                                throws Exception{
        if (errors.hasErrors()) {
            response.addHeader("loginStatus", "parameter error");
            response.sendError(400);
            return JsonResult.ParameterError();
        }

        //用户名，密码验证
        User user = userService.selectUserByUsername(loginParam.getUsername());

        if (user == null || !StringUtils.equals(loginParam.getPassword(), user.getPassword())) {
            response.addHeader("loginStatus", "password error");
            response.sendError(401);
            return new JsonResult(USERNAME_OR_PASSWORD_ERROR);
        }

        LoginUser loginUser = new LoginUser();
        loginUser.setName(user.getUsername());
        loginUser.setUserId(user.getId());
        loginUser.setIsAdmin(user.getRole().equals(Role.ADMIN));
        session.setAttribute(LoginUser.PRINCIPAL_ATTRIBUTE_NAME, loginUser);

        return JsonResult.Success();
    }
}
