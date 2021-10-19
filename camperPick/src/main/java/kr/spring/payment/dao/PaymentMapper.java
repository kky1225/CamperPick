package kr.spring.payment.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import kr.spring.payment.vo.PaymentVO;

public interface PaymentMapper {
	@Insert("INSERT INTO payment VALUES (payment_seq.nextval, #{imp_uid}, #{merchant_uid}, #{biz_email}, #{pay_date}, #{amount}, #{res_num}, #{mem_num})")
	public void payment(PaymentVO paymentVO);
	
	@Select("SELECT * FROM payment WHERE res_num=#{res_num}")
	public PaymentVO getPayment(int res_num);
	
	@Delete("DELETE FROM payment WHERE merchant_uid=#{merchant_uid}")
	public void cancelPayment(String merchant_uid);
}
