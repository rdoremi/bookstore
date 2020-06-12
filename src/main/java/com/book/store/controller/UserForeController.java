package com.book.store.controller;


import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Address;
import com.book.store.entity.Collects;
import com.book.store.entity.Contact;
import com.book.store.entity.User;
import com.book.store.service.impl.*;
import com.book.store.vo.AddressVo;
import com.book.store.vo.CartVo;
import com.book.store.vo.UserListVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/fore")
public class UserForeController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private ContactServiceImpl contactService;
    @Autowired
    private CollectsServiceImpl collectsService;
    @PostMapping("/to_login")
    public ServerResponse foreToLogin(String tel, String password, HttpSession session){
        ServerResponse<User> login = userService.loginByFore(tel, password);
        if (login.isSuccess()){
            ServerResponse<PageInfo> allCartList = cartService.getAllCartList(login.getData().getId(), 1, 3);
            session.setAttribute("Cart",allCartList.getData().getList());
            session.setAttribute(Const.TOTAL,allCartList.getData().getTotal());
            List<CartVo> list = allCartList.getData().getList();
            BigDecimal bigDecimal = new BigDecimal("0");
            for (CartVo cartVo : list) {
                bigDecimal = cartVo.getAllTotalPrice();
            }
            session.setAttribute("totalPrice",bigDecimal);
            session.setAttribute(Const.FORE_CURRENT_USER,login.getData());
        }
        return login;
    }
    @PostMapping("/register")
    public ServerResponse rester(User user){
        return userService.register(user);
    }
    @PostMapping("/forget")
    public ServerResponse forget(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.forget(user);
    }
    @PostMapping("/update_username")
    public ServerResponse updateUsername(HttpSession session,String username){

        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.updateUsername(user.getTel(),username);
    }
    @PostMapping("/update_password")
    public ServerResponse updatePassword(HttpSession session,String olderPassword,String newPassowrd,String newPwd){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.updatePassword(user.getTel(),olderPassword,newPassowrd,newPwd);
    }
    @RequestMapping("/get_info")
    public ServerResponse getInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.getInformation(user.getTel());
    }

    @PostMapping("/add_receiver")
    public ServerResponse addRecevier(HttpSession session, Address address){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        address.setUserId(user.getId());
        return addressService.add(address);
    }
    @GetMapping("/delete_receiver")
    public ServerResponse deleteReceiver(HttpSession session,Integer id){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return addressService.deleteAddressById(id);
    }

    @PostMapping("/to_sub_order")
    public ServerResponse subOrder(HttpSession session, String cartId){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);

        if (user == null){
           return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        ServerResponse<BigDecimal> allTotal = cartService.getAllTotal(cartId);

        ServerResponse<List<CartVo>> cartListById = cartService.getCartListById(cartId);
        ServerResponse<List<AddressVo>> addressByUserId = addressService.getAddressByUserId(user.getId());
        session.setAttribute(Const.RECEIVER,addressByUserId.getData());
        session.setAttribute(Const.SUB_ORDER,cartListById.getData());
        session.setAttribute("totalPrice",allTotal.getData());
        return ServerResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
    }
    @GetMapping("/cancer_order")
    public ServerResponse cancerOrder(HttpSession session){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        session.removeAttribute(Const.SUB_ORDER);
        session.removeAttribute(Const.RECEIVER);
        session.removeAttribute("totalPrice");
        return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
    }

    @PostMapping("/add_contact")
    public ServerResponse addContact(HttpSession session,Contact contact){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        if (StringUtils.isEmpty(contact.getTel())){
            contact.setTel(user.getTel());
        }
        return contactService.addContact(contact);
    }

    /*收藏*/
    @PostMapping("/add_collect")
    public ServerResponse addCollect(HttpSession session, Collects collects){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        collects.setTel(user.getTel());
        return collectsService.addCollect(collects);
    }
    @GetMapping("/delete_collect")
    public ServerResponse deleteCollect(HttpSession session,int id){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return collectsService.deleteById(id);
    }
}
