package com.book.store.controller.manage;

import com.book.store.common.Const;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/page")
public class ManageIndexController {

    @RequestMapping("/login")
    public String adminLogin(){
        return "admin/login";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return "redirect:/admin/page/login";
    }
    @RequestMapping("/goshop")
    public String goShop(HttpSession session){
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);
        return "redirect:/shop_book?merchantId="+merchant.getMerchantId();
    }
    @RequestMapping("/index")
    public String adminIndex(){
        return "admin/index";
    }
    @RequestMapping("/manage_list")
    public String manageList(){
        return "admin/pages/manage-list";
    }
    @RequestMapping("/errors")
    public String error(){
        return "admin/pages/error";
    }
    @RequestMapping("/user_list")
    public String userList(){
        return "admin/pages/user-list";
    }
    @RequestMapping("/welcome")
    public String welcome(){
        return "admin/pages/welcome";
    }
    @RequestMapping("/category_list")
    public String category(){
        return "admin/pages/category";
    }
    @RequestMapping("/new_category")
    public String newCategory(){
        return "admin/pages/new-category";
    }
    @RequestMapping("/product_add")
    public String productAdd(){
        return "admin/pages/product-add";
    }
    @RequestMapping("/product_list")
    public String productLists(){
        return "admin/pages/product-list";
    }
    @RequestMapping("/shop_add")
    public String shopAdd(){
        return "admin/pages/shop-add";
    }
    @RequestMapping("/shop_list_admin")
    public String shopListAdmin(){
        return "admin/pages/shop-list";
    }
    @RequestMapping("blog_add")
    public String blogAdd(){
        return "admin/pages/blog-add";
    }
    @RequestMapping("/blog_list")
    public String blgoList(){
        return "admin/pages/blog-list";
    }
    @RequestMapping("/my_blog_list")
    public String myBlgoList(){
        return "admin/pages/my-blog-list";
    }

    @RequestMapping("/admin_order_list")
    public String adminOrderList(){
        return "admin/pages/order-list";
    }
    @RequestMapping("/admin_order_edit")
    public String adminOrderEdit(){
        return "admin/pages/order-edit";
    }
    @RequestMapping("/admin_order_refund")
    public String adminOrderRefund(){
        return "admin/pages/order-refund-list";
    }
    @RequestMapping("/admin_order_return")
    public String adminOrderReturn(){
        return "admin/pages/order-return-list";
    }
    @RequestMapping("/admin_order_finish")
    public String adminOrderFinish() {
        return "admin/pages/order-finish-list";
    }
    @RequestMapping("/contact")
    public String adminCantactList(){
        return "admin/pages/contact";
    }
    @RequestMapping("/cartogram")
    public String catogram(){
        return "admin/pages/cartogram";
    }
    @RequestMapping("/nav")
    public String nav(){
        return "admin/pages/nav";
    }
    @RequestMapping("/my_account")
    public String myAccount(){
        return "admin/pages/my-account";
    }
    @RequestMapping("/balance")
    public String balance(){
        return "admin/pages/balance";
    }
    @RequestMapping("/transfer_accounts")
    public String ransferAccounts(){
        return "admin/pages/transfer-accounts";
    }
}
