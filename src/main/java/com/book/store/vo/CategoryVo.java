package com.book.store.vo;

import java.util.List;

public class CategoryVo {
    private Integer id;
    private Integer parentId;
    private String title;
    private String sunCategory;

    public CategoryVo(){}

    public CategoryVo(Integer id,Integer parentId,String title){
        this.id = id;
        this.parentId = parentId;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSunCategory() {
        return sunCategory;
    }

    public void setSunCategory(String sunCategory) {
        this.sunCategory = sunCategory;
    }
}
