package com.jiurui.purchase.request;

import com.jiurui.purchase.model.FinanceCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by mark on 15/9/18.
 */
public class FinanceRequest {
    private List<FinanceCategory> financeList;

    public List<FinanceCategory> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(List<FinanceCategory> financeList) {
        this.financeList = financeList;
    }
}
