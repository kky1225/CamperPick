package kr.spring.reservation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.reservation.vo.ReservationVO;

public interface ReservationMapper {
	@Select("SELECT creserve_seq.nextval FROM dual")
	public int selectMem_num();
	@Insert("INSERT INTO creserve (res_num,res_email,res_name,res_phone,headcount,res_start,res_end,mem_num,room_num,camping_num,res_price) VALUES (#{res_num},#{res_email},#{res_name},#{res_phone},#{headcount},#{res_start},#{res_end},#{mem_num},#{room_num},#{camping_num},#{res_price})")
	public void insertReservation(ReservationVO reservation);
	public void updateReservation(ReservationVO reservation);
	@Delete("DELETE FROM creserve WHERE res_num=#{res_num}")
	public void deleteReservation(Integer res_num);
	@Delete("DELETE FROM creserve WHERE room_num=#{room_num}")
	public void deleteReservationToo(Integer room_num);
	@Delete("DELETE FROM creserve WHERE camping_num=#{camping_num}")
	public void deleteReservationFirst(Integer camping_num);
	public List<ReservationVO> getReservationList(Map<String,Object> map);
	public int getReservationCount(Map<String,Object> map);
	public ReservationVO getReservation(Integer res_num);
	public ReservationVO getRecentReservation(Integer mem_num);
	//결제 완료 시 예약완료로 예약상태 바꿔줌.
	@Update("UPDATE creserve SET res_state='예약완료' WHERE res_num=#{res_num}")
	public void changeState(Integer res_num);
}
