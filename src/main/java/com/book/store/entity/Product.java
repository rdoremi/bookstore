package com.book.store.entity;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;
    private String merchantId;
    private String authorName;
    private String shopName;
    private Integer categoryId;
    private Integer categoryChildId;
    private String number;
    private String name;
    private BigDecimal price;
    private String detail;
    private String subtitle;
    private Integer stock;
    private String publish;
    private String publishTime;
    private String mainImage;
    private String images;
    private Integer status;
    private String comment;
    private Integer commentStart;
    private Integer saleNumber;
    private String createTime;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryChildId() {
        return categoryChildId;
    }

    public void setCategoryChildId(Integer categoryChildId) {
        this.categoryChildId = categoryChildId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCommentStart() {
        return commentStart;
    }

    public void setCommentStart(Integer commentStart) {
        this.commentStart = commentStart;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
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
        return "Product{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryChildId=" + categoryChildId +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", detail='" + detail + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", stock=" + stock +
                ", publish='" + publish + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", images='" + images + '\'' +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", commentStart=" + commentStart +
                ", saleNumber=" + saleNumber +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
