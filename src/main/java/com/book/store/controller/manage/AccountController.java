package com.book.store.controller.manage;

import com.book.store.common.Const;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.service.impl.AccountServiceImpl;
import com.book.store.service.impl.MyAccountServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private MyAccountServiceImpl myAccountService;

    @GetMapping("/get_my_trade")
    public ServerResponse getMyTrade(HttpSession session) {
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);

        return accountService.selectByMerchantId(merchant.getMerchantId());
    }
    @GetMapping("/get_trade")
    public ServerResponse getTrade(HttpSession session){
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);
        return accountService.selectByMerchantIdAnMonth(merchant.getMerchantId());
    }
    @GetMapping("/get_all_account")
    public ServerResponse getAllCount() {


        return accountService.selectAll();
    }
    @GetMapping("/get_my_account")
    public ServerResponse getMyAccount(HttpSession session){
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);

        return myAccountService.selectMyAccount(merchant.getMerchantId());
    }
    @GetMapping("/get_all_merchant_account")
    public ServerResponse<PageInfo> getAllMerchantAccount(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit,String tel){
        if (StringUtils.isEmpty(tel)){
            return myAccountService.selectAllMyAccount(page,limit);
        }
        return myAccountService.selectAllMyAccountAndTel(page,limit,tel);
    }
    @GetMapping("/get_transfer_account")
    public ServerResponse<PageInfo> getTransferAccount(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit){

        return myAccountService.selectAllTransferMyAccount(page,limit);
    }
}
