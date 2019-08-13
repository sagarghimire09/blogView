package com.edu.mum.repository;

import com.edu.mum.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPostId(Long id);
}
