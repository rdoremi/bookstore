package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {

    ServerResponse addComment(Comment comment,String orderNo);
    ServerResponse selectListByMerchantId(String merchantId);
    ServerResponse selectListByTel(String tel);
    ServerResponse selectListByProductId(Integer productId);
    ServerResponse<PageInfo> selectByProductId(Integer productId,int pageNum,int pageSize);
}
