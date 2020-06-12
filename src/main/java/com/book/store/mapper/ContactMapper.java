package com.book.store.mapper;

import com.book.store.entity.Contact;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Contact)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-04 14:06:51
 */
public interface ContactMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Contact queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Contact> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param contact 实例对象
     * @return 对象列表
     */
    List<Contact> queryAll(Contact contact);

    /**
     * 新增数据
     *
     * @param contact 实例对象
     * @return 影响行数
     */
    int insert(Contact contact);

    /**
     * 修改数据
     *
     * @param contact 实例对象
     * @return 影响行数
     */
    int update(Contact contact);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Contact> selectList();

}