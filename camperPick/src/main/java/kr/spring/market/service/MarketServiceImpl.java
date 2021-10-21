package kr.spring.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.market.dao.MarketMapper;
import kr.spring.market.vo.MarketVO;

@Service
@Transactional
public class MarketServiceImpl implements MarketService{

	@Autowired
	private MarketMapper marketMapper;

	@Override
	public void insertMarket(MarketVO market) {
		marketMapper.insertMarket(market);
		
	}

	@Override
	public int getMarketCount(Map<String, Object> map) {
		return marketMapper.getMarketCount(map);
	}

	@Override
	public List<MarketVO> getMarketList(Map<String, Object> map) {
		return marketMapper.getMarketList(map);
	}

	@Override
	public MarketVO getMarket(int market_num) {
		return marketMapper.getMarket(market_num);
	}

	@Override
	public void updateMarket(MarketVO marketVO) {
		marketMapper.updateMarket(marketVO);
		
	}

	@Override
	public void deleteMarket(int market_num) {
		marketMapper.deleteMarket(market_num);
		
	}

	@Override
	public void updateHit(int market_num) {
		marketMapper.updateHit(market_num);
		
	}

	@Override
	public void deleteFile(Integer market_num) {
		marketMapper.deleteFile(market_num);
		
	}

}
