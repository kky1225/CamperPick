package kr.spring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.spring.notice.service.NoticeService;
import kr.spring.notice.vo.NoticeVO;

public class NoticeWriterCheckInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger =
			LoggerFactory.getLogger(NoticeWriterCheckInterceptor.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		logger.debug("====로그인 회원번호와 작성자 회원번호 일치여부 체크");
		
		// 작성자의 회원번호 구하기
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));
		NoticeVO noticeVO = noticeService.getNotice(notice_num);
		
		// 로그인 회원번호 구하기
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		logger.debug("<<로그인 회원 번호>> : " + user_num);
		logger.debug("<<작성자 회원 번호>> : " + noticeVO.getMem_num());
		
		// 로그인 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != noticeVO.getMem_num()) {
			logger.debug("<<로그인 회원 번호와 작성자 회원 번호 불일치>>");
			
			request.setAttribute("accessMsg", "로그인 회원번호와 작성자 회원번호 불일치");
			request.setAttribute("accessUrl", request.getContextPath() + "/notice/noticeList.do");
			request.setAttribute("accessBtn", "공지사항 목록");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/noticeNotice.jsp");
			
			dispatcher.forward(request, response);
			
			return false;
			
		}// end of if
		
		logger.debug("<<로그인 회원번호와 작성자 회원번호 일치>>");
		
		return true;
	}
	
}


































