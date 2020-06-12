package com.book.store.controller.manage;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.User;
import com.book.store.service.UserService;
import com.book.store.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user")
public class UserController {
   /* @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ServerResponse<User> login(String tel, String password, HttpSession session){
        ServerResponse<User> response = userService.login(tel,password);
        if (response.isSuccess()){
            User user = response.getData();
            if (userService.checkAdminRole(user).isSuccess()){
                //登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
            }else {
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

 @RequestMapping("/add_user")
    public ServerResponse addUser01(HttpSession session,User user){
            User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
            if (currentUser == null){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
            }

        return userService.addUser(user);
    }

    @GetMapping("/delete_user")//管理员及超级管理员，删除普通用户
    public ServerResponse deleteUser(HttpSession session,int id){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if (user.getId() == id){
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return userService.deleteById(id,user);
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @GetMapping("/get_manage_list")
    public ServerResponse getManageList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.selectAllManage(page,limit,null);
    }
    @GetMapping("/get_user_list")
    public ServerResponse getUserList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.selectAllUser(page,limit);
    }

    @PostMapping("/add_admin")
    public ServerResponse addAdmin(HttpSession session,User user){

        User userbean = (User) session.getAttribute(Const.CURRENT_USER);
        if (userbean == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (userService.checkSuperAdminRole(userbean).isSuccess()){
            user.setRole(Const.Role.ROLE_ADMIN);
            return userService.addUser(user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"添加失败");
    }
    @PostMapping("/add_user")
    public ServerResponse addUser(HttpSession session,User user){
        User userbean = (User) session.getAttribute(Const.CURRENT_USER);
        if (userbean == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (userService.checkAdminRole(userbean).isSuccess()){
            user.setRole(Const.Role.ROLE_CUSTOMER);
            return userService.addUser(user);
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @PostMapping("/update_user")
    public ServerResponse updateUser(HttpSession session,User user){
        User userbean = (User) session.getAttribute(Const.CURRENT_USER);
        if (userbean == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if (userService.checkAdminRole(userbean).isSuccess()){
            if (StringUtils.isBlank(user.getPassword())){
                user.setPassword(userService.selectByTel(user.getTel()).getPassword());
            }
            if (userbean.getRole() == Const.Role.ROLE_ADMIN){
                return userService.update(user);
            }else if (userbean.getRole() == Const.Role.ROLE_SUPER_ADMIN){
                return userService.updateByAdmin(user);
            }
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }*/
}
