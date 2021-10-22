package kr.spring.review.vo;

import javax.validation.constraints.NotEmpty;

public class ReviewReplyVO {
	private int rre_num;//댓글번호(대댓글번호)
	@NotEmpty
	private String re_content;//내용
	private String re_ip;
	private String re_date;//등록일
	private String re_modifydate;//수정일
	private int mem_num;//회원번호
	private int review_num;//리뷰 번호(댓글번호)
	private int res_num;//예약번호
	private int auth; //회원권한(후기게시판은 관리자만 댓글(답글)가능) auth=4
	
	public int getRre_num() {
		return rre_num;
	}
	public void setRre_num(int rre_num) {
		this.rre_num = rre_num;
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
	public void setRe_date(String re_date) {
		this.re_date = re_date;
	}
	public String getRe_modifydate() {
		return re_modifydate;
	}
	public void setRe_modifydate(String re_modifydate) {
		this.re_modifydate = re_modifydate;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getReview_num() {
		return review_num;
	}
	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}
	public int getRes_num() {
		return res_num;
	}
	public void setRes_num(int res_num) {
		this.res_num = res_num;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	@Override
	public String toString() {
		return "ReviewReplyVO [rre_num=" + rre_num + ", re_content=" + re_content + ", re_ip=" + re_ip + ", re_date="
				+ re_date + ", re_modifydate=" + re_modifydate + ", mem_num=" + mem_num + ", review_num=" + review_num
				+ ", res_num=" + res_num + ", auth=" + auth + "]";
	}
	
	
	
}
