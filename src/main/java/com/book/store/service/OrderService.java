package com.book.store.service;


import com.book.store.common.ServerResponse;
import com.book.store.entity.Order;
import com.book.store.vo.CartVo;
import com.book.store.vo.OrderProductVo;
import com.book.store.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by geely
 */
public interface OrderService {
    ServerResponse pay(String orderNo, Integer userId, String path);
    ServerResponse aliCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer userId, String orderNo);
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse<String> cancel(Integer userId, String orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, String orderNo);
    ServerResponse<PageInfo<OrderProductVo>> getOrderList(Integer userId, int pageNum, int pageSize);

    ServerResponse updatePayMessage(Order order);
    ServerResponse<Order> addOrder(List<CartVo> cartVos, BigDecimal total, Integer addressId, Integer userId);
    ServerResponse<Order> getOrderByOderNo(String orderNo);
    //backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);
    ServerResponse<OrderVo> manageDetail(String orderNo);
    ServerResponse<PageInfo> manageSearch(String orderNo, int pageNum, int pageSize);
    ServerResponse<String> manageSendGoods(String orderNo);

    //fore
    ServerResponse cancelOrderStatus(String orderNo, Integer status);
    ServerResponse enterGoodsOrderStatus(String orderNo, Integer status);
    ServerResponse delForeOrderByOrderNo(String orderNo);
    ServerResponse refundByOrderNo(String orderNo);
    ServerResponse returnGoods(String orderNo);

    //以下商家处理
    ServerResponse<PageInfo<OrderProductVo>> getShopOrderList(String merchantId, int pageNum, int pageSize);
    ServerResponse<PageInfo<OrderProductVo>> getOrderListByStatus(String merchantId,Integer status, int pageNum, int pageSize);
    ServerResponse updateOrderStatus(String merchantId,String orderNo,Integer status);
    ServerResponse delOrderByOrderNo(String orderNo);
    ServerResponse refuseRefund(String orderNo);

    ServerResponse refuseReturn(String orderNo);

    ServerResponse getOrderItemListByOrderNo(String orderNo);

    ServerResponse<PageInfo<OrderProductVo>> getShopOrderListAndOrderNo(String merchantId, Integer page, Integer limit, String orderNo);
}
