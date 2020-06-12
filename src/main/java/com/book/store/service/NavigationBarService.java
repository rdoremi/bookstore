package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.NavigationBar;
import java.util.List;

/**
 * (NavigationBar)表服务接口
 *
 * @author makejava
 * @since 2020-03-08 09:31:27
 */
public interface NavigationBarService {

   ServerResponse addNavigationBar(NavigationBar navigationBar);
   ServerResponse updateNavigationBarById(NavigationBar navigationBar);
   ServerResponse selectNavigationBar();
   ServerResponse deleteNavigationBarById(Integer id);
}