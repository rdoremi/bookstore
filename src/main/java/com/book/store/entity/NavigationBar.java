package com.book.store.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (NavigationBar)实体类
 *
 * @author makejava
 * @since 2020-03-08 09:31:27
 */
public class NavigationBar implements Serializable {
    private static final long serialVersionUID = 550827327057871546L;
    
    private Integer id;
    
    private String title;
    
    private String url;
    
    private String createTime;
    
    private String updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "NavigationBar{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}