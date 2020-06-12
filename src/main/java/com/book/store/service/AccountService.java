package com.book.store.service;

import com.book.store.common.ServerResponse;
import com.book.store.entity.Account;

public interface AccountService {

    ServerResponse addAccount(Account account);
    ServerResponse selectAll();

    ServerResponse selectByMerchantId(String merchantId);

    ServerResponse selectByMerchantIdAnMonth(String merchantId);
}
