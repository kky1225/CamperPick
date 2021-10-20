package kr.spring.reservation.service;

import java.util.List;
import java.util.Map;

import kr.spring.reservation.vo.ReservationVO;
import kr.spring.reservation.vo.ReserveNotificationVO;

public interface ReservationService {
	public int insertReservation(ReservationVO reservation);
	public void updateReservation(ReservationVO reservation);
	public void deleteReservation(Integer res_num);
	public List<ReservationVO> getReservationList(Map<String,Object> map);
	public int getReservationCount(String email);
	public ReservationVO getReservation(Integer res_num);
	public ReservationVO getRecentReservation(Integer mem_num);
	public void changeState(Integer res_num);
	public void changeState2(Integer res_num);
	public List<ReservationVO> getReservationByRoom(Integer room_num);
	
	public int insertReserveNotification(ReserveNotificationVO reserveNotificationVO);
	public void updateReserveNotfication(Integer res_num);
	public void deleteReserveNotfication(Integer res_num);
	public List<ReserveNotificationVO> getReserveNotificationList(Integer mem_num);
}
