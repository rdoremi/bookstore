package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Blog;
import com.github.pagehelper.PageInfo;

public interface BlogService {
    ServerResponse addBlog(Blog blog);
    ServerResponse updateBlog(Blog blog);
    ServerResponse deleteById(int id);
    ServerResponse<Blog> selectBlogById(int id);
    ServerResponse<PageInfo> selectBlogByMerchantId(String merchantId,int page,int pageSize);
    ServerResponse<PageInfo> selectAll(int page,int pageSize);
    ServerResponse<PageInfo> selectAllByMerchantId(int page, int pageSize,String merchantId);

    ServerResponse selectAuthor();

    ServerResponse selectAllByTitle(int page, int limit, String title);

    ServerResponse selectAllByMerchantIdAndTitle(int page, int limit, String merchantId,String title);
}
