package kr.spring.member.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class MemberVO {
	private int mem_num;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String id;
	private int auth;
	@NotEmpty
	private String name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String passwd;
	@NotEmpty
	private String phone;
	@Email
	@NotEmpty
	private String email;
	@Size(min=5, max=5)
	private String zipcode;
	@NotEmpty
	private String address1;
	@NotEmpty
	private String address2;
	private byte[] photo;
	private String photo_name;
	private Date reg_date;
	private Date modify_date;
	
	public boolean isCheckedPassword(String userPasswd) {
		if(auth > 1 && passwd.equals(userPasswd)) {
			return true;
		}
		return false;
	}
	
	public void setUpload(MultipartFile upload) throws IOException{
		//MultipartFile -> byte[]
		setPhoto(upload.getBytes());
		setPhoto_name(upload.getOriginalFilename());
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
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getPhoto_name() {
		return photo_name;
	}
	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
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
	
	@Override
	public String toString() {
		return "MemberVO [mem_num=" + mem_num + ", id=" + id + ", auth=" + auth + ", name=" + name + ", passwd="
				+ passwd + ", phone=" + phone + ", email=" + email + ", zipcode=" + zipcode + ", address1=" + address1
				+ ", address2=" + address2 + ", photo_name=" + photo_name + ", reg_date=" + reg_date + ", modify_date="
				+ modify_date + "]";
	}
	
}
