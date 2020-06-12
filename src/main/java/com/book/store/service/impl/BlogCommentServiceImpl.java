package com.book.store.service.impl;

import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.BlogComment;
import com.book.store.mapper.BlogCommentMapper;
import com.book.store.service.BlogCommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    public ServerResponse addComment(BlogComment comment) {
        int rowCount = 0;
        if (StringUtils.isEmpty(comment.getDetail())||" ".equals(comment.getDetail())){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"内容不能为空");
        }
        int num = blogCommentMapper.checkTel(comment.getBlogId(),comment.getTel());
        if (num > 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"您已发表过评论");
        }
        try {
            rowCount = blogCommentMapper.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowCount > 0 ? ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc()) : ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "发布失败");
    }

    @Override
    public ServerResponse deleteById(int id) {
        return null;
    }

    @Override
    public ServerResponse selectByTel(String tel) {
        return null;
    }

    @Override
    public ServerResponse selectByBlogId(Integer blogId) {
        return null;
    }

    @Override
    public ServerResponse selectList(Integer blogId, int page, int pageSize) {
        if (blogId < 0 || page < 0 || pageSize < 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Page pg = PageHelper.startPage(page,pageSize);
        List<BlogComment> blogComments = blogCommentMapper.selectByBLogId(blogId);
        PageInfo pageInfo = new PageInfo(pg);
        pageInfo.setList(blogComments);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
