package com.book.store.mapper;


import com.book.store.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int addOrderItem(OrderItem orderItem);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByOrderNoUserId(@Param("orderNo") String orderNo, @Param("userId") Integer userId);

    List<OrderItem> getByOrderNo(@Param("orderNo") String orderNo);



    void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);


    List<OrderItem> selectByOrderNo( String orderNo);

    OrderItem selectOneByOrderNo( String orderNo);

    int delOrderByOrderNo( @Param("orderNo")String orderNo);
}