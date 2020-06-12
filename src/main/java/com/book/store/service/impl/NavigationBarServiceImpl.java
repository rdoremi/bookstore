package com.book.store.service.impl;

import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.NavigationBar;

import com.book.store.mapper.NavigationBarMapper;
import com.book.store.service.NavigationBarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NavigationBar)表服务实现类
 *
 * @author makejava
 * @since 2020-03-08 09:31:27
 */
@Service
public class NavigationBarServiceImpl implements NavigationBarService {
    @Resource
    private NavigationBarMapper navigationBarMapper;

    @Override
    public ServerResponse addNavigationBar(NavigationBar navigationBar) {
        int rowCount = 0;
        try {
             rowCount = navigationBarMapper.insert(navigationBar);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount > 0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    @Override
    public ServerResponse updateNavigationBarById(NavigationBar navigationBar) {
        int rowCount = 0;
        try {
            rowCount = navigationBarMapper.update(navigationBar);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount > 0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());

    }

    @Override
    public ServerResponse selectNavigationBar() {
        List<NavigationBar> list = navigationBarMapper.selectAll();
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse deleteNavigationBarById(Integer id) {
        int rowCount = 0;
        try {
            rowCount = navigationBarMapper.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount > 0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());

    }
}