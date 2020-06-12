package com.book.store.controller.shop;

import com.book.store.common.Const;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.service.impl.AccountServiceImpl;
import com.book.store.service.impl.MyAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shop/account")
public class ShopAccountController {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private MyAccountServiceImpl myAccountService;
    @GetMapping("/get_my_trade")
    public ServerResponse getMyTrade(HttpSession session) {
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        System.out.println(merchant.getMerchantId());
        return accountService.selectByMerchantId(merchant.getMerchantId());
    }
    @GetMapping("/get_trade")
    public ServerResponse getTrade(HttpSession session) {
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);

        return accountService.selectByMerchantIdAnMonth(merchant.getMerchantId());
    }

    @GetMapping("/get_my_account")
    public ServerResponse getMyAccount(HttpSession session){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);

        return myAccountService.selectMyAccount(merchant.getMerchantId());
    }
}
