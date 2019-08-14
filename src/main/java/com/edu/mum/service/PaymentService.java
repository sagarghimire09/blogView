package com.edu.mum.service;

import com.edu.mum.domain.Payment;
import com.edu.mum.domain.User;

public interface PaymentService {
    Boolean savePayment(Payment payment);
    Payment findPaymentByPostId(Long id);
    int getPaidPostCount();
	double getTotalPaidAmount();
	int getPaidPostCountForUser(User user);
	double getPaidAmountForUser(User user);
}
