package kr.spring.camping.service;

import java.util.List;
import java.util.Map;

import kr.spring.camping.vo.CampingVO;

public interface CampingService {
	public void insertCamping(CampingVO camping);
	public List<CampingVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public CampingVO selectCamping(Integer camping_num);
	public void updateCamping(CampingVO camping);
	public void deleteCamping(Integer camping_num);
	public void insertCampingData(CampingVO camping);
}
