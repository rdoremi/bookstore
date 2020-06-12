package com.book.store.mapper;

import com.book.store.entity.Collects;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * (Collects)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-04 17:55:05
 */
public interface CollectsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Collects queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Collects> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param collects 实例对象
     * @return 对象列表
     */
    List<Collects> queryAll(Collects collects);

    /**
     * 新增数据
     *
     * @param collects 实例对象
     * @return 影响行数
     */
    int insert(Collects collects);

    /**
     * 修改数据
     *
     * @param collects 实例对象
     * @return 影响行数
     */
    int update(Collects collects);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Collects> selectByTel(String tel);

    int checkByProductIdAndTel(@Param("productId") Integer productId, @Param("tel") String tel);


}