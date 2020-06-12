package com.book.store.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Contact)实体类
 *
 * @author makejava
 * @since 2020-03-04 17:33:14
 */
public class Contact implements Serializable {
    private static final long serialVersionUID = -66566353148309223L;
    
    private Integer id;
    
    private String name;
    
    private String sex;
    
    private String tel;
    
    private String email;
    
    private String address;
    
    private String detail;
    
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}