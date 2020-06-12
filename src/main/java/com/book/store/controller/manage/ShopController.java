package com.book.store.controller.manage;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.Shop;
import com.book.store.entity.User;
import com.book.store.service.ShopService;
import com.book.store.service.impl.ShopServiceImpl;
import com.book.store.vo.ShopVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/manage/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PostMapping("/add_shop")
    public ServerResponse addShop(ShopVo shop) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return shopService.addShop(shop);
    }

    @GetMapping("/get_shop_list")
    public ServerResponse<PageInfo> getShopList( @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit,String name){
        if (StringUtils.isEmpty(name)){
            return shopService.selectShopList(page,limit);
        }
        return shopService.selectShopListAndName(page,limit,name);
    }
    @GetMapping("/delete_shop")
    public ServerResponse deleteShop(int id){
        return shopService.deleteShopById(id);
    }
}
