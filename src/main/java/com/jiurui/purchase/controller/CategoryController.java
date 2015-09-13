package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.request.CategoryRequest;
import com.jiurui.purchase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by mark on 15/9/13.
 */
@Controller
@RequestMapping("/category")
@ResponseBody
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<List<Category>> find(){
        List<Category> list = categoryService.findAll();
        return new ItemJsonResult<>(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public JsonResult update(@Valid @RequestBody CategoryRequest request, Errors errors,
                                 @PathVariable long id, HttpServletResponse response) throws Exception{
        if (categoryService.selectOne(id) == null) {
            response.setHeader("CATEGORY_FIND_ERROR", "category not exist");
            response.sendError(404, "category not exist");
            return null;
        }
        if (errors.hasErrors()) {
            response.addHeader("ERROR_MESSAGE", "parameter null");
            response.sendError(400, "require parameter missing");
            return JsonResult.ParameterError();
        }
        int result = categoryService.update(id,request);
        if(result == 1) {
            return JsonResult.Success();
        } else {
            JsonResult r = JsonResult.Fail();
            r.setMessage("修改失败");
            return r;
        }
    }
}
