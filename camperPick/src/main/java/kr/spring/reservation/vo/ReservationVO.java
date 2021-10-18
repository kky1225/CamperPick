package kr.spring.reservation.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class ReservationVO {
	private int res_num;
	@NotEmpty
	@Email
	private String res_email;
	@NotEmpty
	private String res_name;
	@Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$")
	private String res_phone;
	@Range(min=1,max=100)
	private int headcount;
	@NotEmpty
	private String res_start;
	@NotEmpty
	private String res_end;
	private String res_state;
	private int mem_num;
	private int room_num;
	private int camping_num;
	private int res_price;
	
	private String camp_name;
	private String room_name;
	
	
	public int getRes_num() {
		return res_num;
	}
	public void setRes_num(int res_num) {
		this.res_num = res_num;
	}
	public String getRes_name() {
		return res_name;
	}
	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}
	public String getRes_phone() {
		return res_phone;
	}
	public void setRes_phone(String res_phone) {
		this.res_phone = res_phone;
	}
	public int getHeadcount() {
		return headcount;
	}
	public void setHeadcount(int headcount) {
		this.headcount = headcount;
	}
	public String getRes_start() {
		return res_start;
	}
	public void setRes_start(String res_start) {
		this.res_start = res_start;
	}
	public String getRes_end() {
		return res_end;
	}
	public void setRes_end(String res_end) {
		this.res_end = res_end;
	}
	
	
	public String getRes_state() {
		return res_state;
	}
	public void setRes_state(String res_state) {
		this.res_state = res_state;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getRoom_num() {
		return room_num;
	}
	public void setRoom_num(int room_num) {
		this.room_num = room_num;
	}
	public int getCamping_num() {
		return camping_num;
	}
	public void setCamping_num(int camping_num) {
		this.camping_num = camping_num;
	}
	public String getRes_email() {
		return res_email;
	}
	public void setRes_email(String res_email) {
		this.res_email = res_email;
	}
	
	public String getCamp_name() {
		return camp_name;
	}
	public void setCamp_name(String camp_name) {
		this.camp_name = camp_name;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	
	public int getRes_price() {
		return res_price;
	}
	public void setRes_price(int res_price) {
		this.res_price = res_price;
	}
	@Override
	public String toString() {
		return "ReservationVO [res_num=" + res_num + ", res_email=" + res_email + ", res_name=" + res_name
				+ ", res_phone=" + res_phone + ", headcount=" + headcount + ", res_start=" + res_start + ", res_end="
				+ res_end + ", res_state=" + res_state + ", mem_num=" + mem_num + ", room_num=" + room_num
				+ ", camping_num=" + camping_num + ", res_price=" + res_price + ", camp_name=" + camp_name
				+ ", room_name=" + room_name + "]";
	}
	
	
	
	
	
}
