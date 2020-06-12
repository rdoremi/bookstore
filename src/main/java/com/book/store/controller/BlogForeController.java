package com.book.store.controller;

import com.book.store.common.Const;
import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.BlogComment;
import com.book.store.entity.User;
import com.book.store.service.impl.BlogCommentServiceImpl;
import com.book.store.service.impl.BlogServiceImpl;
import com.book.store.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/fore/blog")
public class BlogForeController {

    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private BlogCommentServiceImpl blogCommentService;

    @GetMapping("/get_blog")
    public ServerResponse getBLog(int id){

        return blogService.selectBlogById(id);
    }
    @PostMapping("/add_comment")
    public ServerResponse addComment(HttpSession session,BlogComment comment){
        User user = (User) session.getAttribute(Const.FORE_CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录");
        }
        comment.setTel(user.getTel());
        comment.setUsername(user.getUsername());
        return blogCommentService.addComment(comment);
    }
    @GetMapping("/get_blog_comment")
    public ServerResponse getBlogComment(int blogId,@RequestParam(value = "page",defaultValue = "1") int page,@RequestParam(value = "limit",defaultValue = "10") int limit){
        return blogCommentService.selectList(blogId,page,limit);
    }
    @GetMapping("/get_present_user")
    public ServerResponse getPresentUser(){
        return blogService.selectAuthor();
    }

}
