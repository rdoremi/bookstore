package com.book.store.mapper;

import com.book.store.entity.NavigationBar;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NavigationBar)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-08 09:31:27
 */
public interface NavigationBarMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NavigationBar queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NavigationBar> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param navigationBar 实例对象
     * @return 对象列表
     */
    List<NavigationBar> queryAll(NavigationBar navigationBar);

    /**
     * 新增数据
     *
     * @param navigationBar 实例对象
     * @return 影响行数
     */
    int insert(NavigationBar navigationBar);

    /**
     * 修改数据
     *
     * @param navigationBar 实例对象
     * @return 影响行数
     */
    int update(NavigationBar navigationBar);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<NavigationBar> selectAll();

}