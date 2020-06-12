package com.book.store.service.impl;

import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Blog;
import com.book.store.entity.Shop;
import com.book.store.mapper.BlogMapper;
import com.book.store.mapper.ShopMapper;
import com.book.store.service.BlogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public ServerResponse addBlog(Blog blog) {

        if (blog == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            Shop shop = shopMapper.selectShopByMerchantId(blog.getMerchantId());
            blog.setShopName(shop.getShopName());
            int rowCount = blogMapper.insert(blog);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse updateBlog(Blog blog) {
        if (blog == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        try {
            int rowCount = blogMapper.update(blog);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse<Blog> selectBlogById(int id) {
        if (id < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Blog blog = blogMapper.selectById(id);
        return ServerResponse.createBySuccess(blog);
    }

    @Override
    public ServerResponse<PageInfo> selectBlogByMerchantId(String merchantId,int page,int pageSize) {
        Page pg = PageHelper.startPage(page,pageSize);
        List<Blog> blogList = blogMapper.selectByMerchantId(merchantId);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(blogList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectAll(int page, int pageSize) {
        Page pg = PageHelper.startPage(page,pageSize);
        List<Blog> blogList = blogMapper.selectAll();
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(blogList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse selectAllByTitle(int page, int limit, String title) {
        Page pg = PageHelper.startPage(page,limit);
        List<Blog> blogList = blogMapper.selectAllByTitle(title);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(blogList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectAllByMerchantId(int page, int pageSize,String merchantId) {
        Page pg = PageHelper.startPage(page,pageSize);
        List<Blog> blogList = blogMapper.selectAllByMerchantId(merchantId);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(blogList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse selectAllByMerchantIdAndTitle(int page, int limit, String merchantId,String title) {
        Page pg = PageHelper.startPage(page,limit);
        List<Blog> blogList = blogMapper.selectAllByMerchantIdAndTitle(merchantId,title);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(blogList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse deleteById(int id) {
        if (id < 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int rowCont = 0;
        try {
             rowCont = blogMapper.delete(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCont > 0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse selectAuthor() {
        Page page = PageHelper.startPage(1,10);
        List<Blog> blogList = blogMapper.selectAll();
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(blogList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
