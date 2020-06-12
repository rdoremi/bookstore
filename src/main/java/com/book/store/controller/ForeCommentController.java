package com.book.store.controller;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Comment;
import com.book.store.entity.User;
import com.book.store.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/fore/comment")
public class ForeCommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/add_comment")
    public ServerResponse addComment(HttpSession session,Comment comment, String orderNo){

        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        comment.setTel(user.getTel());
        comment.setUsername(user.getUsername());
        return commentService.addComment(comment,orderNo);
    }
    @GetMapping("/get_product_comment")
    public ServerResponse getProductComment(int productId, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit){

        return commentService.selectByProductId(productId,page,limit);
    }
}
