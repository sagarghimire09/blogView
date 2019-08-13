package com.edu.mum.service.impl;

import com.edu.mum.domain.Payment;
import com.edu.mum.repository.PaymentRepository;
import com.edu.mum.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @Override
    public Boolean savePayment(Payment payment) {
        paymentRepository.save(payment);
        return true;
    }

    @Override
    public Payment findPaymentByPostId(Long id) {
        return paymentRepository.findByPostId(id);
    }
}
