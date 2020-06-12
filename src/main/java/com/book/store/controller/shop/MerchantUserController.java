package com.book.store.controller.shop;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;

import com.book.store.service.impl.MerchantServiceImpl;
import com.book.store.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shop/user")
public class MerchantUserController {
    @Autowired
    private MerchantServiceImpl userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ServerResponse<Merchant> login(String tel, String password, HttpSession session){
        ServerResponse<Merchant> response = userService.login(tel,password);
        if (response.isSuccess()){
            Merchant user = response.getData();
            if (userService.checkAdminRole(user).isSuccess()){
                //登录的是管理员
                session.setAttribute(Const.SHOP_CURRENT_USER,user);
            }else {
                return ServerResponse.createByErrorMessage("不是商家,无法登录");
            }
        }
        return response;
    }

    /*@PostMapping("/add_employee")
    public ServerResponse addEmployee(HttpSession session,Merchant merchant){
        Merchant user = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.addUser(merchant);
    }*/
    @GetMapping("/delete_user")//管理员及超级管理员，删除普通用户
    public ServerResponse deleteUser(HttpSession session,int id){
        Merchant user = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        if (user.getId() == id){
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if (userService.checkShopRole(user).isSuccess()){
            return userService.deleteById(id,user);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }

    @GetMapping("/get_manage_list")
    public ServerResponse getManageList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        Merchant user = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);

        return userService.selectAllManage(page,limit,null,user.getMerchantId());
    }
    @GetMapping("/get_user_list")
    public ServerResponse getUserList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        Merchant user = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        return userService.selectAllUser(page,limit,user.getMerchantId());
    }

    @PostMapping("/add_admin")
    public ServerResponse addAdmin(HttpSession session,Merchant user){

        Merchant userbean = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);


        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (userService.checkSuperAdminRole(userbean).isSuccess()){
            user.setRole(Const.Role.ROLE_MERCHANT_EMPLOYEE);
            return userService.addUser(user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"添加失败");
    }
    @PostMapping("/add_employee")
    public ServerResponse addMerchant(HttpSession session,Merchant user){
        Merchant userbean = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);


        if (userService.checkShopRole(userbean).isSuccess()){
            user.setRole(Const.Role.ROLE_MERCHANT_EMPLOYEE);
            user.setMerchantId(userbean.getMerchantId());
            return userService.addUser(user);
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @PostMapping("/update_user")
    public ServerResponse updateUser(HttpSession session,Merchant user){
        Merchant userbean = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if (userService.checkAdminRole(userbean).isSuccess()){
            if (StringUtils.isBlank(user.getPassword())){//如果没填写将不会修改
                user.setPassword(userService.selectByTel(user.getTel()).getPassword());

            }
            if (userbean.getRole() == Const.Role.ROLE_ADMIN){
                return userService.update(user);
            }else if (userbean.getRole() == Const.Role.ROLE_MERCHANT){
                return userService.updateByAdmin(user);
            }
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }
}
