package com.book.store.service.impl;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.*;
import com.book.store.mapper.*;
import com.book.store.service.OrderService;
import com.book.store.utils.BigDecimalUtil;
import com.book.store.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AccountMapper accountMapper;//交易
    @Autowired
    private MyAccountMapper myAccountMapper;//我的账户

    @Override
    public ServerResponse<Order> addOrder(List<CartVo> cartVos, BigDecimal total, Integer addressId, Integer userId) {
        if (cartVos == null || addressId < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        try {
            Order order = new Order();

            order.setAddressId(addressId);
            Date date = new Date();
            String strDateFormat = "yyyyMMddHHmmssSSS";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            order.setOrderNo(sdf.format(date));
            order.setUserId(userId);
            order.setStatus(Const.Order.NOT_PAY);
            order.setPayment(total);
            order.setPostage(0);


            int rowCount1 = 0;
            for (CartVo cartVo : cartVos) {
                order.setMerchantId(cartVo.getMerchantId());
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo(order.getOrderNo());
                orderItem.setProductId(cartVo.getProductId());
                orderItem.setQuantity(cartVo.getQuantity());
                orderItem.setProductName(cartVo.getProductName());
                orderItem.setUserId(userId);

                orderItem.setAuthorName(cartVo.getAuthorName());
                orderItem.setPublish(cartVo.getPublish());
                orderItem.setNumber(cartVo.getNumber());
                orderItem.setPublishDate(cartVo.getPublishDate());


                orderItem.setMerchantId(cartVo.getMerchantId());
                orderItem.setProductImage(cartVo.getImages());
                orderItem.setTotalPrice(cartVo.getPriceTotal());
                rowCount1 = orderItemMapper.insert(orderItem);

            }
            int rowCount2 = orderMapper.addOrder(order);

            if (rowCount1 > 0 && rowCount2 > 0) {
                return ServerResponse.createBySuccess(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse<Order> getOrderByOderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Order order = orderMapper.selectByOrderNo(orderNo);
        return ServerResponse.createBySuccess(order);
    }

    @Override
    public ServerResponse updatePayMessage(Order order) {
        if (StringUtils.isEmpty(order.getPayId()) || StringUtils.isEmpty(order.getOrderNo())) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        try {
            Order tem = orderMapper.selectByOrderNo(order.getOrderNo());
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] dateString = dateFormat.format(date).split("-");
            int year = Integer.parseInt(dateString[0]);
            int month = Integer.parseInt(dateString[1]);
            int day = Integer.parseInt(dateString[2]);
            Account account = new Account();
            account.setDay(day);
            account.setMonth(month);
            account.setYear(year);
            account.setMerchantId(tem.getMerchantId());

            List<Account> allList = accountMapper.getOneByMerchantId(tem.getMerchantId());
            int row = 0;
            int updateRow = 0;
            if (allList.size() != 0){
                Account all = allList.get(0);
                //不同年不同月不同日   添加
                if (all.getYear() != year){/* && all.getMonth() != month&& all.getDay()!= day*/
                    account.setMonthlyIncome(order.getPayment());
                    account.setAnnualIncome(order.getPayment());
                    account.setDailyReceipts(order.getPayment());
                    account.setTotal(BigDecimalUtil.add(order.getPayment().doubleValue(),all.getTotal().doubleValue()));
                    // row = accountMapper.addAccount(account);//直接添加
                }
                //同年同月同日  即今天 ,说明这不是今天第一次交易   更新
                else if (all.getYear() == year && all.getMonth() == month&& all.getDay()== day){//当然多次添加
                    //仅更新
                    account.setAnnualIncome(BigDecimalUtil.add(order.getPayment().doubleValue(),all.getAnnualIncome().doubleValue()));
                    account.setDailyReceipts(BigDecimalUtil.add(order.getPayment().doubleValue(),all.getDailyReceipts().doubleValue()));
                    account.setMonthlyIncome(BigDecimalUtil.add(all.getMonthlyIncome().doubleValue(),order.getPayment().doubleValue()));
                    account.setTotal(BigDecimalUtil.add(all.getTotal().doubleValue(),order.getPayment().doubleValue()));
                     updateRow = accountMapper.updateAccount(account);//直接添加
                }
                //同年同月不同日  添加
                else if (all.getYear() == year && all.getMonth() == month && all.getDay()!= day){

                    account.setAnnualIncome(BigDecimalUtil.add(order.getPayment().doubleValue(),all.getAnnualIncome().doubleValue()));
                    account.setMonthlyIncome(BigDecimalUtil.add(all.getMonthlyIncome().doubleValue(),order.getPayment().doubleValue()));
                    account.setDailyReceipts(order.getPayment());
                    account.setTotal(BigDecimalUtil.add(all.getTotal().doubleValue(),order.getPayment().doubleValue()));
                     //row = accountMapper.addAccount(account);//直接添加
                }
                //年相同，月不同  添加
                else if (all.getYear() == year && all.getMonth() != month ){

                    account.setAnnualIncome(BigDecimalUtil.add(order.getPayment().doubleValue(),all.getAnnualIncome().doubleValue()));
                    account.setMonthlyIncome(order.getPayment());
                    account.setDailyReceipts(order.getPayment());
                    account.setTotal(BigDecimalUtil.add(all.getTotal().doubleValue(),order.getPayment().doubleValue()));
                     //row = accountMapper.addAccount(account);//直接添加
                }
            }else {
                //第一单执行
                account.setDailyReceipts(order.getPayment());
                account.setAnnualIncome(order.getPayment());
                account.setMonthlyIncome(order.getPayment());
                account.setMonthlyIncome(order.getPayment());
                account.setTotal(order.getPayment());
                 //row = accountMapper.addAccount(account);//直接添加
            }
            if (updateRow == 0){
                row = accountMapper.addAccount(account);
            }
            int rowCount = orderMapper.updatePayMessage(order);
            if (rowCount > 0) {
                MyAccount myAccount = myAccountMapper.selectMyAccountByMerchantId(tem.getMerchantId());
                myAccount.setBalance(BigDecimalUtil.add(myAccount.getBalance().doubleValue(),order.getPayment().doubleValue()));
                myAccountMapper.updateAccount(myAccount);

                return ServerResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        return null;
    }

    @Override
    public ServerResponse pay(String orderNo, Integer userId, String path) {

        return null;
    }

    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        return null;
    }

    @Override
    public ServerResponse queryOrderPayStatus(Integer userId, String orderNo) {
        return null;
    }


    @Override
    public ServerResponse<String> cancel(Integer userId, String orderNo) {
        return null;
    }

    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        return null;
    }

    @Override
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, String orderNo) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo<OrderProductVo>> getOrderList(Integer userId, int pageNum, int pageSize) {

        if (userId < 0 || pageNum < 0 || pageSize < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectByUserId(userId);
        List<OrderProductVo> orderProductVoList = Lists.newArrayList();

        for (Order order : orders) {
            OrderProductVo productVo = new OrderProductVo();
            AddressVo addressVo = this.getAddress(order);
            productVo.setAddress(addressVo);
            productVo.setProductTotalPrice(order.getPayment());
            productVo.setOrderNo(order.getOrderNo());
            productVo.setDate(order.getCreateTime());
            productVo.setPayId(order.getPayId());
            productVo.setUpdateTime(order.getUpdateTime());
            productVo.setReceiving(addressVo.getReceiver() + " " + addressVo.getTel() + " " + addressVo.getDetail() + " " + addressVo.getPostalcode());
            if (order.getStatus() == Const.Order.NOT_PAY) {
                productVo.setOrderStatus("未支付");
            } else if (order.getStatus() == Const.Order.PAY) {
                productVo.setOrderStatus("待发货");
            } else if (order.getStatus() == Const.Order.CANCER_PAY) {
                productVo.setOrderStatus("已取消");
            } else if (order.getStatus() == Const.Order.SHIPPED) {
                productVo.setOrderStatus("已发货");
            } else if (order.getStatus() == Const.Order.GET_PRODUCT) {
                productVo.setOrderStatus("已收货");
            } else if (order.getStatus() == Const.Order.NO_REVIEW) {
                productVo.setOrderStatus("待评价");
            } else if (order.getStatus() == Const.Order.REVIEWED) {
                productVo.setOrderStatus("已评价");
            } else if (order.getStatus() == Const.Order.REFUND) {
                productVo.setOrderStatus("待退款");
            } else if (order.getStatus() == Const.Order.REFUNDED) {
                productVo.setOrderStatus("已退款");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCT) {
                productVo.setOrderStatus("待退货");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCTED) {
                productVo.setOrderStatus("已退货");
            }
            List<OrderItem> orderItem = orderItemMapper.selectByOrderNo(order.getOrderNo());
            List<OrderItemVo> orderItemVos = Lists.newArrayList();
            for (OrderItem item : orderItem) {
                OrderItemVo orderItemVo = this.getOrderItemVo(item);
                orderItemVos.add(orderItemVo);
            }
            productVo.setOrderItemVoList(orderItemVos);

            orderProductVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(orderProductVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo<OrderProductVo>> getOrderListByStatus(String merchantId, Integer status, int pageNum, int pageSize) {

        if (StringUtils.isEmpty(merchantId) || pageNum < 0 || pageSize < 0 || status < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectByUserIdByStatus(merchantId, status);
        List<OrderProductVo> orderProductVoList = Lists.newArrayList();

        for (Order order : orders) {
            OrderProductVo productVo = new OrderProductVo();
            AddressVo addressVo = this.getAddress(order);
            productVo.setAddress(addressVo);
            productVo.setProductTotalPrice(order.getPayment());
            productVo.setOrderNo(order.getOrderNo());

            productVo.setDate(order.getCreateTime());
            productVo.setPayId(order.getPayId());
            productVo.setUpdateTime(order.getUpdateTime());
            productVo.setReceiving(addressVo.getReceiver() + " " + addressVo.getTel() + " " + addressVo.getDetail() + " " + addressVo.getPostalcode());
            if (order.getStatus() == Const.Order.NOT_PAY) {
                productVo.setOrderStatus("未支付");
            } else if (order.getStatus() == Const.Order.PAY) {
                productVo.setOrderStatus("待发货");
            } else if (order.getStatus() == Const.Order.CANCER_PAY) {
                productVo.setOrderStatus("已取消");
            } else if (order.getStatus() == Const.Order.SHIPPED) {
                productVo.setOrderStatus("已发货");
            } else if (order.getStatus() == Const.Order.GET_PRODUCT) {
                productVo.setOrderStatus("已收货");
            } else if (order.getStatus() == Const.Order.NO_REVIEW) {
                productVo.setOrderStatus("待评价");
            } else if (order.getStatus() == Const.Order.REVIEWED) {
                productVo.setOrderStatus("已评价");
            } else if (order.getStatus() == Const.Order.REFUND) {
                productVo.setOrderStatus("待退款");
            } else if (order.getStatus() == Const.Order.REFUNDED) {
                productVo.setOrderStatus("已退款");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCT) {
                productVo.setOrderStatus("待退货");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCTED) {
                productVo.setOrderStatus("已退货");
            }

            List<OrderItem> orderItem = orderItemMapper.selectByOrderNo(order.getOrderNo());
            List<OrderItemVo> orderItemVos = Lists.newArrayList();
            for (OrderItem item : orderItem) {
                OrderItemVo orderItemVo = this.getOrderItemVo(item);
                orderItemVos.add(orderItemVo);
            }
            productVo.setOrderItemVoList(orderItemVos);

            orderProductVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(orderProductVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo<OrderProductVo>> getShopOrderList(String merchantId, int pageNum, int pageSize) {
        if (StringUtils.isEmpty(merchantId) || pageNum < 0 || pageSize < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectOrderByMerchantId(merchantId);
        List<OrderProductVo> orderProductVoList = Lists.newArrayList();

        for (Order order : orders) {
            OrderProductVo productVo = new OrderProductVo();
            AddressVo addressVo = this.getAddress(order);

            productVo.setUpdateTime(order.getUpdateTime());
            productVo.setAddress(addressVo);
            productVo.setProductTotalPrice(order.getPayment());
            productVo.setOrderNo(order.getOrderNo());
            productVo.setDate(order.getCreateTime());
            productVo.setPayId(order.getPayId());
            productVo.setUpdateTime(order.getUpdateTime());
            productVo.setReceiving(addressVo.getReceiver() + " " + addressVo.getTel() + " " + addressVo.getDetail() + " " + addressVo.getPostalcode());
            if (order.getStatus() == Const.Order.NOT_PAY) {
                productVo.setOrderStatus("未支付");
            } else if (order.getStatus() == Const.Order.PAY) {
                productVo.setOrderStatus("待发货");
            } else if (order.getStatus() == Const.Order.CANCER_PAY) {
                productVo.setOrderStatus("已取消");
            } else if (order.getStatus() == Const.Order.SHIPPED) {
                productVo.setOrderStatus("已发货");
            } else if (order.getStatus() == Const.Order.GET_PRODUCT) {
                productVo.setOrderStatus("已收货");
            } else if (order.getStatus() == Const.Order.NO_REVIEW) {
                productVo.setOrderStatus("待评价");
            } else if (order.getStatus() == Const.Order.REVIEWED) {
                productVo.setOrderStatus("已评价");
            } else if (order.getStatus() == Const.Order.REFUND) {
                productVo.setOrderStatus("待退款");
            } else if (order.getStatus() == Const.Order.REFUNDED) {
                productVo.setOrderStatus("已退款");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCT) {
                productVo.setOrderStatus("待退货");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCTED) {
                productVo.setOrderStatus("已退货");
            }
            List<OrderItem> orderItem = orderItemMapper.selectByOrderNo(order.getOrderNo());
            List<OrderItemVo> orderItemVos = Lists.newArrayList();
            for (OrderItem item : orderItem) {
                OrderItemVo orderItemVo = this.getOrderItemVo(item);
                orderItemVos.add(orderItemVo);
            }
            productVo.setOrderItemVoList(orderItemVos);

            orderProductVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(orderProductVoList);
        return ServerResponse.createBySuccess(pageInfo);


    }

    @Override
    public ServerResponse<PageInfo<OrderProductVo>> getShopOrderListAndOrderNo(String merchantId, Integer page, Integer limit, String orderNo) {
        if (StringUtils.isEmpty(merchantId) || page < 0 || limit < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Page pg = PageHelper.startPage(limit, limit);
        List<Order> orders = orderMapper.selectOrderByMerchantIdAndOrderNo(merchantId,orderNo);
        List<OrderProductVo> orderProductVoList = Lists.newArrayList();

        for (Order order : orders) {
            OrderProductVo productVo = new OrderProductVo();
            AddressVo addressVo = this.getAddress(order);

            productVo.setUpdateTime(order.getUpdateTime());
            productVo.setAddress(addressVo);
            productVo.setProductTotalPrice(order.getPayment());
            productVo.setOrderNo(order.getOrderNo());
            productVo.setDate(order.getCreateTime());
            productVo.setPayId(order.getPayId());
            productVo.setUpdateTime(order.getUpdateTime());
            productVo.setReceiving(addressVo.getReceiver() + " " + addressVo.getTel() + " " + addressVo.getDetail() + " " + addressVo.getPostalcode());
            if (order.getStatus() == Const.Order.NOT_PAY) {
                productVo.setOrderStatus("未支付");
            } else if (order.getStatus() == Const.Order.PAY) {
                productVo.setOrderStatus("待发货");
            } else if (order.getStatus() == Const.Order.CANCER_PAY) {
                productVo.setOrderStatus("已取消");
            } else if (order.getStatus() == Const.Order.SHIPPED) {
                productVo.setOrderStatus("已发货");
            } else if (order.getStatus() == Const.Order.GET_PRODUCT) {
                productVo.setOrderStatus("已收货");
            } else if (order.getStatus() == Const.Order.NO_REVIEW) {
                productVo.setOrderStatus("待评价");
            } else if (order.getStatus() == Const.Order.REVIEWED) {
                productVo.setOrderStatus("已评价");
            } else if (order.getStatus() == Const.Order.REFUND) {
                productVo.setOrderStatus("待退款");
            } else if (order.getStatus() == Const.Order.REFUNDED) {
                productVo.setOrderStatus("已退款");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCT) {
                productVo.setOrderStatus("待退货");
            } else if (order.getStatus() == Const.Order.RETURN_PRODUCTED) {
                productVo.setOrderStatus("已退货");
            }
            List<OrderItem> orderItem = orderItemMapper.selectByOrderNo(order.getOrderNo());
            List<OrderItemVo> orderItemVos = Lists.newArrayList();
            for (OrderItem item : orderItem) {
                OrderItemVo orderItemVo = this.getOrderItemVo(item);
                orderItemVos.add(orderItemVo);
            }
            productVo.setOrderItemVoList(orderItemVos);

            orderProductVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(orderProductVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 发货 取消订单 收货 等
     *
     * @param merchantId
     * @param orderNo
     * @param status
     * @return
     */
    @Override
    public ServerResponse updateOrderStatus(String merchantId, String orderNo, Integer status) {
        if (StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        try {
            Order order =  new Order();
            order.setStatus(status);
            order.setOrderNo(orderNo);
            order.setMerchantId(merchantId);


            int rowCount = orderMapper.updateOrderStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }
    @Override
    public ServerResponse cancelOrderStatus(String orderNo, Integer status) {
        if ( StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        try {
            Order order =  new Order();
            order.setStatus(status);
            order.setOrderNo(orderNo);
            Order tmp = orderMapper.selectByOrderNo(orderNo);
            if (tmp.getStatus() != Const.Order.NOT_PAY ){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"订单无法取消");
            }
            int rowCount = orderMapper.cancelOrderStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }
    @Override
    public ServerResponse enterGoodsOrderStatus(String orderNo, Integer status) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        try {
            Order order =  new Order();
            order.setStatus(status);
            order.setOrderNo(orderNo);

            Order tmp = orderMapper.selectByOrderNo(orderNo);
            if (tmp.getStatus() != Const.Order.SHIPPED){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"订单无法收货");
            }
            int rowCount = orderMapper.updateEnterGoodsOrderStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }
    @Override
    public ServerResponse delOrderByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            int del = orderMapper.delOrderByOrderNo(orderNo);
            int delItem = orderItemMapper.delOrderByOrderNo(orderNo);

            if (delItem > 0 && del > 0) {
                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }
        } catch (Exception e) {}
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }
    @Override
    public ServerResponse delForeOrderByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Order tmp = orderMapper.selectByOrderNo(orderNo);
        //仅删除已完成，已取消，已退款订单
        if (tmp.getStatus() != Const.Order.GET_PRODUCT||tmp.getStatus() != Const.Order.CANCER_PAY|| tmp.getStatus()!=Const.Order.RETURN_PRODUCTED){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"未完成订单无法删除");
        }
        try {
            int dels = orderMapper.delOrderByOrderNo(orderNo);
            int delItems = orderItemMapper.delOrderByOrderNo(orderNo);

            if (dels > 0 && delItems > 0  ) {
                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse refundByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        try {
            Order order =  new Order();
            order.setStatus(Const.Order.REFUND);
            order.setOrderNo(orderNo);

            Order tmp = orderMapper.selectByOrderNo(orderNo);
            if (tmp.getStatus() != Const.Order.PAY){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"订单无法退款");
            }
            int rowCount = orderMapper.updateEnterGoodsOrderStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"退款失败");
    }

    @Override
    public ServerResponse returnGoods(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            Order order =  new Order();
            order.setStatus(Const.Order.RETURN_PRODUCT);
            order.setOrderNo(orderNo);

            Order tmp = orderMapper.selectByOrderNo(orderNo);
            if (tmp.getStatus() != Const.Order.GET_PRODUCT){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"无法退货");
            }
            int rowCount = orderMapper.updateEnterGoodsOrderStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse refuseRefund(String orderNo) {

        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            Order order =  new Order();
            order.setStatus(Const.Order.PAY);
            order.setOrderNo(orderNo);

            Order tmp = orderMapper.selectByOrderNo(orderNo);
            if (tmp.getStatus() != Const.Order.REFUND){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"无法拒绝");
            }
            int rowCount = orderMapper.updateStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());

    }

    @Override
    public ServerResponse refuseReturn(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            Order order =  new Order();
            order.setStatus(Const.Order.GET_PRODUCT);
            order.setOrderNo(orderNo);

            Order tmp = orderMapper.selectByOrderNo(orderNo);
            if (tmp.getStatus() != Const.Order.RETURN_PRODUCT){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"无法拒绝");
            }
            int rowCount = orderMapper.updateStatus(order);

            if (rowCount > 0) {

                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());

    }

    @Override
    public ServerResponse<PageInfo> manageList(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<OrderVo> manageDetail(String orderNo) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> manageSearch(String orderNo, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<String> manageSendGoods(String orderNo) {
        return null;
    }

    private AddressVo getAddress(Order order) {
        Address address = addressMapper.selectAddressById(order.getAddressId());
        AddressVo addressVo = new AddressVo();
        addressVo.setReceiver(address.getReceiver());
        addressVo.setTel(address.getTel());
        addressVo.setId(address.getId());
        addressVo.setPostalcode(address.getPostalcode());
        addressVo.setDetail(address.getArea() + " " + address.getDetail());
        return addressVo;
    }

    @Override
    public ServerResponse getOrderItemListByOrderNo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(orderNo);
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem item : orderItems) {
            OrderItemVo orderItemVo = this.getOrderItemVo(item);
            orderItemVoList.add(orderItemVo);
        }
        return ServerResponse.createBySuccess(orderItemVoList);
    }

    private OrderItemVo getOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setProductImage("/manage/file/file_download?path=/" + orderItem.getProductImage());
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setPublish(orderItem.getPublish());
        orderItemVo.setNumber(orderItem.getNumber());
        orderItemVo.setPublishDate(orderItem.getPublishDate());
        orderItemVo.setAuthorName(orderItem.getAuthorName());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        return orderItemVo;
    }
}
