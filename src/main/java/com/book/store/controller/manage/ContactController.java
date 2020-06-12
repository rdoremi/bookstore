package com.book.store.controller.manage;

import com.book.store.common.ServerResponse;
import com.book.store.service.impl.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin/contact")
public class ContactController {
    @Autowired
    private ContactServiceImpl contactService;

    @RequestMapping("/get_contact")
    public ServerResponse getContact(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit){

        return contactService.selectList(page,limit);
    }

}
