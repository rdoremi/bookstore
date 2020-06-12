package com.book.store.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * (Account)实体类
 *
 * @author makejava
 * @since 2020-03-05 11:19:03
 */
public class Account implements Serializable {
    private static final long serialVersionUID = -90648505979723622L;
    
    private Integer id;
    private Integer year;
    private Integer month;
    private Integer day;

    private String merchantId;
    /**
    * 总收入  total = total-rufund
    */
    private BigDecimal total;
    /**
    * 退款
    */
    private BigDecimal rufund;
    /**
    * 月收入   month = month-rufund
    */
    private BigDecimal monthlyIncome;//月总额
    private BigDecimal dailyReceipts;//日总额
    private BigDecimal annualIncome;//年总额

    private String createTime;
    
    private String updateTime;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getDailyReceipts() {
        return dailyReceipts;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public void setDailyReceipts(BigDecimal dailyReceipts) {
        this.dailyReceipts = dailyReceipts;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", merchantId='" + merchantId + '\'' +
                ", total=" + total +
                ", rufund=" + rufund +
                ", monthlyIncome=" + monthlyIncome +
                ", dailyReceipts=" + dailyReceipts +
                ", annualIncome=" + annualIncome +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}