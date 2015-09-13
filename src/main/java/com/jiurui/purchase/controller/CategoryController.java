package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.ItemJsonResult;
import com.jiurui.purchase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
