package com.book.store.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EchartsVo {
    private List<BigDecimal> dayList;
    private List<BigDecimal> monthList;
    private List<BigDecimal> yearList;
    private List<BigDecimal> refundList;
    private List<BigDecimal> totalList;
    private List<Integer> days;//0-今天

    public List<BigDecimal> getDayList() {
        return dayList;
    }

    public void setDayList(List<BigDecimal> dayList) {
        this.dayList = dayList;
    }

    public List<BigDecimal> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<BigDecimal> monthList) {
        this.monthList = monthList;
    }

    public List<BigDecimal> getYearList() {
        return yearList;
    }

    public void setYearList(List<BigDecimal> yearList) {
        this.yearList = yearList;
    }

    public List<BigDecimal> getRefundList() {
        return refundList;
    }

    public void setRefundList(List<BigDecimal> refundList) {
        this.refundList = refundList;
    }

    public List<BigDecimal> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<BigDecimal> totalList) {
        this.totalList = totalList;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }
}
