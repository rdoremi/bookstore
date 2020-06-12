package com.book.store.controller;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import com.book.store.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController()
@RequestMapping("/fore/order")
public class OrderForeController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/cancel_order")
    public ServerResponse cancelOrder(HttpSession session, String orderNo){

        User merchant = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return orderService.cancelOrderStatus(orderNo,Const.Order.CANCER_PAY);
    }
    @GetMapping("/del_order")
    public ServerResponse delOrder(HttpSession session,String orderNo){
        User merchant = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.delForeOrderByOrderNo(orderNo);
    }
    @GetMapping("/get_goods")
    public ServerResponse getGoods(HttpSession session,String orderNo){
        User merchant = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.enterGoodsOrderStatus(orderNo,Const.Order.GET_PRODUCT);
    }
    @GetMapping("/refund")
    public ServerResponse refund(HttpSession session,String orderNo){
        User merchant = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.refundByOrderNo(orderNo);
    }
    @GetMapping("/return_goods")
    public ServerResponse returnGoods(HttpSession session,String orderNo) {
        User merchant = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (merchant == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.returnGoods(orderNo);
    }

}
