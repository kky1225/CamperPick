package kr.spring.camping.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.camping.vo.CampingVO;

public interface CampingMapper {
	@Insert("INSERT INTO camping (camping_num,camp_name,camp_address,camp_phone,rcount,filename,uploadfile) VALUES (camping_seq.nextval,#{camp_name},#{camp_address},#{camp_phone},#{rcount},#{filename},#{uploadfile})")
	public void insertCamping(CampingVO camping);
	public List<CampingVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	@Select("SELECT * FROM camping WHERE camping_num=#{camping_num}")
	public CampingVO selectCamping(Integer camping_num);
	public void updateCamping(CampingVO camping);
	@Delete("DELETE FROM camping WHERE camping_num=#{camping_num}")
	public void deleteCamping(Integer camping_num);
	//파일만 지우기
	@Update("UPDATE camping SET uploadfile='', filename='' WHERE camping_num=#{camping_num}")
	public void deleteFile(Integer camping_num);
	//데이터 받아오기
	@Insert("INSERT INTO camping (camping_num,camp_name,camp_address,camp_phone,rcount) VALUES (camping_seq.nextval,#{camp_name},#{camp_address},#{camp_phone},#{rcount})")
	public void insertCampingData(CampingVO camping);

}
