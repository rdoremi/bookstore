package com.book.store.controller.manage;

import com.book.store.common.ServerResponse;
import com.book.store.entity.NavigationBar;
import com.book.store.service.impl.NavigationBarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/nav")
public class NavigationBarController {

    @Autowired
    private NavigationBarServiceImpl navigationBarService;

    @PostMapping("/add_nav")
    public ServerResponse addNav(NavigationBar navigationBar){

        return navigationBarService.addNavigationBar(navigationBar);
    }
    @PostMapping("/update_nav")
    public ServerResponse updateNav(NavigationBar navigationBar){
        return navigationBarService.updateNavigationBarById(navigationBar);
    }
    @RequestMapping("/delete_nav")
    public ServerResponse deleteNav(int id){
        return navigationBarService.deleteNavigationBarById(id);
    }
    @RequestMapping("/get_nav")
    public ServerResponse getNav(){
        return navigationBarService.selectNavigationBar();
    }
}
