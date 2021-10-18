package kr.spring.room.service;

import java.util.List;
import java.util.Map;


import kr.spring.room.vo.RoomVO;


public interface RoomService {
	public void insertRoom(RoomVO roomVO);
	public void updateRoom(RoomVO roomVO);
	public void deleteRoom(Integer room_num);
	public int getRoomCount(Integer camping_num);
	public List<RoomVO> getRoomList(Map<String,Object> map);
	public RoomVO getRoom(Integer room_num);
}
