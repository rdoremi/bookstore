package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Shop;
import com.book.store.vo.ShopVo;
import com.github.pagehelper.PageInfo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface ShopService {
    ServerResponse addShop(ShopVo shop) throws UnsupportedEncodingException, NoSuchAlgorithmException;
    ServerResponse deleteShopById(Integer id);
    ServerResponse deleteShopByMerchantId(String MerchantId);
    ServerResponse updateShop(Shop shop);
    ServerResponse<Shop> selectShop(String merchantId);
    ServerResponse<PageInfo> selectShopList(int pageNum,int pageSize);

    ServerResponse<PageInfo> selectShopListAndName(int page, int limit, String name);
}
