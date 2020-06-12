package com.book.store.controller;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Blog;
import com.book.store.entity.Order;
import com.book.store.entity.Shop;
import com.book.store.entity.User;
import com.book.store.service.impl.*;
import com.book.store.vo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private ShopServiceImpl shopService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private CollectsServiceImpl collectsService;

    @RequestMapping("/foreLogin")
    public String loginFore(){
        return "fore/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(Const.FORE_CURRENT_USER);
        session.removeAttribute("Cart");
        session.removeAttribute("totalPrice");
        session.removeAttribute("total");
        return "redirect:/foreLogin";
    }
    @RequestMapping("/register")
    public String register(){
        return "fore/register";
    }

    @RequestMapping("/forget")
    public String forget(){
        return "fore/forget";
    }


    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        request.setAttribute("HotProduct",productService.getProductList(1,6).getData().getList());
        request.setAttribute("NewProduct01",productService.getProductList(1,2).getData().getList());
        request.setAttribute("NewProduct02",productService.getProductList(2,2).getData().getList());
        request.setAttribute("NewProduct03",productService.getProductList(3,2).getData().getList());
        request.setAttribute("NewProduct04",productService.getProductList(4,2).getData().getList());
        request.setAttribute("NewProduct05",productService.getProductList(5,2).getData().getList());
        request.setAttribute("Blog",blogService.selectAll(1,3).getData().getList());
        return "fore/index";
    }

    @RequestMapping("/shop_list")
    public String about(HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1") int page){
        ServerResponse<PageInfo> shopList = shopService.selectShopList(page, 8);
        request.setAttribute("shopList",shopList.getData().getList());
        request.setAttribute("PageInfo",shopList.getData());
        request.setAttribute("shopTotal",shopList.getData().getTotal());
        return "fore/shop-list";
    }

    @RequestMapping("/book_list")
    public String bookList(@RequestParam(value = "page",defaultValue = "1") Integer page,HttpServletRequest request){
        ServerResponse<PageInfo> productList = productService.getProductList(page, 12);
        ServerResponse<List<CategoryVo>> listServerResponse = categoryService.selectParentCategoryList();
        request.setAttribute("Product",productList.getData().getList());
        request.setAttribute("PageInfo",productList.getData());
        request.setAttribute("Category",listServerResponse.getData());
        request.setAttribute("bootTotal",productList.getData().getTotal());
        return "fore/book-list";
    }
    //资料
    @RequestMapping("/material")
    public String material(@RequestParam(value = "page",defaultValue = "1") Integer page,HttpServletRequest request){
        ServerResponse<PageInfo> productList = productService.getMaterialList(page, 12);
        ServerResponse<List<CategoryVo>> listServerResponse = categoryService.selectParentCategoryList();
        request.setAttribute("Product",productList.getData().getList());
        request.setAttribute("PageInfo",productList.getData());
        request.setAttribute("Category",listServerResponse.getData());
        request.setAttribute("bootTotal",productList.getData().getTotal());
        return "fore/material";
    }
    //期刊
    @RequestMapping("/periodical")
    public String periodical(@RequestParam(value = "page",defaultValue = "1") Integer page,HttpServletRequest request){
        ServerResponse<PageInfo> productList = productService.getPeriodical(page, 12);
        ServerResponse<List<CategoryVo>> listServerResponse = categoryService.selectParentCategoryList();
        request.setAttribute("Product",productList.getData().getList());
        request.setAttribute("PageInfo",productList.getData());
        request.setAttribute("Category",listServerResponse.getData());
        request.setAttribute("bootTotal",productList.getData().getTotal());
        return "fore/periodical";
    }
    @RequestMapping("/blog")
    public String blog(HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1") int page){
        ServerResponse<PageInfo> bloginfo = blogService.selectAll(page, 5);
        request.setAttribute("Blog",bloginfo.getData().getList());
        request.setAttribute("PageInfo",bloginfo.getData());
        return "fore/blog";
    }
    @RequestMapping("/blog_detail")
    public String blogDetail(HttpServletRequest request,int id){
        ServerResponse<Blog> blog = blogService.selectBlogById(id);
        //request.setAttribute("blog",blog.getData());
        request.setAttribute("blogId",id);
        return "fore/blog-details";
    }
    @RequestMapping("/children_books")
    public String childrenBooks(@RequestParam(value = "page",defaultValue = "1") Integer page,HttpServletRequest request){
        ServerResponse<PageInfo> productList = productService.getChildrenList(page, 12);
        ServerResponse<List<CategoryVo>> listServerResponse = categoryService.selectParentCategoryList();
        request.setAttribute("Product",productList.getData().getList());
        request.setAttribute("PageInfo",productList.getData());
        request.setAttribute("Category",listServerResponse.getData());
        request.setAttribute("childBookTotal",productList.getData().getTotal());
        return "fore/child-book";
    }
    @RequestMapping("/contact")
    public String contact(){
        return "fore/contact";
    }
    @RequestMapping("/portfolio")
    public String portfolio(){
        return "fore/portfolio";
    }
    @RequestMapping("/product_detail")
    public String productDetail(Integer id,HttpServletRequest request){
        ServerResponse<ProductDetailVo> product = productService.getProductById(id);
        request.setAttribute("Product",product.getData());
        request.setAttribute("Category",categoryService.selectParentCategoryList().getData());
        return "fore/product-detail";
    }
    @GetMapping("/cart_list")
    public String cartList(HttpSession session,HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1") Integer page){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "redirect:/foreLogin";
        }
        ServerResponse<PageInfo> allCartList = cartService.getAllCartList(user.getId(), page, 5);
        request.setAttribute("cartList",allCartList.getData().getList());
        request.setAttribute("total",allCartList.getData().getTotal());
        request.setAttribute("PageInfo",allCartList.getData());
        return "fore/cart";
    }
    @GetMapping("/submit_order")
    public String submitOrder(HttpSession session){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "redirect:/foreLogin";
        }
        return "fore/submit-order";
    }
    @GetMapping("/receiver")
    public String receiver(HttpSession session,HttpServletRequest request){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "redirect:/foreLogin";
        }
        ServerResponse<List<AddressVo>> addressByUserId = addressService.getAddressByUserId(user.getId());
        request.setAttribute("receiver",addressByUserId.getData());
        return "fore/receiver-address";
    }

    @GetMapping("/pay")
    public String pay(HttpSession session,Integer addressId,HttpServletRequest request){

        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
              return "redirect:/foreLogin";
        }
        request.setAttribute("nickName",user.getUsername());

        List<CartVo> list = (List<CartVo>) session.getAttribute(Const.SUB_ORDER);
        if (list == null){
            request.setAttribute("msg","该商品已完成订单或已取消");
            return "fore/error404";
        }
        BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");
        /*ServerResponse addOrder(List<CartVo> cartVos, BigDecimal total,Integer addressId,Integer userId)*/

        request.setAttribute("payPrice",totalPrice);
        ServerResponse<Order> orderServerResponse = orderService.addOrder(list, totalPrice, addressId, user.getId());
        request.setAttribute("orderId",orderServerResponse.getData().getOrderNo());
        request.setAttribute("totalAmount",orderServerResponse.getData().getPayment());

        return  "fore/pay";
    }
    @GetMapping("/my_order")
    public String myOrder(HttpSession session,@RequestParam(value = "page",defaultValue = "1") Integer page,HttpServletRequest request){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "redirect:/foreLogin";
        }
        ServerResponse<PageInfo<OrderProductVo>> orderList = orderService.getOrderList(user.getId(), page, 10);

        request.setAttribute("Product",orderList.getData().getList());
        request.setAttribute("PageInfo",orderList.getData());

        return "fore/my-order";
    }
    @GetMapping("/getTrade_no")
    public String getTradeNo(HttpServletRequest request,HttpSession session){
        session.removeAttribute(Const.SUB_ORDER);
        session.removeAttribute(Const.RECEIVER);
        session.removeAttribute("totalPrice");

        String trade_no = request.getParameter("trade_no");
        String out_trade_no = request.getParameter("out_trade_no");
        String total_amount = request.getParameter("total_amount");

        Order order = new Order();
        order.setPayId(trade_no);
        order.setPayment(new BigDecimal(total_amount));
        order.setOrderNo(out_trade_no);
        order.setStatus(Const.Order.PAY);
        orderService.updatePayMessage(order);

        return "redirect:/my_order";
    }
    @GetMapping("/shop_book")
    public String shopBook(HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1") int page,String merchantId){
        ServerResponse<PageInfo> product = productService.getProductByMerchantId(merchantId, page, 16);
        ServerResponse<Shop> shop = shopService.selectShop(merchantId);

        request.setAttribute("Product",product.getData().getList());
        request.setAttribute("PageInfo",product.getData());
        request.setAttribute("Merchant",shop.getData());
        return "fore/shop-book-list";
    }
    @GetMapping("/my_collect")
    public String myCollect(HttpSession session,HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1") int page){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "redirect:/foreLogin";
        }
        ServerResponse<PageInfo> collectList = collectsService.selectListByTel(user.getTel(),page,5);
        request.setAttribute("collectList",collectList.getData().getList());
        request.setAttribute("total",collectList.getData().getTotal());
        request.setAttribute("PageInfo",collectList.getData());
        return "fore/my-collect";
    }
    @GetMapping("/my_info")
    public String myInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return "redirect:/foreLogin";
        }
        return "fore/my-info";
    }
}
