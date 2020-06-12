package com.book.store.mapper;

import com.book.store.entity.MyAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * (MyAccount)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-08 11:25:58
 */
public interface MyAccountMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MyAccount queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MyAccount> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param myAccount 实例对象
     * @return 对象列表
     */
    List<MyAccount> queryAll(MyAccount myAccount);

    /**
     * 新增数据
     *
     * @param myAccount 实例对象
     * @return 影响行数
     */
    int insert(MyAccount myAccount);

    /**
     * 修改数据
     *
     * @param myAccount 实例对象
     * @return 影响行数
     */
    int update(MyAccount myAccount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    int updateMyAccount(@Param("merchantId") String merchantId, @Param("balance") BigDecimal balance);


    MyAccount selectMyAccountByMerchantId(@Param("merchantId") String merchantId);

    List<MyAccount> selectAll();

    int updateAccount(MyAccount myAccount);

    List<MyAccount> selectNeedTransFer(Integer status);

    List<MyAccount> selectByTel(String tel);
}