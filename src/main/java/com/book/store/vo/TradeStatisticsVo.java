package com.book.store.vo;

import java.math.BigDecimal;

public class TradeStatisticsVo {

    private BigDecimal total;
    private BigDecimal rufund;
    private BigDecimal monthlyIncome;//月总额
    private BigDecimal dailyReceipts;//日总额
    private BigDecimal annualIncome;//年总额
    private Long productTotal;
    private int tradeTimes;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getRufund() {
        return rufund;
    }

    public void setRufund(BigDecimal rufund) {
        this.rufund = rufund;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getDailyReceipts() {
        return dailyReceipts;
    }

    public void setDailyReceipts(BigDecimal dailyReceipts) {
        this.dailyReceipts = dailyReceipts;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Long getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Long productTotal) {
        this.productTotal = productTotal;
    }

    public int getTradeTimes() {
        return tradeTimes;
    }

    public void setTradeTimes(int tradeTimes) {
        this.tradeTimes = tradeTimes;
    }
}
