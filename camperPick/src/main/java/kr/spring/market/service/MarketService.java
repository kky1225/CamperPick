package kr.spring.market.service;

import java.util.List;
import java.util.Map;

import kr.spring.market.vo.MarketVO;

public interface MarketService {
	public void insertMarket(MarketVO market);
	public int getMarketCount(Map<String, Object> map);
	public List<MarketVO> getMarketList(Map<String,Object> map);
	public MarketVO getMarket(int market_num);
	public void updateHit(int market_num);
	public void updateMarket(MarketVO marketVO);
	public void deleteMarket(int market_num);
	public void deleteFile(Integer market_num);
}
