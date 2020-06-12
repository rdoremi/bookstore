package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Contact;

public interface ContactService {
    ServerResponse addContact(Contact contact);
    ServerResponse selectList(int pageNum,int pageSize);
    ServerResponse deleteById(int id);
}
