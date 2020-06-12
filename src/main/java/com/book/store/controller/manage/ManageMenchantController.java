package com.book.store.controller.manage;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import com.book.store.service.impl.MerchantServiceImpl;
import com.book.store.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin/user")
public class ManageMenchantController {
    @Autowired
    private MerchantServiceImpl merchantService;
    @Autowired
    private UserServiceImpl uService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ServerResponse<Merchant> login(String tel, String password, HttpSession session){
        ServerResponse<Merchant> response = merchantService.login(tel,password);
        if (response.isSuccess()){
            Merchant user = response.getData();
            if (merchantService.checkRole(user).isSuccess()){
                //登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
            }else {
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

    @GetMapping("/delete_user")//管理员及超级管理员，删除普通用户
    public ServerResponse deleteUser(HttpSession session,int id){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (user.getId() == id){
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if (merchantService.checkRole(user).isSuccess()){
            return uService.deleteById(id,user);
        }
        return ServerResponse.createByErrorMessage("无权限操作");
    }
    @GetMapping("/delete_admin")//管理员及超级管理员，删除管理员
    public ServerResponse deleteAdmin(HttpSession session,int id){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (user.getId() == id){
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if (merchantService.checkSuperRole(user).isSuccess()){
            return merchantService.deleteById(id,user);
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }
    @GetMapping("/get_manage_list")
    public ServerResponse getManageList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        return merchantService.selectAdminManage(page,limit,user,user.getMerchantId());
    }
    @GetMapping("/get_user_list")
    public ServerResponse getUserList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit",defaultValue = "10") Integer limit,String tel){
        if (StringUtils.isEmpty(tel)){
            return uService.selectAllUser(page,limit);
        }
        return uService.selectAllUserByTel(tel);
    }

    @PostMapping("/add_admin")
    public ServerResponse addAdmin(HttpSession session,Merchant user){

        Merchant userbean = (Merchant) session.getAttribute(Const.CURRENT_USER);

        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (merchantService.checkSuperRole(userbean).isSuccess()){
            user.setRole(Const.Role.ROLE_ADMIN);
            user.setMerchantId(userbean.getMerchantId());
            return merchantService.addAdminUser(user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"添加失败");
    }
    @PostMapping("/add_user")
    public ServerResponse addUser(HttpSession session, User user){
        Merchant userbean = (Merchant) session.getAttribute(Const.CURRENT_USER);

        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (merchantService.checkRole(userbean).isSuccess()){
            user.setRole(Const.Role.ROLE_CUSTOMER);
            return uService.addUser(user);
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @PostMapping("/update_user")
    public ServerResponse updateUser(HttpSession session,User user){
        Merchant userbean = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if (merchantService.checkRole(userbean).isSuccess()){
            if (StringUtils.isBlank(user.getPassword())){
                user.setPassword(uService.selectByTel(user.getTel()).getPassword());
            }
            return uService.update(user);
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }
    @PostMapping("/update_admin")
    public ServerResponse updateAdmin(HttpSession session,Merchant user){
        Merchant userbean = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if (merchantService.checkSuperRole(userbean).isSuccess()){
            if (StringUtils.isBlank(user.getPassword())){
                user.setPassword(merchantService.selectByTel(user.getTel()).getPassword());
            }
            return merchantService.updateByAdmin(user);
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }
}
