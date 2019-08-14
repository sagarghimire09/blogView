package com.edu.mum.service.impl;

import com.edu.mum.domain.Payment;
import com.edu.mum.domain.User;
import com.edu.mum.repository.PaymentRepository;
import com.edu.mum.service.PaymentService;

import java.util.List;

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
	@Override
	public int getPaidPostCount() {
		return (int) paymentRepository.count();
	}
	@Override
	public double getTotalPaidAmount() {
		return paymentRepository.sumPayment();
	}
	@Override
	public int getPaidPostCountForUser(User user) {
		int res = 0;
		List<Payment> pays = paymentRepository.findAll();
		for(Payment p:pays) {
			if(p.getUser().equals(user)) {
				res += 1;
			}
		}
		return res;
	}
	
	@Override
	public double getPaidAmountForUser(User user) {
		double res = 0;
		List<Payment> payments = paymentRepository.findAll();
		for(Payment p:payments) {
			if(p.getUser().equals(user)) {
				res += p.getPayAmount();
			}
		}
		return res;
	}
	
}
