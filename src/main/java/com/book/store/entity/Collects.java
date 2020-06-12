package com.book.store.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Collects)实体类
 *
 * @author makejava
 * @since 2020-03-04 17:55:05
 */
public class Collects implements Serializable {
    private static final long serialVersionUID = -70583391581217241L;
    
    private Integer id;
    
    private Integer productId;
    
    private String tel;
    
    private String createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}