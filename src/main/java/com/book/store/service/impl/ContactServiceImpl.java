package com.book.store.service.impl;

import com.book.store.common.ResponseCode;
import com.book.store.common.ServerResponse;
import com.book.store.entity.Contact;
import com.book.store.mapper.ContactMapper;
import com.book.store.service.ContactService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactMapper contactMapper;
    @Override
    public ServerResponse addContact(Contact contact) {
        int rowCount = 0;
        try {
            rowCount = contactMapper.insert(contact);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount>0?ServerResponse.createBySuccessCodeMessage(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc()):ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"留言失败");
    }

    @Override
    public ServerResponse selectList(int pageNum, int pageSize) {
        if (pageNum < 0|| pageSize < 0){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<Contact> contactList = contactMapper.selectList();
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(contactList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse deleteById(int id) {
        return null;
    }
}
