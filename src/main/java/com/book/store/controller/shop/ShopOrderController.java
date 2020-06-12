package com.book.store.controller.shop;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import com.book.store.service.impl.OrderServiceImpl;
import com.book.store.vo.OrderProductVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shop/order")
public class ShopOrderController {
    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 待处理订单
     * @param session
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/get_shop_order_list")
    public ServerResponse<PageInfo<OrderProductVo>> shopOrderList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit){
       Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        /*if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }*/
        return orderService.getOrderListByStatus(merchant.getMerchantId(),Const.Order.PAY,page,limit);
    }

    /**
     * 商家，全部订单
     * @param session
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/get_shop_order_all_list")
    public ServerResponse<PageInfo<OrderProductVo>> shopOrderAllList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,String orderNo){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);

        if (StringUtils.isEmpty(orderNo)){
            return orderService.getShopOrderList(merchant.getMerchantId(),page,limit);
        }
        return orderService.getShopOrderListAndOrderNo(merchant.getMerchantId(),page,limit,orderNo);
    }
    @GetMapping("/get_order_item")
    public ServerResponse getOrderItem(String orderNo){

        return orderService.getOrderItemListByOrderNo(orderNo);
    }
    /**
     * 待退款
     * @param session
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/get_shop_order_refun_list")
    public ServerResponse<PageInfo<OrderProductVo>> shopOrderRefundList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        return orderService.getOrderListByStatus(merchant.getMerchantId(),Const.Order.REFUND,page,limit);
    }

    /**
     * 待退货
     * @param session
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/get_shop_order_return_list")
    public ServerResponse<PageInfo<OrderProductVo>> shopOrderReturnList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        return orderService.getOrderListByStatus(merchant.getMerchantId(),Const.Order.RETURN_PRODUCT,page,limit);
    }
    @GetMapping("/get_shop_order_cancel_list")
    public ServerResponse<PageInfo<OrderProductVo>> shopOrderCancelList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        return orderService.getOrderListByStatus(merchant.getMerchantId(),Const.Order.CANCER_PAY,page,limit);
    }
    @GetMapping("/send_product")
    public ServerResponse sendProduct(HttpSession session,String orderNo){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        return orderService.updateOrderStatus(merchant.getMerchantId(),orderNo,Const.Order.SHIPPED);
    }

    @GetMapping("/cancel_order")
    public ServerResponse cancelOrder(String orderNo){
        return orderService.cancelOrderStatus(orderNo,Const.Order.CANCER_PAY);
    }
    @GetMapping("/del_order")
    public ServerResponse delOrder(String orderNo){
        return orderService.delOrderByOrderNo(orderNo);
    }
    @GetMapping("/refuse")
    public ServerResponse refuse(String orderNo) {
        return orderService.refuseRefund(orderNo);
    }
    @GetMapping("/refuse_return")
    public ServerResponse refuseReturn(String orderNo){
        return orderService.refuseReturn(orderNo);
    }
    @GetMapping("/return")
    public ServerResponse returns(HttpSession session,String orderNo){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);

        return orderService.updateOrderStatus(merchant.getMerchantId(),orderNo,Const.Order.RETURN_PRODUCTED);
    }
}
