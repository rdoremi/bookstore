package com.book.store.mapper;


import com.book.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //    public Userbean login(String tel,String password);
    public List<User> selectAll();
    public List<User> selectAllManage();
    public List<User> selectManage();
    public List<User> selectAllUser();
    public User selectById(int id);
    public User selectByTel(String tel);
    public List<User> selectByTelList(String tel);
    public int insert(User user);
    public int register(User user);
    public int registerByFor(User user);
    public int update(User user);
    public int updateByAdmin(User user);
    public int updateRole(User user);
    public int delete(String tel);
    public int deleteById(int id);
    public int deleteAdminById(int id);
    public int checkUser(String tel);
    public int checkUserByFore(String tel);
    public User selectLogin(@Param("tel") String tel, @Param("password") String password);
    public User selectLoginByTel( String tel);
    public int checkEmail(String email);
    public int checkUsername(String username);
    public String selectQuestion(String tel);
    public int checkAnswer(@Param("tel") String tel,@Param("question")String question,@Param("answer")String answer);
    public int updatePasswordByTel(@Param("tel")String tel,@Param("password")String password);
    public int checkPassword(@Param("password") String password,@Param("tel") String tel);
    public int checkEmailByTel(@Param("tel") String tel,@Param("email") String email);
    public int updateInform(User user);

    int updateUsername(@Param("tel") String tel, @Param("username") String username);
}
