package kr.spring.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.notice.vo.NoticeVO;

public interface NoticeMapper {
	@Insert("insert into cnotice(notice_num,title,content,ip,mem_num) values (cnotice_seq.nextval,#{title},#{content},#{ip},#{mem_num})")
	public void insertNotice(NoticeVO notice);
	
	public int getNoticeCount(Map<String,Object> map);
	
	public List<NoticeVO> getNoticeList(Map<String,Object> map);
	
	@Select("select * from cnotice n join cmember_detail d on n.mem_num = d.mem_num where n.notice_num = #{notice_num}")
	public NoticeVO getNotice(int notice_num);
	
	@Update("update cnotice set hit=hit+1 where notice_num=#{notice_num}")
	public void updateHit(int notice_num);
	
	public void updateNotice(NoticeVO noticeVO);
	
	@Delete("delete from cnotice where notice_num=#{notice_num}")
	public void deleteNotice(int notice_num);
	
	@Update("update cnotice set uploadfile='',filename='' where notice_num=#{notice_num}")
	public void deleteFile(Integer notice_num);
}
