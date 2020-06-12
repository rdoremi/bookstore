package com.book.store.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * (MyAccount)实体类
 *
 * @author makejava
 * @since 2020-03-08 11:25:58
 */
public class MyAccount implements Serializable {
    private static final long serialVersionUID = -42304320298065449L;
    
    private Integer id;

    private Integer status;

    private BigDecimal balance;
    
    private String merchantId;

    private String account;

    private BigDecimal drawMoney;
    
    private String applicant;
    
    private String time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getDrawMoney() {
        return drawMoney;
    }

    public void setDrawMoney(BigDecimal drawMoney) {
        this.drawMoney = drawMoney;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}