package com.edu.mum.service;

import com.edu.mum.domain.Payment;

public interface PaymentService {
    Boolean savePayment(Payment payment);
    Payment findPaymentByPostId(Long id);
}
