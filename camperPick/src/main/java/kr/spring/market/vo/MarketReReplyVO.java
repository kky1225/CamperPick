package kr.spring.market.vo;

import kr.spring.util.DurationFromNow;

public class MarketReReplyVO {
	private int mrre_num;
	private String re_content;	//내용
	private String re_ip;
	private String re_date;	//등록일
	private String re_modifydate;	//수정일
	private int mem_num;	//회원번호
	private int mre_num;	//거래게시판 댓글 번호
	private int market_num;	// 거래게시판 번호
	private String name;	// 회원이름
	public int getMrre_num() {
		return mrre_num;
	}
	public void setMrre_num(int mrre_num) {
		this.mrre_num = mrre_num;
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
	public int getMre_num() {
		return mre_num;
	}
	public void setMre_num(int mre_num) {
		this.mre_num = mre_num;
	}
	public int getMarket_num() {
		return market_num;
	}
	public void setMarket_num(int market_num) {
		this.market_num = market_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "MarketReReplyVO [mrre_num=" + mrre_num + ", re_content=" + re_content + ", re_ip=" + re_ip
				+ ", re_date=" + re_date + ", re_modifydate=" + re_modifydate + ", mem_num=" + mem_num + ", mre_num="
				+ mre_num + ", market_num=" + market_num + ", name=" + name + "]";
	}
	
	
}
