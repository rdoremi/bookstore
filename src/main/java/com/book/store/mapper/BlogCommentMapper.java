package com.book.store.mapper;

import com.book.store.entity.BlogComment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (BlogComment)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-04 12:08:56
 */
public interface BlogCommentMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BlogComment queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<BlogComment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param blogComment 实例对象
     * @return 对象列表
     */
    List<BlogComment> queryAll(BlogComment blogComment);

    /**
     * 新增数据
     *
     * @param blogComment 实例对象
     * @return 影响行数
     */
    int insert(BlogComment blogComment);

    /**
     * 修改数据
     *
     * @param blogComment 实例对象
     * @return 影响行数
     */
    int update(BlogComment blogComment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    List<BlogComment> selectByBLogId(Integer blogId);

    int checkTel(@Param("blogId") Integer blogId,@Param("tel") String tel);

}