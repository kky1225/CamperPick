package kr.spring.review.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import kr.spring.util.DurationFromNow;

public class ReviewVO {
	private int review_num; //리뷰번호
	private int res_num; //예약번호
	@NotEmpty
	private String title;//제목
	@NotEmpty
	private String content;//내용
	private int hit;//조회수
	private String reg_date;//등록일
	private String modify_date;//수정일
    private byte[] uploadfile;//업로드 파일
    private String filename;//파일명
    private String ip;//ip
    private int mem_num;//회원번호
    private String id;//id
    private int camping_num;//캠핑장번호
	
    //업로드 파일 처리
    public void setUpload(MultipartFile upload)throws IOException{
    	//MultipartFile -> byte[]
    	setUploadfile(upload.getBytes());
    	//파일명 구하기
    	setFilename(upload.getOriginalFilename());
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	// 날짜 표기 형식을 변경(예 5초전)
	public void setReg_date(String reg_date) {
		this.reg_date = DurationFromNow.getTimeDiffLabel(reg_date);
	}

	public String getModify_date() {
		return modify_date;
	}
	// 날짜 표기 형식을 변경(예 5초전)
	public void setModify_date(String modify_date) {
		this.modify_date = DurationFromNow.getTimeDiffLabel(modify_date);
	}

	public byte[] getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(byte[] uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public int getCamping_num() {
		return camping_num;
	}

	public void setCamping_num(int camping_num) {
		this.camping_num = camping_num;
	}

	@Override
	public String toString() {
		return "ReviewVO [review_num=" + review_num + ", res_num=" + res_num + ", title=" + title + ", content="
				+ content + ", hit=" + hit + ", reg_date=" + reg_date + ", modify_date=" + modify_date + ", filename="
				+ filename + ", ip=" + ip + ", mem_num=" + mem_num + ", id=" + id + ", camping_num=" + camping_num
				+ "]";
	}

	


}
