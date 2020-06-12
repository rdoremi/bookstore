package com.book.store.controller;

import com.book.store.common.ServerResponse;
import com.book.store.service.NavigationBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fore/nav")
public class ForNavController {

    @Autowired
    private NavigationBarService navigationBarService;

    @RequestMapping("/get_nav")
    public ServerResponse getNav(){
        return navigationBarService.selectNavigationBar();
    }
}
