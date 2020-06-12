package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.BlogComment;

public interface BlogCommentService {
    ServerResponse addComment(BlogComment comment);
    ServerResponse deleteById(int id);
    ServerResponse selectByTel(String tel);
    ServerResponse selectByBlogId(Integer blogId);
    ServerResponse selectList(Integer blogId,int page,int pageSize);
}
