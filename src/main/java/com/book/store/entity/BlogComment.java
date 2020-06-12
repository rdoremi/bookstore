package com.book.store.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (BlogComment)实体类
 *
 * @author makejava
 * @since 2020-03-04 12:08:56
 */
public class BlogComment implements Serializable {
    private static final long serialVersionUID = -48232439427541233L;
    
    private Integer id;
    
    private Integer blogId;
    
    private String tel;
    
    private String username;
    
    private String detail;
    
    private String image;
    
    private String createTime;
    
    private String createUpdate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUpdate() {
        return createUpdate;
    }

    public void setCreateUpdate(String createUpdate) {
        this.createUpdate = createUpdate;
    }

}