package kr.spring.payment.vo;

import java.sql.Date;

public class PaymentVO {
	private int pay_num;
	private String imp_uid;
	private String merchant_uid;
	private String biz_email;
	private Date pay_date;
	private int amount;
	private int res_num;
	private int mem_num;
	private String camp_name;
	
	public int getPay_num() {
		return pay_num;
	}
	public void setPay_num(int pay_num) {
		this.pay_num = pay_num;
	}
	public String getImp_uid() {
		return imp_uid;
	}
	public void setImp_uid(String imp_uid) {
		this.imp_uid = imp_uid;
	}
	public String getMerchant_uid() {
		return merchant_uid;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public String getBiz_email() {
		return biz_email;
	}
	public void setBiz_email(String biz_email) {
		this.biz_email = biz_email;
	}
	public Date getPay_date() {
		return pay_date;
	}
	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getRes_num() {
		return res_num;
	}
	public void setRes_num(int res_num) {
		this.res_num = res_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	public String getCamp_name() {
		return camp_name;
	}
	public void setCamp_name(String camp_name) {
		this.camp_name = camp_name;
	}
	
	@Override
	public String toString() {
		return "PaymentVO [pay_num=" + pay_num + ", imp_uid=" + imp_uid + ", merchant_uid=" + merchant_uid
				+ ", biz_email=" + biz_email + ", pay_date=" + pay_date + ", amount=" + amount + ", res_num=" + res_num
				+ ", mem_num=" + mem_num + ", camp_name=" + camp_name + "]";
	}
	
	
}
