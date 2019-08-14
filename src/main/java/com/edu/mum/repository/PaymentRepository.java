package com.edu.mum.repository;

import com.edu.mum.domain.Payment;
import com.edu.mum.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPostId(Long id);
    
    @Query(value = "SELECT sum(payAmount) FROM Payment")
	double sumPayment();
  
}
