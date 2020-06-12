package com.book.store.service.impl;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Account;
import com.book.store.entity.Order;
import com.book.store.mapper.AccountMapper;
import com.book.store.mapper.OrderMapper;
import com.book.store.mapper.ProductMapper;
import com.book.store.service.AccountService;
import com.book.store.utils.BigDecimalUtil;
import com.book.store.vo.EchartsVo;
import com.book.store.vo.TradeStatisticsVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ServerResponse addAccount(Account account) {
        return null;
    }

    @Override
    public ServerResponse selectAll() {
        List<Account> accountList = accountMapper.selectAll();
        if (accountList.size() == 0) {

            return ServerResponse.createBySuccess(0);
        }
        BigDecimal total = new BigDecimal("0");
        for (Account account : accountList) {
            total = BigDecimalUtil.add(account.getDailyReceipts().doubleValue(), total.doubleValue());
        }
        return ServerResponse.createBySuccess(total);
    }

    @Override
    public ServerResponse selectByMerchantId(String merchantId) {
        List<Account> accountList = accountMapper.getOneByMerchantId(merchantId);
        ServerResponse<PageInfo> productByMerchantId = productService.getProductByMerchantId(merchantId, 1, 1);
        List<Order> orderList = orderMapper.selectOrderByMerchantIdUseTrades(merchantId);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] time = simpleDateFormat.format(date).split("-");
        int year = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]);
        int day = Integer.parseInt(time[2]);
        List<Account> thisYear = accountMapper.selectByMerchantYear(merchantId, year);
        List<Account> thisMonth = accountMapper.selectByMerchantMonth(merchantId, year, month);
        List<Account> today = accountMapper.selectByMerchantMonthDay(merchantId, year, month, day);

        TradeStatisticsVo account = new TradeStatisticsVo();

        account.setTradeTimes(orderList.size());
        account.setProductTotal(productByMerchantId.getData().getTotal());
        if (accountList.size() == 0) {

           /* account.setDailyReceipts(new BigDecimal("0"));
            account.setMonthlyIncome(new BigDecimal("0"));
            account.setTotal(new BigDecimal("0"));
            account.setRufund(new BigDecimal("0"));*/
            return ServerResponse.createBySuccess(account);
        }
        account.setTotal(accountList.get(0).getTotal());
        if (thisYear.size() == 0) {
          /*  account.setAnnualIncome(new BigDecimal("0"));
            account.setDailyReceipts(new BigDecimal("0"));
            account.setMonthlyIncome(new BigDecimal("0"));
            account.setRufund(new BigDecimal("0"));*/
            return ServerResponse.createBySuccess(account);
        }
        account.setAnnualIncome(thisYear.get(0).getAnnualIncome());
        if (thisMonth.size() == 0) {
           /* account.setDailyReceipts(new BigDecimal("0"));
            account.setMonthlyIncome(new BigDecimal("0"));
            account.setRufund(new BigDecimal("0"));*/
            return ServerResponse.createBySuccess(account);
        }

        account.setMonthlyIncome(thisMonth.get(0).getMonthlyIncome());
        account.setRufund(thisMonth.get(0).getRufund());
        if (today.size() == 0) {
            /*account.setDailyReceipts(new BigDecimal("0"));*/
            return ServerResponse.createBySuccess(account);
        }
        account.setDailyReceipts(today.get(0).getDailyReceipts());
        return ServerResponse.createBySuccess(account);
    }

    @Override
    public ServerResponse selectByMerchantIdAnMonth(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Account> accountList = accountMapper.getOneByMerchantId(merchantId);

        List<Integer> days = new ArrayList<>();
        EchartsVo echartsVo = new EchartsVo();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] time = simpleDateFormat.format(date).split("-");

        List<BigDecimal> dayList = new ArrayList<BigDecimal>();
        List<BigDecimal> monthList = new ArrayList<BigDecimal>();
        List<BigDecimal> yearList = new ArrayList<BigDecimal>();
        List<BigDecimal> totalList = new ArrayList<BigDecimal>();
        List<BigDecimal> refundlList = new ArrayList<BigDecimal>();

        int year = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]);
        int day = Integer.parseInt(time[2]);

        List<Account> thisYear = accountMapper.selectByMerchantYear(merchantId, year);
        List<Account> thisMonth = accountMapper.selectByMerchantMonth(merchantId, year, month);

        for (int i = 1; i <= day; i++) {
            days.add(i);
            if (thisYear.size() != 0 && accountList.size() != 0) {
                Account today = accountMapper.selectByMerchantMonthOneDay(merchantId, year, month, i);
                if (today == null) {
                    if (i == 1) {
                        monthList.add(new BigDecimal("0"));
                    } else {
                        monthList.add(monthList.get(i - 2));
                    }
                    if (thisMonth.size() == 0) {
                        yearList.add(accountList.get(0).getAnnualIncome());
                    } else {
                        yearList.add(accountList.get(0).getAnnualIncome());
                    }

                    totalList.add(accountList.get(0).getTotal());
                    refundlList.add(new BigDecimal("0"));
                    dayList.add(new BigDecimal("0"));
                } else {
                    dayList.add(today.getDailyReceipts());
                    monthList.add(today.getMonthlyIncome());
                    yearList.add(today.getAnnualIncome());
                    totalList.add(today.getTotal());
                    if (today.getRufund() == null) {
                        refundlList.add(new BigDecimal("0"));
                    } else {
                        refundlList.add(today.getRufund());
                    }

                }
            } else if (thisYear.size() == 0 && accountList.size() != 0) {//今年没有订单的情况
                yearList.add(new BigDecimal("0"));
                totalList.add(accountList.get(0).getTotal());
                dayList.add(new BigDecimal("0"));
                refundlList.add(new BigDecimal("0"));
                monthList.add(new BigDecimal("0"));
            } else if (accountList.size() == 0) {
                yearList.add(new BigDecimal("0"));
                totalList.add(new BigDecimal("0"));
                dayList.add(new BigDecimal("0"));
                refundlList.add(new BigDecimal("0"));
                monthList.add(new BigDecimal("0"));
            }
        }
        echartsVo.setDayList(dayList);
        echartsVo.setMonthList(monthList);
        echartsVo.setYearList(yearList);
        echartsVo.setTotalList(totalList);
        echartsVo.setDays(days);
        echartsVo.setRefundList(refundlList);
        return ServerResponse.createBySuccess(echartsVo);
    }
}
