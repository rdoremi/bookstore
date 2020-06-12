package com.book.store.mapper;



import com.book.store.common.ServerResponse;
import com.book.store.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int addOrder(Order order);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int checkCount(@Param("merchantId")String merchantId,@Param("orderNo")String orderNo);

    Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") String orderNo);

    Order selectByOrderNo(String orderNo);

    List<Order> selectByUserId(Integer userId);


    List<Order> selectByUserIdByStatus(@Param("merchantId")String merchantId,@Param("status")Integer status);

    List<Order> selectAllOrder();

    int updatePayMessage(Order order);
    //以下商家处理
    List<Order> selectOrderByMerchantId(@Param("merchantId")String merchantId);

    //int sentProduct(@Param("merchantId")String merchantId,@Param("orderNo")Long orderNo,@Param("status") Integer status);
    int updateOrderStatus(Order order);

    int updateStatus(Order order);

    int updateEnterGoodsOrderStatus(Order order);

    int cancelOrderStatus(Order order);

    int delOrderByOrderNo(@Param("orderNo")String orderNo);

    List<Order> selectOrderByMerchantIdAndGet(@Param("merchantId") String merchantId, @Param("status") int status);

    List<Order> selectOrderByMerchantIdUseTrades(@Param("merchantId") String merchantId);//用于统计

    List<Order> selectOrderByMerchantIdAndOrderNo(@Param("merchantId")String merchantId, @Param("orderNo")String orderNo);
}
