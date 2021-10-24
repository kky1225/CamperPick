package kr.spring.market.service;

import java.util.List;
import java.util.Map;

import kr.spring.market.vo.MarketReReplyVO;
import kr.spring.market.vo.MarketReplyVO;
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
	
	// 댓글 부분
		public List<MarketReplyVO> selectListReply(Map<String, Object> map);
		public int selectRowCountReply(Map<String,Object> map);
		public void insertReply(MarketReplyVO marketReplyVO);
		public void updateReply(MarketReplyVO marketReplyVO);
		public void deleteReply(Integer mre_num);
		
		// 대댓글 부분
		public int getReReplyCount(Map<String, Object> map);
		public List<MarketReReplyVO> getReReplyList(Map<String,Object> map);
		public MarketReReplyVO getReReply(Integer mrre_num);
		public void insertReReply(MarketReReplyVO marketReReplyVO);
	    public void updateReReply(MarketReReplyVO marketReReplyVO);
	    public void deleteReReply(Integer mrre_num); 
	    public void deleteReReplyByMarketNum(Integer market_num);
}
