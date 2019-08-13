package com.edu.mum.service;

import com.edu.mum.domain.Account;

public interface AccountService {
    public abstract void saveAccount(Account account);
    Account getAccountByUser(Long id);
}
