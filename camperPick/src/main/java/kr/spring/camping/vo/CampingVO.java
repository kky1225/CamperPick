package kr.spring.camping.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class CampingVO {
	private int camping_num;
	@NotEmpty
	private String camp_name;
	@NotEmpty
	private String camp_address;
	@Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$")
	private String camp_phone;
	@Range(min=1,max=10000)
	private int rcount;		//객실 수
	public int getCamping_num() {
		return camping_num;
	}
	public void setCamping_num(int camping_num) {
		this.camping_num = camping_num;
	}
	public String getCamp_name() {
		return camp_name;
	}
	public void setCamp_name(String camp_name) {
		this.camp_name = camp_name;
	}
	public String getCamp_address() {
		return camp_address;
	}
	public void setCamp_address(String camp_address) {
		this.camp_address = camp_address;
	}
	public String getCamp_phone() {
		return camp_phone;
	}
	public void setCamp_phone(String camp_phone) {
		this.camp_phone = camp_phone;
	}
	public int getRcount() {
		return rcount;
	}
	public void setRcount(int rcount) {
		this.rcount = rcount;
	}
	@Override
	public String toString() {
		return "CampingVO [camping_num=" + camping_num + ", camp_name=" + camp_name + ", camp_address=" + camp_address
				+ ", camp_phone=" + camp_phone + ", rcount=" + rcount + "]";
	}
	
	
	
	
	
	
	
	
	
}
