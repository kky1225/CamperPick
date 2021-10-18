package kr.spring.room.vo;

import java.io.IOException;
import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class RoomVO {
	private int room_num;
	@NotEmpty
	private String room_name;
	@Range(min=1,max=100)
	private int people;			//정원 수
	@Range(min=1,max=100000)
	private int area;			//면적
	@Range(min=1,max=1000000)
	private int price;
	@Pattern(regexp="^\\d{1,2}:\\d{1,2}$")
	private String checkIn;
	@Pattern(regexp="^\\d{1,2}:\\d{1,2}$")
	private String checkOut;
	@NotEmpty
	private String info;
	private byte[] uploadfile;
	private String filename;
	private int camping_num;
	
	//업로드 파일 처리
		public void setUpload(MultipartFile upload)throws IOException {
			//multipartfile타입을 byte[] 로 바꿔줌
			setUploadfile(upload.getBytes());
			//파일명 구하기
			setFilename(upload.getOriginalFilename());
		}
		
	public int getRoom_num() {
		return room_num;
	}
	public void setRoom_num(int room_num) {
		this.room_num = room_num;
	}
	
	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	public int getCamping_num() {
		return camping_num;
	}
	public void setCamping_num(int camping_num) {
		this.camping_num = camping_num;
	}

	@Override
	public String toString() {
		return "RoomVO [room_num=" + room_num + ", room_name=" + room_name + ", people=" + people + ", area=" + area
				+ ", price=" + price + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", info=" + info
				+ ", uploadfile=" + Arrays.toString(uploadfile) + ", filename=" + filename + ", camping_num="
				+ camping_num + "]";
	}

	
}
