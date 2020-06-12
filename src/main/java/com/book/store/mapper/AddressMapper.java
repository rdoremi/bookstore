package com.book.store.mapper;


import com.book.store.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface AddressMapper {
    public int insert(Address address);
    public int update(Address address);
    public int deleteById(Integer id);
    public List<Address> selectAddressByUserId(Integer userId);
    public Address selectAddressById(Integer id);
}
