package kr.spring.market.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.market.dao.MarketMapper;
import kr.spring.market.vo.MarketReReplyVO;
import kr.spring.market.vo.MarketReplyVO;
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
	
	// 댓글
		@Override
		public List<MarketReplyVO> selectListReply(Map<String, Object> map) {
			return marketMapper.selectListReply(map);
		}

		@Override
		public int selectRowCountReply(Map<String, Object> map) {
			return marketMapper.selectRowCountReply(map);
		}

		@Override
		public void insertReply(MarketReplyVO marketReplyVO) {
			marketMapper.insertReply(marketReplyVO);
		}

		@Override
		public void updateReply(MarketReplyVO marketReplyVO) {
			marketMapper.updateReply(marketReplyVO);
		}

		@Override
		public void deleteReply(Integer mre_num) {
			// 댓글을 삭제하면 대댓글도 삭제
			marketMapper.deleteReReplyByReplyNum(mre_num);
			marketMapper.deleteReply(mre_num);
		}
		
		// 대댓글
		@Override
		public int getReReplyCount(Map<String, Object> map) {
			return marketMapper.getReReplyCount(map);
		}

		@Override
		public List<MarketReReplyVO> getReReplyList(Map<String, Object> map) {
			return marketMapper.getReReplyList(map);
		}

		@Override
		public MarketReReplyVO getReReply(Integer mrre_num) {
			// TODO Auto-generated method stub
			return marketMapper.getReReply(mrre_num);
		}

		@Override
		public void insertReReply(MarketReReplyVO marketReReplyVO) {
			// TODO Auto-generated method stub
			marketMapper.insertReReply(marketReReplyVO);
		}

		@Override
		public void updateReReply(MarketReReplyVO marketReReplyVO) {
			// TODO Auto-generated method stub
			marketMapper.updateReReply(marketReReplyVO);
		}

		@Override
		public void deleteReReply(Integer mrre_num) {
			// TODO Auto-generated method stub
			marketMapper.deleteReReply(mrre_num);
		}

		@Override
		public void deleteReReplyByMarketNum(Integer market_num) {
			// TODO Auto-generated method stub
			marketMapper.deleteReReplyByMarketNum(market_num);
		}

}
