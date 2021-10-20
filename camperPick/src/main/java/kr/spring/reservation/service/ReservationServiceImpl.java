package kr.spring.reservation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.reservation.dao.ReservationMapper;
import kr.spring.reservation.vo.ReservationVO;
import kr.spring.reservation.vo.ReserveNotificationVO;
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationMapper reservationMapper; 

	@Override
	public int insertReservation(ReservationVO reservation) {
		
		reservation.setRes_num(reservationMapper.selectMem_num());
		
		reservationMapper.insertReservation(reservation);
	
		return reservation.getRes_num();
		
	}

	@Override
	public void updateReservation(ReservationVO reservation) {
		reservationMapper.updateReservation(reservation);
		
	}

	@Override
	public void deleteReservation(Integer res_num) {
		reservationMapper.deleteReservation(res_num);
	}

	@Override
	public List<ReservationVO> getReservationList(Map<String, Object> map) {
		
		return reservationMapper.getReservationList(map);
	}
	

	@Override
	public ReservationVO getReservation(Integer res_num) {
		
		return reservationMapper.getReservation(res_num);
	}

	@Override
	public int getReservationCount(Map<String, Object> map) {
		
		return reservationMapper.getReservationCount(map);
	}

	@Override
	public ReservationVO getRecentReservation(Integer mem_num) {
		
		return reservationMapper.getRecentReservation(mem_num);
	}

	@Override
	public void changeState(Integer res_num) {
		reservationMapper.changeState(res_num);
		
	}
	
	@Override
	public int insertReserveNotification(ReserveNotificationVO reserveNotificationVO) {
		reservationMapper.insertReserveNotfication(reserveNotificationVO);
		return reserveNotificationVO.getNot_num();
	}

	@Override
	public void deleteReserveNotfication(Integer res_num) {
		reservationMapper.deleteReserveNotfication(res_num);
	}

	@Override
	public List<ReserveNotificationVO> getReserveNotificationList(Integer mem_num) {
		return reservationMapper.getReserveNotificationList(mem_num);
	}

	@Override
	public void updateReserveNotfication(Integer res_num) {
		reservationMapper.updateReserveNotfication(res_num);
		
	}

	@Override
	public List<ReservationVO> getReservationByRoom(Integer room_num) {
		return reservationMapper.getReservationByRoom(room_num);
		
	}

}
