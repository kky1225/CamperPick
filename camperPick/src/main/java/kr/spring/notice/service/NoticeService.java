package kr.spring.notice.service;

import java.util.List;
import java.util.Map;

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
}
