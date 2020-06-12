package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import com.book.store.vo.UserListVo;
import com.github.pagehelper.PageInfo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    public ServerResponse<User> login(String tel, String password);

    ServerResponse<User> loginByFore(String tel, String password);

    public ServerResponse<PageInfo> selectAllManage(int pageNum, int pageSize, User us);

    ServerResponse<PageInfo> getUserList(int pageNum, int pageSize);

    //    public ServerResponse<PageInfo> selectAllUser(int pageNum, int pageSize);
    public ServerResponse getUserByTel(String tel);

    public ServerResponse<User> getUserById(Integer id);


    public ServerResponse addUser(User user);

    public ServerResponse update(User user);

    public ServerResponse updateByAdmin(User user);

    public ServerResponse updateRole(User user);


    public ServerResponse deleteAdmin(int id);

    ServerResponse<PageInfo> selectAllUser(Integer pageNum, Integer pageSize);

    public ServerResponse deleteById(int id,Merchant user);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);//校验用户名和邮箱

    public ServerResponse<String> selectQuestion(String tel);

    public ServerResponse<String> checkAnswer(String tel, String question, String answer);

    public ServerResponse<String> forgetRestPassword(String tel, String passwordNew, String forgetToker);

    public ServerResponse<String> resetPassword(String passwordold, String passwordnew, User user);

    public ServerResponse<User> updateInformation(User user);

    public ServerResponse<User> getInformation(String tel);

    public ServerResponse checkAdminRole(User user);

    public ServerResponse checkSuperAdminRole(Merchant user);
    public ServerResponse checkShopRole(User user);

    User selectByTel(String tel);

    ServerResponse updateUsername(String tel, String username);

    ServerResponse updatePassword(String tel, String olderPassword, String newPassowrd, String newPwd);

    ServerResponse selectAllUserByTel(String tel);

    ServerResponse forget(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
