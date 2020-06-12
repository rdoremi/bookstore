package com.book.store.service;


import com.book.store.common.ServerResponse;
import com.book.store.entity.Address;
import com.book.store.vo.AddressVo;

import java.util.List;

public interface AddressService {
    ServerResponse add(Address address);
    ServerResponse<List<AddressVo>> getAddressByUserId(Integer userId);
    ServerResponse deleteAddressById(Integer id);
}
