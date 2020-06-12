package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.MyAccount;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * (MyAccount)表服务接口
 *
 * @author makejava
 * @since 2020-03-08 11:25:58
 */
public interface MyAccountService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MyAccount queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MyAccount> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param myAccount 实例对象
     * @return 实例对象
     */
    ServerResponse insert(MyAccount myAccount);

    /**
     * 修改数据
     *
     * @param myAccount 实例对象
     * @return 实例对象
     */
    MyAccount update(MyAccount myAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    ServerResponse selectMyAccount(String merchantId);

    ServerResponse<PageInfo> selectAllMyAccount(int pageNum, int pageSize);

    ServerResponse<PageInfo> selectAllTransferMyAccount(int page, int limit);

    ServerResponse<PageInfo> selectAllMyAccountAndTel(int page, int limit, String tel);
}