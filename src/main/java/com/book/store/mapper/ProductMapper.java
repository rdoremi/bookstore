package com.book.store.mapper;



import com.book.store.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product product);

    int setComment(Product product);

    int insertSelective(Product product);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product product);

    int updateByPrimaryKey(Product product);

    List<Product> selectList();
    List<Product> selectListByMerchantId(String merchantId);


    List<Product> selectListOrderByRecommend();

    List<Product> selectByNameAndProductId(@Param("name") String name, @Param("id") Integer id);

    List<Product> selectProductByCategoryId(Integer categoryId);

    List<Product> selectProductByRecommend();

    List<Product> selectProductByMerchantId(String merchantId);

    int checkProductCount(Integer id);//查询是否有该商品

    Product selectById(Integer id);

    List<Product> selectListBySearch(String name);

    List<Product> selectByCategory(Integer categoryChildId);

    List<Product> selectListByMerchantIdAndSearch(@Param("merchantId")String merchantId, @Param("name")String name);
}