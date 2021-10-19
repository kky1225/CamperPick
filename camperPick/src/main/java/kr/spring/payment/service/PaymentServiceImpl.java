package kr.spring.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.payment.dao.PaymentMapper;
import kr.spring.payment.vo.PaymentVO;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentMapper paymentMapper;

	@Override
	public void payment(PaymentVO paymentVO) {
		paymentMapper.payment(paymentVO);
	}

	@Override
	public PaymentVO getPayment(int res_num) {
		return paymentMapper.getPayment(res_num);
	}

	@Override
	public void cancelPayment(String merchant_uid) {
		paymentMapper.cancelPayment(merchant_uid);	
	}


}
