package com.jiurui.purchase.controller;

import com.jiurui.purchase.model.*;
import com.jiurui.purchase.request.FinanceRequest;
import com.jiurui.purchase.response.*;
import com.jiurui.purchase.service.ClosedService;
import com.jiurui.purchase.service.FinanceService;
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
 * Created by mark on 15/9/18.
 */
@Controller
@ResponseBody
@RequestMapping("/charge/admin/{date}")
public class FinanceController {

    @Autowired
    private FinanceService financeService;
    @Autowired
    private ClosedService closedService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ItemJsonResult<FinanceResult> charge(@PathVariable String date, HttpSession session,
                                                   HttpServletResponse response){
        User user = (User) session.getAttribute("user");
        ItemJsonResult<FinanceResult> result = new ItemJsonResult<>(null);
        if(user.getRoleId() == Role.BRANCH){
            response.addHeader("accessDenied", "NO PERMISSION");
            response.setStatus(403);
            result.setStatus(JsonResult.FAIL);
            return result;
        }
        FinanceResult financeResult = new FinanceResult();
        if(closedService.isClosed(date)==0){
            financeResult.setStatus(2);
            financeResult.setMessage("本日还未截单");
        }else {
            boolean isCharged = financeService.getTodayCount(date);
            List<FinanceCategory> list = financeService.find(date);
            if (isCharged) {
                financeResult.setStatus(3);
                financeResult.setMessage("本日金额已经录入完成");
            } else {
                financeResult.setStatus(1);
            }
            financeResult.setChargeList(list);
            financeResult.setUsers(userService.findBranchesWithAmount(date));
        }
        result.setItem(financeResult);
        return result;
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public JsonResult save(@PathVariable String date, @RequestBody FinanceRequest request,
                           HttpSession session, HttpServletResponse response){
        User user = (User) session.getAttribute("user");
        if(user.getRoleId() == Role.BRANCH){
            ItemJsonResult<List<FinanceCategory>> result = new ItemJsonResult<>(null);
            response.addHeader("accessDenied", "NO PERMISSION");
            response.setStatus(403);
            result.setStatus(JsonResult.FAIL);
            return result;
        }
        if(financeService.save(request,date) == 1) {
            return JsonResult.Success();
        } else {
            return JsonResult.Fail();
        }
    }

    @RequestMapping(value = "/csv", method = RequestMethod.GET)
    public void csvDownLoad(@PathVariable String date, HttpServletResponse httpServletResponse) throws Exception {
        String fileName = date+"采购金额.csv";

        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(),"iso-8859-1"));
        BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        String path = System.getProperty("java.io.tmpdir") + "\\tmp.csv";
        File file = new File(path);
        FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"gbk");
        BufferedWriter bw = new BufferedWriter(fwwe);

        List<FinanceCategory> list = financeService.find(date);
        List<User> users = userService.findBranches();
        bw.write(",,");
        for (User user : users) {
            bw.write(user.getUsername()+",,,");
        }
        bw.write("\n大类,菜品,单位,");
        for (User user : users) {
            bw.write("需求数量,实际采购量,采购总价,");
        }
        bw.write("\n");
        for(FinanceCategory financeCategory : list){
            bw.write(financeCategory.getCategoryName());
            List<FinanceIngredient> financeIngredients = financeCategory.getIngredients();
            for(FinanceIngredient financeIngredient : financeIngredients){
                bw.write(","+financeIngredient.getName()+","+financeIngredient.getUnit());
                for (Finance finance : financeIngredient.getFinances()) {
                    bw.write(","+finance.getAmount()+","+finance.getActualBuy()+","+finance.getTotalCharge());
                }
                bw.write(",\n");
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
