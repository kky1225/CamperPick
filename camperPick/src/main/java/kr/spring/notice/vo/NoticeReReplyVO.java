package kr.spring.notice.vo;

import kr.spring.util.DurationFromNow;

public class NoticeReReplyVO {
	private int nrre_num;
	private String re_content;	//내용
	private String re_ip;
	private String re_date;	//등록일
	private String re_modifydate;	//수정일
	private int mem_num;	//회원번호
	private int nre_num;	//공지사항 댓글 번호
	private int notice_num;	// 공지사항 번호
	private String name;	// 회원이름
	public int getNrre_num() {
		return nrre_num;
	}
	public void setNrre_num(int nrre_num) {
		this.nrre_num = nrre_num;
	}
	public String getRe_content() {
		return re_content;
	}
	public void setRe_content(String re_content) {
		this.re_content = re_content;
	}
	public String getRe_ip() {
		return re_ip;
	}
	public void setRe_ip(String re_ip) {
		this.re_ip = re_ip;
	}
	public String getRe_date() {
		return re_date;
	}
	// 날짜 표기 형식을 변경(예 5초전)
	public void setRe_date(String re_date) {
		this.re_date = DurationFromNow.getTimeDiffLabel(re_date);
	}
	public String getRe_modifydate() {
		return re_modifydate;
	}
	// 날짜 표기 형식을 변경(예 5초전)
	public void setRe_modifydate(String re_modifydate) {
		this.re_modifydate = DurationFromNow.getTimeDiffLabel(re_modifydate);
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getNre_num() {
		return nre_num;
	}
	public void setNre_num(int nre_num) {
		this.nre_num = nre_num;
	}
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "NoticeReReplyVO [nrre_num=" + nrre_num + ", re_content=" + re_content + ", re_ip=" + re_ip
				+ ", re_date=" + re_date + ", re_modifydate=" + re_modifydate + ", mem_num=" + mem_num + ", nre_num="
				+ nre_num + ", notice_num=" + notice_num + ", name=" + name + "]";
	}
	
	
}
