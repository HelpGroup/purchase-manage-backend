package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Ingredient;
import com.jiurui.purchase.request.IngredientRequest;
import com.jiurui.purchase.response.JsonResult;
import com.jiurui.purchase.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by mark on 15/9/15.
 */
@Controller
@RequestMapping("/ingredient")
@ResponseBody
public class IngredientsController {

    @Autowired
    private IngredientService ingredientService;

    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@Valid @RequestBody IngredientRequest request, Errors errors,
                             HttpServletResponse response) throws Exception {
        if (errors.hasErrors()) {
            response.addHeader("USER_CREATE_ERROR", "parameter error");
            response.sendError(400);
            return JsonResult.ParameterError();
        }
        Ingredient ingredient = ingredientService.selectOneByName(request.getName());
        if(ingredient != null) {
            response.setHeader("INGREDIENT_CREATE_ERROR", "ingredient exist");
            response.sendError(409);
            return null;
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
            response.sendError(404, "ingredient not exist");
            return null;
        }
        int result = ingredientService.delete(id);
        if(result == 1) {
            return JsonResult.Success();
        } else {
            return JsonResult.Fail();
        }
    }
}
