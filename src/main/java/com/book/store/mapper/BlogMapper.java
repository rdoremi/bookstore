package com.book.store.mapper;

import com.book.store.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    int insert(Blog blog);
    int update(Blog blog);
    int delete(int id);
    Blog selectById(int id);
    List<Blog> selectAll();
    List<Blog> selectAllByMerchantId(@Param("merchantId") String merchantId);
    List<Blog> selectByMerchantId(String merchantId);
    List<Blog> selectByAuthorId(int authorId);

    List<Blog> selectAllByTitle(String title);

    List<Blog> selectAllByMerchantIdAndTitle(@Param("merchantId")String merchantId, @Param("title")String title);
}
