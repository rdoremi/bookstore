package com.book.store.controller.common;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Blog;
import com.book.store.entity.Merchant;
import com.book.store.entity.User;
import com.book.store.service.impl.BlogServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogServiceImpl blogService;

    @PostMapping("/add_blog")
    public ServerResponse addBlog(HttpSession session,Blog blog){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        blog.setAuthorName(merchant.getUsername());
        blog.setMerchantId(merchant.getMerchantId());
        return blogService.addBlog(blog);
    }

    @GetMapping("/get_shop_blog_list")
    public ServerResponse getShopBLogList(HttpSession session,@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit,String title){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        if (StringUtils.isEmpty(title)){
            return blogService.selectAllByMerchantId(page,limit,merchant.getMerchantId());
        }
        return blogService.selectAllByMerchantIdAndTitle(page,limit,merchant.getMerchantId(),title);
    }

    @GetMapping("/shop_delete_blog")
    public ServerResponse shopDeleteBlog(HttpSession session,int id){
        Merchant merchant = (Merchant) session.getAttribute(Const.SHOP_CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return blogService.deleteById(id);
    }


    /********************manage***************************/

    @PostMapping("admin_add_blog")
    public ServerResponse mAddBlog(HttpSession session,Blog blog){
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        blog.setAuthorName(merchant.getUsername());
        blog.setMerchantId(merchant.getMerchantId());
        return blogService.addBlog(blog);
    }
    @GetMapping("/get_blog_List")
    public ServerResponse getBlogList(HttpSession session,@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit,String title){
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (StringUtils.isEmpty(title)){
            return blogService.selectAll(page,limit);
        }
        return blogService.selectAllByTitle(page,limit,title);
    }
    @GetMapping("/get_admin_blog_list")
    public ServerResponse getBLogListAdmin(HttpSession session,@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit,String title){
        Merchant merchant = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (merchant == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (StringUtils.isEmpty(title)){
            return blogService.selectAllByMerchantId(page,limit,merchant.getMerchantId());
        }
        return blogService.selectAllByMerchantIdAndTitle(page,limit,merchant.getMerchantId(),title);
    }
    @GetMapping("/delete_blog")
    public ServerResponse deleteBlog(Integer id){
        return blogService.deleteById(id);
    }
}
