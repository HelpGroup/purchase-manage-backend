package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.request.IngredientRequest;
import com.jiurui.purchase.request.UpdateIngredientRequest;
import com.jiurui.purchase.response.ItemJsonResult;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by mark on 15/9/15.
 */
@Controller
@RequestMapping("/category/{categoryId}/ingredient")
@ResponseBody
public class IngredientsController {

    @Autowired
    private IngredientService ingredientService;

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@Valid @RequestBody IngredientRequest request, Errors errors,
                             @PathVariable long categoryId, HttpServletResponse response) throws Exception {
        if (errors.hasErrors()) {
            response.addHeader("USER_CREATE_ERROR", "parameter error");
            response.setStatus(400);
            JsonResult result = JsonResult.ParameterError();
            result.setMessage("请求参数缺失");
            return result;
        }
        Ingredient ingredient = ingredientService.selectOneByName(request.getName(),categoryId);
        if(ingredient != null) {
            response.setHeader("INGREDIENT_CREATE_ERROR", "ingredient exist");
            response.setStatus(422);
            JsonResult result = JsonResult.Fail();
            result.setMessage("菜品已经存在");
            return result;
        }
        int result = ingredientService.create(request);
        if(result==1) return JsonResult.Success();
        else {
            JsonResult r = JsonResult.Fail();
            r.setMessage("创建失败");
            return r;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable long id, HttpServletResponse response) throws Exception{
        if (ingredientService.selectOneById(id) == null) {
            response.setHeader("INGREDIENT_FIND_ERROR", "ingredient not exist");
            response.setStatus(404);
            JsonResult result = JsonResult.Fail();
            result.setMessage("菜品不存在");
            return result;
        }
        int result = ingredientService.delete(id);
        if(result == 1) {
            return JsonResult.Success();
        } else {
            return JsonResult.Fail();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public JsonResult update(@RequestBody UpdateIngredientRequest request,
                             @PathVariable long id, HttpServletResponse response) throws Exception{
        if (ingredientService.selectOneById(id) == null) {
            response.setHeader("INGREDIENT_FIND_ERROR", "ingredient not exist");
            response.setStatus(404);
            JsonResult result = JsonResult.Fail();
            result.setMessage("菜品不存在");
            return result;
        }
        if (request.getName()==null && request.getUnit()==null) {
            response.addHeader("ERROR_MESSAGE", "parameter null");
            response.setStatus(400);
            JsonResult result = JsonResult.ParameterError();
            result.setMessage("请求参数缺失");
            return result;
        }
        int result = ingredientService.update(id,request);
        if(result == 1) {
            return JsonResult.Success();
        } else {
            JsonResult r = JsonResult.Fail();
            r.setMessage("修改失败");
            return r;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<List<Ingredient>> find(){
        List<Ingredient> list = ingredientService.findAll();
        return new ItemJsonResult<>(list);
    }
}
