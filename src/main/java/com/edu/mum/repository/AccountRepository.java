package com.edu.mum.repository;

import com.edu.mum.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserId(Long id);
}
