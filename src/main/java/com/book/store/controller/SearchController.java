package com.book.store.controller;

import com.book.store.common.ServerResponse;
import com.book.store.service.impl.ProductServiceImpl;
import com.book.store.vo.CategoryVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping("/search")
    public String searchResult(String search, HttpServletRequest request){
        ServerResponse<PageInfo> productList = productService.getSearchProductList(1, 12,search);
        //ServerResponse<List<CategoryVo>> listServerResponse = categoryService.selectParentCategoryList();
        request.setAttribute("Product",productList.getData().getList());
        request.setAttribute("PageInfo",productList.getData());
        request.setAttribute("bookTotal",productList.getData().getSize());
        //request.setAttribute("Category",listServerResponse.getData());
        return "fore/search";
    }
}
