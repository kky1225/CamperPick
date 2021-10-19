package kr.spring.payment.service;

import kr.spring.payment.vo.PaymentVO;

public interface PaymentService {
	public void payment(PaymentVO paymentVO);
	public PaymentVO getPayment(int res_num);
	public void cancelPayment(String merchant_uid);
}
