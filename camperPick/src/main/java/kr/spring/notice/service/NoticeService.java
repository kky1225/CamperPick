package kr.spring.notice.service;

import java.util.List;
import java.util.Map;

import kr.spring.notice.vo.NoticeReReplyVO;
import kr.spring.notice.vo.NoticeReplyVO;
import kr.spring.notice.vo.NoticeVO;

public interface NoticeService {
	public void insertNotice(NoticeVO notice);
	public int getNoticeCount(Map<String,Object> map);
	public List<NoticeVO> getNoticeList(Map<String,Object> map);
	public NoticeVO getNotice(int notice_num);
	public void updateHit(int notice_num);
	public void updateNotice(NoticeVO noticeVO);
	public void deleteNotice(int notice_num);
	public void deleteFile(Integer notice_num);
	
	// 댓글
		public List<NoticeReplyVO> selectListReply(Map<String, Object> map);
		public int selectRowCountReply(Map<String,Object> map);
		public void insertReply(NoticeReplyVO NoticeReplyVO);
		public void updateReply(NoticeReplyVO NoticeReplyVO);
		public void deleteReply(Integer nre_num);
		
		// 대댓글
		public int getReReplyCount(Map<String, Object> map);
		public List<NoticeReReplyVO> getReReplyList(Map<String,Object> map);
		public NoticeReReplyVO getReReply(Integer nrre_num);
		public void insertReReply(NoticeReReplyVO noticeReReplyVO);
	    public void updateReReply(NoticeReReplyVO noticeReReplyVO);
	    public void deleteReReply(Integer nrre_num); 
	    public void deleteReReplyByNoticeNum(Integer notice_num); 
}
