package com.book.store.mapper;

import com.book.store.entity.Shop;

import java.util.List;

public interface ShopMapper {
    int insert(Shop shop);
    int updateByMerchantId(Shop shop);
    int addShop(Shop shop);
    int deleteShopById(Integer id);
    int deleteShopByMerchantId(String merchantId);
    Shop selectShopById(Integer id);
    Shop selectShopByMerchantId(String merchantId);
    List<Shop> selectAllList();

    int checkByTel(String tel);

    List<Shop> selectAllListByName(String name);
}
