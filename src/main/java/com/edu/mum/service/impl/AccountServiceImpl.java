package com.edu.mum.service.impl;

import com.edu.mum.domain.Account;
import com.edu.mum.repository.AccountRepository;
import com.edu.mum.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public void saveAccount(Account account) {
         accountRepository.save(account);
    }

    @Override
    public Account getAccountByUser(Long id) {
        return accountRepository.findByUserId(id);
    }
}

