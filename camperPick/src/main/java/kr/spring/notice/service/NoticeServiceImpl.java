package kr.spring.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.notice.dao.NoticeMapper;
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


}
