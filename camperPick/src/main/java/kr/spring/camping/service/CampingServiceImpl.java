package kr.spring.camping.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.camping.dao.CampingMapper;
import kr.spring.camping.vo.CampingVO;
import kr.spring.payment.dao.PaymentMapper;
import kr.spring.reservation.dao.ReservationMapper;
import kr.spring.room.dao.RoomMapper;

@Service
@Transactional
public class CampingServiceImpl implements CampingService{
	
	@Autowired
	private CampingMapper campingMapper;
	@Autowired
	private RoomMapper roomMapper;
	@Autowired
	private ReservationMapper reservationMapper;
	@Autowired
	private PaymentMapper paymemtMapper;
	
	@Override
	public List<CampingVO> selectList(Map<String, Object> map) {
		
		return campingMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		
		return campingMapper.selectRowCount(map);
	}

	@Override
	public CampingVO selectCamping(Integer camping_num) {
		
		return campingMapper.selectCamping(camping_num);
	}

	@Override
	public void insertCamping(CampingVO camping) {
		campingMapper.insertCamping(camping);
		
	}

	@Override
	public void updateCamping(CampingVO camping) {
		campingMapper.updateCamping(camping);
		
	}

	@Override
	public void deleteCamping(Integer camping_num) {
		reservationMapper.deleteReserveNotficationByCamping(camping_num);
		paymemtMapper.deletePaymentByCamping(camping_num);
		reservationMapper.deleteReservationFirst(camping_num);
		roomMapper.deleteRoomToo(camping_num);
		campingMapper.deleteCamping(camping_num);
	}


	@Override
	public void insertCampingData(CampingVO camping) {
		campingMapper.insertCampingData(camping);
		
	}

	@Override
	public void deleteFile(Integer camping_num) {
		campingMapper.deleteFile(camping_num);
		
	}
	

}
