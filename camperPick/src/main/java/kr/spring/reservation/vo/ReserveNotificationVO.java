package kr.spring.reservation.vo;

import java.sql.Date;

public class ReserveNotificationVO {
	private int not_num;
	private String message;
	private Date date_time;
	private Date read_time;
	private int res_num;
	private int mem_num;
	public int getNot_num() {
		return not_num;
	}
	public void setNot_num(int not_num) {
		this.not_num = not_num;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public Date getRead_time() {
		return read_time;
	}
	public void setRead_time(Date read_time) {
		this.read_time = read_time;
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
	
	@Override
	public String toString() {
		return "ReserveNotificationVO [not_num=" + not_num + ", message=" + message + ", date_time=" + date_time
				+ ", read_time=" + read_time + ", res_num=" + res_num + ", mem_num=" + mem_num + "]";
	}
	
}
