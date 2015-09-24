package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.Amount;
import com.jiurui.purchase.model.Category;
import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.AmountRequest;
import com.jiurui.purchase.response.*;
import com.jiurui.purchase.service.AmountService;
import com.jiurui.purchase.service.ClosedService;
import com.jiurui.purchase.service.UserService;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mark on 15/9/16.
 */
@Controller
@ResponseBody
@RequestMapping("/amount")
public class AmountController {

    @Autowired
    private AmountService amountService;
    @Autowired
    private ClosedService closedService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/branch/{date}", method = RequestMethod.GET)
    public ItemJsonResult<AmountResult> list(@PathVariable String date, HttpSession session){
        ItemJsonResult<AmountResult> result = new ItemJsonResult<>();
        AmountResult amountResult = new AmountResult();

        User user = (User) session.getAttribute("user");
        List<Category> list = amountService.list(user.getId(), date);

        if(null == list) result.setStatus(JsonResult.FAIL);

        amountResult.setCategories(list);
        int isClosed = closedService.isClosed(date)+1;
        if(isClosed>1){
            amountResult.setLock(true);
        }

        result.setItem(amountResult);
        return result;
    }


    @RequestMapping(value = "/admin/{date}", method = RequestMethod.GET)
    public ItemJsonResult<AggregateResponse> find(@PathVariable String date, HttpSession session,
                                                  HttpServletResponse httpServletResponse) throws Exception{
        User user = (User) session.getAttribute("user");
        if(user.getRoleId() == Role.BRANCH){
            httpServletResponse.addHeader("accessDenied", "NO PERMISSION");
            httpServletResponse.setStatus(403);
            ItemJsonResult<AggregateResponse> result = new ItemJsonResult<>();
            result.setStatus(JsonResult.FAIL);
            return result;
        }
        ItemJsonResult<AggregateResponse> result = new ItemJsonResult<>();
        List<CategoryResponse> list = amountService.find(date);
        AggregateResponse response = new AggregateResponse();
        response.setList(list);
        response.setUsers(userService.findBranches());
        int isClosed = closedService.isClosed(date)+1;
        if(isClosed>1){
            response.setLock(true);
        }
        result.setItem(response);
        return result;
    }

    @RequestMapping(value = "/branch/today", method = RequestMethod.POST)
    public JsonResult input(@RequestBody AmountRequest request, HttpSession session,
                            HttpServletResponse response) throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        int isClosed = closedService.isClosed(today)+1;
        if(isClosed ==2) {
            response.addHeader("accessDenied", "NO PERMISSION");
            response.setStatus(403);
            return JsonResult.Fail();
        }
        try {
            User user = (User) session.getAttribute("user");
            return amountService.input(request, user.getId())==1?JsonResult.Success():JsonResult.Fail();
        } catch (Exception e) {
            return JsonResult.Fail();
        }
    }

    @RequestMapping(value = "/{date}/csv", method = RequestMethod.GET)
    public void csvDownLoad(@PathVariable String date, HttpServletResponse httpServletResponse) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "purchase-amount-";
        fileName += dateFormat.format(new Date());
        fileName += ".csv";

        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        String path = System.getProperty("java.io.tmpdir") + "\\tmp.csv";
        File file = new File(path);
        FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"gbk");
        BufferedWriter bw = new BufferedWriter(fwwe);

        List<CategoryResponse> list = amountService.find(date);
        bw.write("分类,菜品,单位,总数量,");
        List<User> users = userService.findBranches();
        for(User user : users){
            bw.write(user.getUsername()+",");
        }
        bw.write("\n");
        for(CategoryResponse categoryResponse : list){
            bw.write(categoryResponse.getName());
            List<IngredientResponse> ingredientList = categoryResponse.getIngredientList();
            for(IngredientResponse ingredientResponse : ingredientList){
                bw.write(","+ingredientResponse.getName()+","+ingredientResponse.getUnit()+","+ingredientResponse.getAmount()+",");
                List<Amount> amounts = ingredientResponse.getAmounts();
                for(Amount amount : amounts){
                    bw.write(amount.getAmount()+",");
                }
                bw.write("\n");
            }
        }

        bw.close();
        fwwe.close();
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(httpServletResponse.getOutputStream());
            byte[] buff = new byte[2048];
            while (true) {
                int bytesRead;
                if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
                    break;
                }
                out.write(buff, 0, bytesRead);
            }
            file.deleteOnExit();
        }
        catch (IOException e) {
            throw e;
        }
        finally{
            try {
                if(bis != null){
                    bis.close();
                }
                if(out != null){
                    out.flush();
                    out.close();
                }
            }
            catch (IOException e) {
                throw e;
            }
        }

    }

}
