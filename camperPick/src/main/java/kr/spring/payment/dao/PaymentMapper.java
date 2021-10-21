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
	
	@Delete("DELETE FROM payment WHERE res_num IN(SELECT res_num FROM creserve r JOIN croom c ON r.room_num=c.room_num WHERE c.room_num=#{room_num})")
	public void deletePaymentByRoom(Integer room_num);
	
	@Delete("DELETE FROM payment WHERE res_num IN(SELECT res_num FROM creserve r JOIN camping c ON r.camping_num=c.camping_num WHERE c.camping_num=#{camping_num})")
	public void deletePaymentByCamping(Integer camping_num);
}
