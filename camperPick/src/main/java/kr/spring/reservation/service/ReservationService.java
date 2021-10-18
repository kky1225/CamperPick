package kr.spring.reservation.service;

import java.util.List;
import java.util.Map;

import kr.spring.reservation.vo.ReservationVO;

public interface ReservationService {
	public void insertReservation(ReservationVO reservation);
	public void updateReservation(ReservationVO reservation);
	public void deleteReservation(Integer res_num);
	public List<ReservationVO> getReservationList(Map<String,Object> map);
	public int getReservationCount(Map<String,Object> map);
	public ReservationVO getReservation(Integer res_num);
	public ReservationVO getRecentReservation(Integer mem_num);
	public void changeState(Integer res_num);
}
