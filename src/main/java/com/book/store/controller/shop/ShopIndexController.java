package com.book.store.controller.shop;

import com.book.store.common.Const;
import com.book.store.entity.Merchant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/shop/page")
public class ShopIndexController {


    @RequestMapping("/index")
    public String shopIndex(){
        return "shop/index";
    }
    @RequestMapping("/login")
    public String shopLogin(){
        return "shop/login";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(Const.SHOP_CURRENT_USER);
        return "redirect:/shop/page/login";
    }
    @RequestMapping("/goshop")
    public String goShop(HttpSession session){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        return "redirect:/shop_book?merchantId="+merchant.getMerchantId();
    }
    @RequestMapping("/shop_blog_add")
    public String shopBlogAdd(){
        return "shop/pages/blog-add";
    }
    @RequestMapping("/shop_blog_list")
    public String shopBlogList(){
        return "shop/pages/blog-list";
    }
    @RequestMapping("/shop_manage_list")
    public String shopManageList(){
        return "shop/pages/manage-list";
    }
    @RequestMapping("/shop_product_add")
    public String shopProductAdd(){
        return "shop/pages/product-add";
    }
    @RequestMapping("/shop_product_list")
    public String shopProductList(){
        return "shop/pages/product-list";
    }
    @RequestMapping("/shop_user_list")
    public String shopUserList(){
        return "shop/pages/user-list";
    }
    @RequestMapping("/welcome")
    public String shopWelcome(){
        return "shop/pages/welcome";
    }
    @RequestMapping("/shop_order_list")
    public String shopOrderList(){
        return "shop/pages/order-list";
    }
    @RequestMapping("/shop_order_edit")
    public String orderEdit(){
        return "shop/pages/order-edit";
    }
    @RequestMapping("/shop_order_refund")
    public String orderRefund(){
        return "shop/pages/order-refund-list";
    }
    @RequestMapping("/shop_order_return")
    public String orderReturn(){
        return "shop/pages/order-return-list";
    }
    @RequestMapping("/shop_order_finish")
    public String shopOrderFinish(){
        return "shop/pages/order-finish-list";
    }
    @RequestMapping("/cartogram")
    public String shopCartogram(){
        return "shop/pages/cartogram";
    }
    @RequestMapping("/my_account")
    public String myAccount(){
        return "shop/pages/my-account";
    }
}
