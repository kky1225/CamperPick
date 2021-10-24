package kr.spring.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.notice.dao.NoticeMapper;
import kr.spring.notice.vo.NoticeReReplyVO;
import kr.spring.notice.vo.NoticeReplyVO;
import kr.spring.notice.vo.NoticeVO;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public void insertNotice(NoticeVO notice) {
		noticeMapper.insertNotice(notice);
		
	}

	@Override
	public int getNoticeCount(Map<String, Object> map) {
		return noticeMapper.getNoticeCount(map);
	}

	@Override
	public List<NoticeVO> getNoticeList(Map<String, Object> map) {
		return noticeMapper.getNoticeList(map);
	}

	@Override
	public NoticeVO getNotice(int notice_num) {
		return noticeMapper.getNotice(notice_num);
	}

	@Override
	public void updateNotice(NoticeVO noticeVO) {
		noticeMapper.updateNotice(noticeVO);
		
	}

	@Override
	public void deleteNotice(int notice_num) {
		noticeMapper.deleteNotice(notice_num);
		
	}

	@Override
	public void updateHit(int notice_num) {
		noticeMapper.updateHit(notice_num);
		
	}

	@Override
	public void deleteFile(Integer notice_num) {
		noticeMapper.deleteFile(notice_num);
		
	}
	
	// 댓글
		@Override
		public List<NoticeReplyVO> selectListReply(Map<String, Object> map) {
			return noticeMapper.selectListReply(map);
		}

		@Override
		public int selectRowCountReply(Map<String, Object> map) {
			return noticeMapper.selectRowCountReply(map);
		}

		@Override
		public void insertReply(NoticeReplyVO NoticeReplyVO) {
			noticeMapper.insertReply(NoticeReplyVO);
		}

		@Override
		public void updateReply(NoticeReplyVO NoticeReplyVO) {
			noticeMapper.updateReply(NoticeReplyVO);
		}

		@Override
		public void deleteReply(Integer nre_num) {
			// 댓글을 삭제하면 대댓글도 삭제
			noticeMapper.deleteReReplyByReplyNum(nre_num);
			noticeMapper.deleteReply(nre_num);
		}
		
		// 대댓글
		@Override
		public int getReReplyCount(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return noticeMapper.getReReplyCount(map);
		}

		@Override
		public List<NoticeReReplyVO> getReReplyList(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return noticeMapper.getReReplyList(map);
		}

		@Override
		public NoticeReReplyVO getReReply(Integer nrre_num) {
			// TODO Auto-generated method stub
			return noticeMapper.getReReply(nrre_num);
		}

		@Override
		public void insertReReply(NoticeReReplyVO noticeReReplyVO) {
			// TODO Auto-generated method stub
			noticeMapper.insertReReply(noticeReReplyVO);
		}

		@Override
		public void updateReReply(NoticeReReplyVO noticeReReplyVO) {
			// TODO Auto-generated method stub
			noticeMapper.updateReReply(noticeReReplyVO);
		}

		@Override
		public void deleteReReply(Integer nrre_num) {
			// TODO Auto-generated method stub
			noticeMapper.deleteReReply(nrre_num);
		}

		@Override
		public void deleteReReplyByNoticeNum(Integer notice_num) {
			// TODO Auto-generated method stub
			noticeMapper.deleteReReplyByNoticeNum(notice_num);
		}


}
