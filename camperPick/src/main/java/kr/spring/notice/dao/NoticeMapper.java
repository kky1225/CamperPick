package kr.spring.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.notice.vo.NoticeReReplyVO;
import kr.spring.notice.vo.NoticeReplyVO;
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
	
	// 댓글 부분
	
		public List<NoticeReplyVO> selectListReply(Map<String, Object> map);
			
		public int selectRowCountReply(Map<String,Object> map);
			
		@Select("insert into cnotice_reply (nre_num,re_content,re_ip,notice_num,mem_num) values (cnotice_reply_seq.nextval,#{re_content},#{re_ip},#{notice_num},#{mem_num})")
		public void insertReply(NoticeReplyVO NoticeReplyVO);
			
		@Update("update cnotice_reply set re_content=#{re_content},re_ip=#{re_ip},re_modifydate=sysdate where nre_num=#{nre_num}")
		public void updateReply(NoticeReplyVO NoticeReplyVO);
			
		@Delete("delete from cnotice_reply where nre_num=#{nre_num}")
		public void deleteReply(Integer nre_num);
			
		// 부모글 삭제시 댓글이 존재하면 부모글 삭제전 댓글 삭제
		@Delete("delete from cnotice_reply where notice_num=#{notice_num}")
		public void deleteReplyByNoticeNum(Integer notice_num);
		
		// 대댓글 부분
		public int getReReplyCount(Map<String, Object> map);
		
		public List<NoticeReReplyVO> getReReplyList(Map<String,Object> map);
		
		@Select("SELECT * FROM cnotice_reply2 WHERE nrre_num=#{nrre_num}")
		public NoticeReReplyVO getReReply(Integer nrre_num);
		
		@Insert("INSERT INTO cnotice_reply2 (nrre_num,re_content,re_ip,mem_num,nre_num,notice_num) VALUES (cnotice_reply2_seq.nextval,#{re_content},#{re_ip},#{mem_num},#{nre_num},#{notice_num})")
		public void insertReReply(NoticeReReplyVO noticeReReplyVO);
		
		@Update("UPDATE cnotice_reply2 SET re_content=#{re_content},re_ip=#{re_ip},re_modifydate=SYSDATE WHERE nrre_num=#{nrre_num}") 
	    public void updateReReply(NoticeReReplyVO noticeReReplyVO);
	   
		@Delete("DELETE FROM cnotice_reply2 WHERE nrre_num=#{nrre_num}")
	    public void deleteReReply(Integer nrre_num); 
	    
		@Delete("DELETE FROM cnotice_reply2 WHERE nre_num=#{nre_num}")
	    public void deleteReReplyByReplyNum(Integer nre_num); 
	    
		//공지사항 상세페이지 삭제시 대댓글이 존재하면 부모글 삭제전 댓글 삭제
	    @Delete("DELETE FROM cnotice_reply2 WHERE notice_num=#{notice_num}") 
	    public void deleteReReplyByNoticeNum(Integer notice_num);
}
