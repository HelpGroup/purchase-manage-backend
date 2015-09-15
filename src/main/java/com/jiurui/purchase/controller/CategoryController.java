package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.request.CategoryRequest;
import com.jiurui.purchase.service.CategoryService;
import com.jiurui.purchase.service.IngredientService;
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
    @Autowired
    private IngredientService ingredientService;

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<List<Category>> find(){
        List<Category> list = categoryService.findAll();
        return new ItemJsonResult<>(list);
    }

    @RequestMapping(value = "/{id}/ingredients", method = RequestMethod.GET)
    public ItemJsonResult<Category> getOne(@PathVariable long id, HttpServletResponse response)
    throws Exception {
        Category category = categoryService.selectOne(id);
        if (category == null) {
            response.setHeader("CATEGORY_FIND_ERROR", "category not exist");
            response.sendError(404, "category not exist");
            return null;
        }
        List<Ingredient> list = ingredientService.findAllByCategoryId(id);
        category.setIngredientList(list);
        return new ItemJsonResult<>(category);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable long id, HttpServletResponse response) throws Exception {
        if (categoryService.selectOne(id) == null) {
            response.setHeader("CATEGORY_FIND_ERROR", "category not exist");
            response.sendError(404, "category not exist");
            return null;
        }
        int result = categoryService.delete(id);
        if(result == 1) {
            return JsonResult.Success();
        } else {
            return JsonResult.Fail();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@Valid @RequestBody CategoryRequest request, Errors errors,
                             HttpServletResponse response) throws Exception {
        if (errors.hasErrors()) {
            response.addHeader("USER_CREATE_ERROR", "parameter error");
            response.sendError(400);
            return JsonResult.ParameterError();
        }

        Category category = categoryService.selectOnerByName(request.getName());
        if(category != null) {
            response.setHeader("CATEGORY_CREATE_ERROR", "category exist");
            response.sendError(422);
            JsonResult result = JsonResult.Fail();
            result.setMessage("菜品大类已经存在");
            return result;
        }
        int result = categoryService.create(request);
        if(result==1) return JsonResult.Success();
        else {
            JsonResult r = JsonResult.Fail();
            r.setMessage("创建失败");
            return r;
        }
    }
}
