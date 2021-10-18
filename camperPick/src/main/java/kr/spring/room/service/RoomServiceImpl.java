package kr.spring.room.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.reservation.dao.ReservationMapper;
import kr.spring.room.dao.RoomMapper;
import kr.spring.room.vo.RoomVO;

@Service
@Transactional
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomMapper roomMapper;
	@Autowired
	private ReservationMapper reservationMapper;

	@Override
	public void insertRoom(RoomVO roomVO) {
		roomMapper.insertRoom(roomVO);
		
	}

	@Override
	public void updateRoom(RoomVO roomVO) {
		roomMapper.updateRoom(roomVO);
		
	}

	@Override
	public void deleteRoom(Integer room_num) {
		reservationMapper.deleteReservationToo(room_num);
		roomMapper.deleteRoom(room_num);
		
		
	}

	@Override
	public int getRoomCount(Integer camping_num) {
		
		return roomMapper.getRoomCount(camping_num);
	}

	@Override
	public List<RoomVO> getRoomList(Map<String, Object> map) {
		
		return roomMapper.getRoomList(map);
	}

	@Override
	public RoomVO getRoom(Integer room_num) {
		return roomMapper.getRoom(room_num);
	}


}
