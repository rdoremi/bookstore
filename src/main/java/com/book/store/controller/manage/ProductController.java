package com.book.store.controller.manage;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Merchant;
import com.book.store.entity.Product;
import com.book.store.entity.User;
import com.book.store.service.impl.ProductServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/save")
    public ServerResponse saveProduct(HttpSession session, Product product){


        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);

        product.setMerchantId(user.getMerchantId());
        return productService.saveOrUpdateProduct(product);
    }
    @GetMapping("/get_product_list")
    public ServerResponse getProductList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit,String name){
        Merchant user = (Merchant) session.getAttribute(Const.CURRENT_USER);
        if (StringUtils.isEmpty(name)){
            return productService.getProductListByMerchantId(page,limit,user.getMerchantId());
        }
        return productService.getProductListByMerchantIdAndSearch(page,limit,user.getMerchantId(),name);
    }
    @GetMapping("/delete_product")
    public ServerResponse deleteProduct(Integer id){

        return productService.deleteProductById(id);
    }
}
