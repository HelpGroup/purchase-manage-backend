package com.jiurui.purchase.model;

import java.math.BigDecimal;

/**
 * Created by mark on 15/9/18.
 */
public class Finance extends Ingredient{
    private Long financeId;
    private Integer needBuy;
    private Integer actualBuy;
    private BigDecimal totalCharge;

    public Long getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Long financeId) {
        this.financeId = financeId;
    }

    public Integer getNeedBuy() {
        return needBuy;
    }

    public void setNeedBuy(Integer needBuy) {
        this.needBuy = needBuy==null?0:needBuy;
    }

    public Integer getActualBuy() {
        return actualBuy;
    }

    public void setActualBuy(Integer actualBuy) {
        this.actualBuy = actualBuy==null?0:actualBuy;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge==null?new BigDecimal(0.00):totalCharge;
    }
}
