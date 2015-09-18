package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.CreateUserRequest;
import com.jiurui.purchase.request.PasswordRequest;
import com.jiurui.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by mark on 15/9/12.
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@Valid @RequestBody CreateUserRequest request, Errors errors, HttpServletResponse response) throws Exception {
        if (errors.hasErrors()) {
            response.addHeader("USER_CREATE_ERROR", "parameter error");
            response.setStatus(400);
            JsonResult result = JsonResult.ParameterError();
            result.setMessage("请求参数有误");
            return result;
        }

        User user = userService.selectUserByUsername(request.getUsername());
        if(user != null) {
            response.setHeader("USER_CREATE_ERROR", "username exist");
            response.setStatus(422);
            JsonResult result = JsonResult.Fail();
            result.setMessage("用户名已被使用");
            return result;
        }
        int result = userService.createUser(request);
        if(result==1) return JsonResult.Success();
        else {
            JsonResult r = JsonResult.Fail();
            r.setMessage("创建失败");
            return r;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<List<User>> find(){
        List<User> list = userService.findAll();
        return new ItemJsonResult<>(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ItemJsonResult<User> find(@PathVariable long id, HttpServletResponse response) throws Exception{
        User user = userService.selectUserById(id);
        if(user == null) {
            response.setHeader("USER_FIND_ERROR", "user not exist");
            response.setStatus(404);
            ItemJsonResult<User> result = new ItemJsonResult<>();
            result.setStatus(JsonResult.FAIL);
            result.setMessage("用户不存在");
            return result;
        } else {
            user.setPassword(null);
            return new ItemJsonResult<>(user);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable long id, HttpServletResponse response) throws Exception{
        int result = userService.deleteUser(id);
        if(result == 1) {
            return JsonResult.Success();
        } else  if(result == 0) {
            response.setHeader("USER_FIND_ERROR", "user not exist");
            response.setStatus(404);
            JsonResult fail = JsonResult.Fail();
            fail.setMessage("用户不存在");
            return fail;
        } else if(result == -1){
            response.setHeader("USER_DELETE_ERROR", "no permission");
            response.setStatus(403);
            return null;
        } else {
            return JsonResult.Fail();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public JsonResult changePassword(@Valid @RequestBody PasswordRequest password, Errors errors,
                                     @PathVariable long id, HttpServletResponse response) throws Exception{
        if(userService.selectUserById(id) == null) {
            response.setHeader("USER_FIND_ERROR", "user not exist");
            response.setStatus(404);
            JsonResult fail = JsonResult.Fail();
            fail.setMessage("用户不存在");
            return fail;
        }
        if (errors.hasErrors()) {
            response.addHeader("ERROR_MESSAGE", "parameter null");
            response.setStatus(400);
            JsonResult result = JsonResult.ParameterError();
            result.setMessage("请求参数有误");
            return result;
        }
        int result = userService.changePassword(id, password.getPassword());
        if(result == 1) {
            return JsonResult.Success();
        } else {
            JsonResult r = JsonResult.Fail();
            r.setMessage("修改失败");
            return r;
        }
    }
}
