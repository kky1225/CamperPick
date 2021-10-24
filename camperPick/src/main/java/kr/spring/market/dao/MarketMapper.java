package kr.spring.market.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.market.vo.MarketReReplyVO;
import kr.spring.market.vo.MarketReplyVO;
import kr.spring.market.vo.MarketVO;

public interface MarketMapper {
	// 부모글
	@Insert("insert into cmarket(market_num,title,content,ip,mem_num,choice) values (cmarket_seq.nextval,#{title},#{content},#{ip},#{mem_num},#{choice})")
	public void insertMarket(MarketVO market);
	
	public int getMarketCount(Map<String, Object> map);
	
	public List<MarketVO> getMarketList(Map<String,Object> map);
	
	@Select("select * from cmarket n join cmember_detail d on n.mem_num=d.mem_num where n.market_num=#{market_num}")
	public MarketVO getMarket(int market_num);
	
	@Update("update cmarket set hit=hit+1 where market_num=#{market_num}")
	public void updateHit(int market_num);
	
	public void updateMarket(MarketVO marketVO);
	
	@Delete("delete from cmarket where market_num=#{market_num}")
	public void deleteMarket(int market_num);
	
	@Update("update cmarket set uploadfile='',filename='' where market_num=#{market_num}")
	public void deleteFile(Integer market_num);
	
	// 댓글 부분
	
	public List<MarketReplyVO> selectListReply(Map<String, Object> map);
		
	public int selectRowCountReply(Map<String,Object> map);
		
	@Select("insert into cmarket_reply (mre_num,re_content,re_ip,market_num,mem_num) values (cmarket_reply_seq.nextval,#{re_content},#{re_ip},#{market_num},#{mem_num})")
	public void insertReply(MarketReplyVO marketReplyVO);
		
	@Update("update cmarket_reply set re_content=#{re_content},re_ip=#{re_ip},re_modifydate=sysdate where mre_num=#{mre_num}")
	public void updateReply(MarketReplyVO marketReplyVO);
		
	@Delete("delete from cmarket_reply where mre_num=#{mre_num}")
	public void deleteReply(Integer mre_num);
		
	// 부모글 삭제시 댓글이 존재하면 부모글 삭제전 댓글 삭제
	@Delete("delete from cmarket_reply where market_num=#{market_num}")
	public void deleteReplyByMarketNum(Integer market_num);
		
	// 대댓글 부분
	public int getReReplyCount(Map<String, Object> map);
		
	public List<MarketReReplyVO> getReReplyList(Map<String,Object> map);
		
	@Select("SELECT * FROM cmarket_reply2 WHERE mrre_num=#{mrre_num}")
	public MarketReReplyVO getReReply(Integer mrre_num);
		
	@Insert("INSERT INTO cmarket_reply2 (mrre_num,re_content,re_ip,mem_num,mre_num,market_num) VALUES (cmarket_reply2_seq.nextval,#{re_content},#{re_ip},#{mem_num},#{mre_num},#{market_num})")
	public void insertReReply(MarketReReplyVO marketReReplyVO);
		
	@Update("UPDATE cmarket_reply2 SET re_content=#{re_content},re_ip=#{re_ip},re_modifydate=SYSDATE WHERE mrre_num=#{mrre_num}") 
	public void updateReReply(MarketReReplyVO marketReReplyVO);
	   
	@Delete("DELETE FROM cmarket_reply2 WHERE mrre_num=#{mrre_num}")
	public void deleteReReply(Integer mrre_num); 
	    
	@Delete("DELETE FROM cmarket_reply2 WHERE mre_num=#{mre_num}")
	public void deleteReReplyByReplyNum(Integer mre_num); 
	    
	//거래게시판 상세페이지 삭제시 대댓글이 존재하면 부모글 삭제전 댓글 삭제
	@Delete("DELETE FROM cmarket_reply2 WHERE market_num=#{market_num}") 
	public void deleteReReplyByMarketNum(Integer market_num);
}
