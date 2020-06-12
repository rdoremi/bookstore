package com.book.store.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.book.store.common.Const;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Order;
import com.book.store.entity.User;
import com.book.store.service.AlipayService;
import com.book.store.service.impl.AlipayServiceImpl;
import com.book.store.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AlipayController {

    @Autowired
    @Qualifier("alipayService")
    private AlipayServiceImpl alipayService;
    @Autowired
    private OrderServiceImpl orderService;
    /**
     * web 订单支付
     */
    @PostMapping("/getPagePay")
    public String getPagePay(HttpSession session,String oderNo) throws Exception{
        /** 模仿数据库，从后台调数据*/
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "未登录";
        }
        String subject = "图书商品";

        Order data = orderService.getOrderByOderNo(oderNo).getData();

        String pay = alipayService.webPagePay(data.getOrderNo().toString(), data.getPayment(),subject );

        return pay;
    }

    /**
     * app 订单支付
     */
    @GetMapping("getAppPagePay")
    public ServerResponse<?> getAppPagePay() throws Exception{
        /** 模仿数据库，从后台调数据*/
        String outTradeNo = "131233";
        BigDecimal totalAmount = new BigDecimal("1000");
        String subject = "天猫超市012";

        String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);

        Object json = JSONObject.toJSON(pay);

        System.out.println(json);

        return ServerResponse.createBySuccess(json);
    }

    /**
     * 交易查询
     */
    @PostMapping("/aipayQuery")
    public ServerResponse<?> alipayQuery() throws Exception{
        /**调取支付订单号*/
        String outTradeNo = "1313123";

        String query = alipayService.query(outTradeNo);

        Object json = JSONObject.toJSON(query);

        /*JSONObject jObject = new JSONObject();
        jObject.get(query);*/
        return ServerResponse.createBySuccess(json);
    }

    /**
     * 退款
     * @throws AlipayApiException
     */
    @GetMapping("alipayRefund")
    public ServerResponse<?> alipayRefund(
            @RequestParam("outTradeNo")String outTradeNo,
            @RequestParam(value = "outRequestNo", required = false)String outRequestNo,
            @RequestParam(value = "refundAmount", required = false)Integer refundAmount
    ) throws AlipayApiException{

        /** 调取数据*/
        //String outTradeNo = "15382028806591197";
        String refundReason = "用户不想购买";
        //refundAmount = 1;
        //outRequestNo = "22";

        String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);

        System.out.println(refund);

        return ServerResponse.createBySuccess(refund);
    }

    /**
     * 退款查询
     * @throws AlipayApiException
     */
    @PostMapping("refundQuery")
    public ServerResponse<?> refundQuery() throws AlipayApiException{

        /** 调取数据*/
        String outTradeNo = "13123";
        String outRequestNo = "2";

        String refund = alipayService.refundQuery(outTradeNo, outRequestNo);

        return ServerResponse.createBySuccess(refund);

    }

    /**
     * 交易关闭
     * @throws AlipayApiException
     */
    @PostMapping("alipayclose")
    public ServerResponse<?> alipaycolse() throws AlipayApiException {

        /** 调取数据*/
        String outTradeNo = "13123";

        String close = alipayService.close(outTradeNo);

        return ServerResponse.createBySuccess(close);
    }


}
