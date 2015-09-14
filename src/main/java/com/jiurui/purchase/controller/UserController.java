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
            response.sendError(404);
            return null;
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
            response.sendError(404);
            return null;
        } else if(result == -1){
            response.setHeader("USER_DELETE_ERROR", "no permission");
            response.sendError(403);
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
            response.sendError(404);
            return null;
        }
        if (errors.hasErrors()) {
            response.addHeader("ERROR_MESSAGE", "parameter null");
            response.sendError(400, "require parameter missing");
            return JsonResult.ParameterError();
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
