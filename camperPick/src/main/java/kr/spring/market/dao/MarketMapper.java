package kr.spring.market.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.market.vo.MarketVO;

public interface MarketMapper {
	@Insert("insert into cmarket(market_num,title,content,uploadfile,filename,ip,mem_num,choice) values (cmarket_seq.nextval,#{title},#{content},#{uploadfile},#{filename},#{ip},#{mem_num},#{choice})")
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
}
