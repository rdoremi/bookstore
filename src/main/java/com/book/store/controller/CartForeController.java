package com.book.store.controller;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Cart;
import com.book.store.entity.User;
import com.book.store.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartForeController {
    @Autowired
    private CartServiceImpl cartService;

    @PostMapping("/add_cart")
    public ServerResponse addCart(HttpSession session,Cart cart){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){

            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        cart.setUserId(user.getId());

        return cartService.add(cart);
    }
    @PostMapping("/get_cart_Total")
    public ServerResponse getAddTotal( String cartId){

        return cartService.getAllTotal(cartId);
    }
    @PostMapping("/update_quantity")
    public ServerResponse updateQuantity(HttpSession session, Integer cartId, Integer quantity){

        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createBySuccessCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.updateQuantity(user.getId(),cartId,quantity);
    }
    @GetMapping("/delete_cart")
    public ServerResponse deleteCart(HttpSession session,Integer id){

        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createBySuccessCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.deleteCart(id);
    }
}
