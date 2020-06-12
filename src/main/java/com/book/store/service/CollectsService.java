package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Collects;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * (Collects)表服务接口
 *
 * @author makejava
 * @since 2020-03-04 17:55:05
 */
public interface CollectsService {

        ServerResponse addCollect(Collects collects);
        ServerResponse selectCollectsByTel(String tel);
        ServerResponse delectByTel(String tel);
        ServerResponse<PageInfo> selectListByTel(String tel, int pageNum, int pageSize);
        ServerResponse deleteById(int id);

}