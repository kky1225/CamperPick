package kr.spring.room.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


import kr.spring.room.vo.RoomVO;

public interface RoomMapper {
	@Insert("INSERT INTO croom (room_num, room_name, people,area,price,checkIn, checkOut, info,filename,camping_num,uploadfile) VALUES (croom_seq.nextval,#{room_name}, #{people},#{area},#{price},#{checkIn},#{checkOut},#{info},#{filename},#{camping_num},#{uploadfile})")
	public void insertRoom(RoomVO roomVO);
	public void updateRoom(RoomVO roomVO);
	public void deleteRoom(Integer room_num);
	@Select("SELECT count(*) FROM croom WHERE camping_num=#{camping_num}")
	public int getRoomCount(Integer camping_num);
	@Select("SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT * FROM croom ORDER BY room_num DESC)a) WHERE rnum >= #{start} AND rnum <= #{end} AND camping_num=#{camping_num}")
	public List<RoomVO> getRoomList(Map<String,Object> map);
	@Select("SELECT * FROM croom WHERE room_num=#{room_num}")
	public RoomVO getRoom(Integer room_num);
	@Delete("DELETE FROM croom WHERE camping_num=#{camping_num}")
	public void deleteRoomToo(Integer camping_num);
	
}
