package com.book.store.service.impl;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Comment;
import com.book.store.entity.Order;
import com.book.store.entity.OrderItem;
import com.book.store.mapper.CommentMapper;
import com.book.store.mapper.OrderItemMapper;
import com.book.store.mapper.OrderMapper;
import com.book.store.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public ServerResponse addComment(Comment comment,String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        OrderItem orderItem = orderItemMapper.selectOneByOrderNo(orderNo);
        if (order == null||order.getStatus() != Const.Order.GET_PRODUCT){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"订单未完成或已评论");
        }
        comment.setMerchantId(order.getMerchantId());
        comment.setProductId(orderItem.getProductId());
        int rowCount = 0;
        try {
            rowCount = commentMapper.insert(comment);
            if (rowCount > 0){
                order.setStatus(Const.Order.REVIEWED);
                orderMapper.updateStatus(order);
               return ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
            }
        }catch (Exception e){
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"发布失败");
    }

    @Override
    public ServerResponse<PageInfo> selectByProductId(Integer productId, int pageNum, int pageSize) {
        if (productId < 0 || pageNum < 0 || pageSize<0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments = commentMapper.selectByProductId(productId);
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(comments);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse selectListByMerchantId(String merchantId) {
        return null;
    }

    @Override
    public ServerResponse selectListByTel(String tel) {
        return null;
    }

    @Override
    public ServerResponse selectListByProductId(Integer productId) {
        return null;
    }
}
