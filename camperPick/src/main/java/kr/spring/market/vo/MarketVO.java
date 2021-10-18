package kr.spring.market.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class MarketVO {
	private int market_num;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private int hit;
	private Date reg_date;
	private Date modify_date;
	private byte[] uploadfile;
	private String filename;
	private String ip;
	private String state;	// 거래상태 0:거래중, 1:거래완료
	private String choice;	// 거래구분 0:팝니다, 1:삽니다
	private int mem_num;
	private String passwd;
	private String name;
	
	// 업로드 파일 처리
	public void setUpload(MultipartFile upload) throws IOException{
		// MultiPartFile -> byte[]
		setUploadfile(upload.getBytes());
		// 파일명 구하기
		setFilename(upload.getOriginalFilename());
	}
	
	public int getMarket_num() {
		return market_num;
	}
	public void setMarket_num(int market_num) {
		this.market_num = market_num;
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
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "MarketVO [market_num=" + market_num + ", title=" + title + ", content=" + content + ", hit=" + hit
				+ ", reg_date=" + reg_date + ", modify_date=" + modify_date + ", filename=" + filename + ", ip=" + ip
				+ ", state=" + state + ", choice=" + choice + ", mem_num=" + mem_num + ", passwd=" + passwd + ", name="
				+ name + "]";
	}
	
	
}
