package kr.spring.notice.controller;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.notice.service.NoticeService;
import kr.spring.notice.vo.NoticeReReplyVO;
import kr.spring.notice.vo.NoticeReplyVO;
import kr.spring.notice.vo.NoticeVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

// 공지사항 관련 Controller
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	// 로그 처리(로그 대상 지정)
	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	private int rowCount = 20;
	private int pageCount = 10;
	
	// 자바빈 초기화
	@ModelAttribute
	public NoticeVO initCommand() {
		return new NoticeVO();
	}
	
	// 공지사항 호출
	@RequestMapping("/notice/noticeList.do")
	public ModelAndView notice(@RequestParam(value="pageNum",defaultValue = "1") int currentPage,
								@RequestParam(value="keyfield",defaultValue = "") String keyfield,
								@RequestParam(value="keyword",defaultValue = "1") String keyword) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		// 글의 총 개수 또는 검색된 글의 개수
		int count = noticeService.getNoticeCount(map);
		
		log.debug("<<count>> : " + count);
				
		// 페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage, count, rowCount, pageCount, "noticeList.do");
		
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		// 목록 호출
		List<NoticeVO> list = null;
		if(count > 0) {
			list = noticeService.getNoticeList(map);
		}
			
		ModelAndView mav = new ModelAndView();
		mav.setViewName("notice");	// 타일스 식별자
		mav.addObject("count",count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		return mav;	
	}
	
	// 공지사항 - 글쓰기 폼 호출
	@GetMapping("/notice/noticeWrite.do")
	public String writeNoticeForm() {
		return "noticeWrite";
	}
	
	// 공지사항 - 글쓰기 처리
	@PostMapping("/notice/noticeWrite.do")
	public String submitWrite(@Valid NoticeVO noticeVO, BindingResult result, HttpServletRequest request) {
		
		log.debug("<<공지사항 작성>> : " + noticeVO);
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return writeNoticeForm();
		}
		
		// ip 세팅
		noticeVO.setIp(request.getRemoteAddr());
		
		// 글쓰기
		noticeService.insertNotice(noticeVO);
		
		return "redirect:/notice/noticeList.do";
	}
	
	// 공지사항 글 상세
	@RequestMapping("/notice/noticeDetail.do")
	public String detail(@RequestParam int notice_num, Model model) {
		
		// 해당 공지사항 조회수 증가
		noticeService.updateHit(notice_num);
		
		NoticeVO notice = noticeService.getNotice(notice_num);
		
		log.debug("<<공지사항 글 상세>> : " + notice);
		
		// HTML 태그 불허
		notice.setTitle(StringUtil.useNoHtml(notice.getTitle()));
		// HTML 태그 불허, 줄바꿈 허용
		//notice.setContent(StringUtil.useBrNoHtml(notice.getContent()));
		
		model.addAttribute("notice", notice);
		
		return "noticeDetail";
	}
	
	// 이미지 출력
	@RequestMapping("/notice/imageView.do")
	public ModelAndView viewImage(@RequestParam int notice_num) {
		
		NoticeVO notice = noticeService.getNotice(notice_num);
			
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		//				속성명			속성값(byte[]의 데이터)
		mav.addObject("imageFile", notice.getUploadfile());
		mav.addObject("filename", notice.getFilename());
			
		return mav;
		}
	
	// 공지사항 수정 폼 호출
	@GetMapping("/notice/noticeUpdate.do")
	public String updateNoticeForm(@RequestParam int notice_num,Model model) {
		NoticeVO noticeVO = noticeService.getNotice(notice_num);
		
		// 데이터 저장
		model.addAttribute("noticeVO", noticeVO);
		
		return "noticeUpdate";
	}
	
	// 공지사항 수정
	@PostMapping("/notice/noticeUpdate.do")
	public String submitUpdate(@Valid NoticeVO noticeVO, BindingResult result, HttpServletRequest request) {
		
		// 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "noticeUpdate";
		}
		
		// 회원번호,비밀번호 일치 여부 체크
		// DB에 저장된 회원번호 구하기
		NoticeVO dbNotice = noticeService.getNotice(noticeVO.getNotice_num());
		
		log.debug("<<DB 저장된 회원>> : " + dbNotice);
		
		if(dbNotice.getMem_num() != noticeVO.getMem_num()) {
			result.reject("invalidMem_num");
			return "noticeUpdate";
		}
		
		if(!dbNotice.getPasswd().equals(noticeVO.getPasswd())) {
			result.reject("invalidPassword");
			return "noticeUpdate";
		}
		
		// ip 세팅
		noticeVO.setIp(request.getRemoteAddr());
		
		noticeService.updateNotice(noticeVO);
		
		return "redirect:/notice/noticeList.do";
	}
	
	// 글 수정 - 파일 삭제
	@RequestMapping("/notice/deleteFile.do")
	@ResponseBody
	public Map<String, String> processFile(int notice_num,HttpSession session){
			
		Map<String, String> map = new HashMap<String, String>();
			
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			map.put("result", "logout");
		}else {
			noticeService.deleteFile(notice_num);
			map.put("result", "success");
		}
		return map;
	}
		
	// ckeditor를 이용한 이미지 업로드
	@RequestMapping("/notice/imageUploader.do")
	@ResponseBody
	public Map<String, Object> uploadImage(MultipartFile upload, HttpSession session, HttpServletRequest request) throws Exception{
			
		// 업로드 할 절대 경로 구하기
		String realFolder = session.getServletContext().getRealPath("/resources/image_upload");
			
		// 업로드 한 파일 이름
		String org_filename = upload.getOriginalFilename();
		String str_filename = System.currentTimeMillis() + org_filename;
			
		log.debug("<<원본 파일명>> : " + org_filename);
		log.debug("<<저장할 파일명>> : " + str_filename);
			
		Integer user_num = (Integer)session.getAttribute("user_num");
			
		String filepath = realFolder + "\\" + user_num + "\\" + str_filename;
		log.debug("<<파일 경로>> : " + filepath);
			
		File f = new File(filepath);
		if(!f.exists()) {
			f.mkdirs();
		}
			
		upload.transferTo(f);
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploaded", true);
		map.put("url", request.getContextPath()+"/resources/image_upload/"+user_num+"/"+str_filename);
			
		return map;
	}
	
	// 공지사항 삭제 폼 호출
	@GetMapping("/notice/noticeDelete.do")
	public String deleteNoticeForm(@RequestParam int notice_num,Model model) {
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setNotice_num(notice_num);
			
		// 데이터 저장
		model.addAttribute("noticeVO", noticeVO);
			
		return "noticeDelete";
	}
	
	@PostMapping("/notice/noticeDelete.do")
	public String submitDelete(@Valid NoticeVO noticeVO, BindingResult result) {
		
		// 회원번호,비밀번호 일치 여부
		// DB에 저장된 회원번호 구하기
		NoticeVO dbNotice = noticeService.getNotice(noticeVO.getNotice_num());
		
		log.debug("<<DB 저장된 회원>> : " + dbNotice);
		
		if(dbNotice.getMem_num() != noticeVO.getMem_num()) {
			result.reject("invalidMem_num");
			return "noticeDelete";
		}
		
		if(!dbNotice.getPasswd().equals(noticeVO.getPasswd())) {
			result.reject("invalidPassword");
			return "noticeDelete";
		}
		
		noticeService.deleteNotice(noticeVO.getNotice_num());
		
		return "redirect:/notice/noticeList.do";
	}
	
	// 댓글 등록(ajax)
		@RequestMapping("/notice/writeReply.do")
		@ResponseBody
		public Map<String, String> writeReply(NoticeReplyVO noticeReplyVO,
											  HttpSession session,
											  HttpServletRequest request){
			log.debug("<<댓글 등록>> : " + noticeReplyVO);
					
			Map<String, String> map = new HashMap<String, String>();
					
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				// 로그인 안 됨
				map.put("result", "logout");
			}else {
				// 로그인 됨
				// ip 등록
				noticeReplyVO.setRe_ip(request.getRemoteAddr());
				// 댓글 등록
				noticeService.insertReply(noticeReplyVO);
				map.put("result", "success");
			}
					
			return map;
		}
				
		// 댓글 목록  
		@RequestMapping("/notice/listReply.do")
		@ResponseBody
		public Map<String,Object> getList(
							@RequestParam(value="pageNum",defaultValue="1") int currentPage,
							@RequestParam int notice_num){
					
			log.debug("<<currentPage>> : " + currentPage);
			log.debug("<<notice_num>> : " + notice_num);
					
			Map<String,Object> map = new HashMap<String, Object>();
					
			map.put("notice_num", notice_num);
					
			// 총 글의 개수
			int count = noticeService.selectRowCountReply(map);
					
			PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, null);
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
					
			List<NoticeReplyVO> list = null;
			if(count > 0) {
				list = noticeService.selectListReply(map);
			}else {
				list = Collections.emptyList();
			}
					
			Map<String,Object> mapJson = new HashMap<String, Object>();
			mapJson.put("count", count);
			mapJson.put("rowCount",rowCount);
			mapJson.put("list", list);
					
			return mapJson;
		}
				
		// 댓글 삭제
		@RequestMapping("/notice/deleteReply.do")
		@ResponseBody
		public Map<String, String> deleteReply(@RequestParam int nre_num,@RequestParam int mem_num,HttpSession session){
					
			log.debug("<<nre_num>> : " + nre_num);
			log.debug("<<mem_num>> : " + mem_num);
					
			Map<String,String> map = new HashMap<String, String>();
					
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				// 로그인이 되어있지 않음
				map.put("result", "logout");
			}else if(user_num != null && user_num == mem_num){
				// 로그인이 되어있고 로그인 한 아이디와 작성자 아이디가 일치
				noticeService.deleteReply(nre_num);
				map.put("result", "success");
			}else {
				// 로그인 아이디와 작성자 아이디 불일치
				map.put("result", "wrongAccess");
			}
					
			return map;
		}
				
		// 댓글 수정
		@RequestMapping("/notice/updateReply.do")
		@ResponseBody
		public Map<String, String> modifyReply(NoticeReplyVO noticeReplyVO, HttpSession session,HttpServletRequest request){
			log.debug("<<댓글 수정>> : " + noticeReplyVO);

			Map<String, String> map = new HashMap<String, String>();
					
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				// 로그인이 안 된 경우
				map.put("result", "logout");
			}else if(user_num != null && user_num == noticeReplyVO.getMem_num()) {
				// 로그인 회원 번호와 작성자 회원 번호 일치
				// ip 등록
				noticeReplyVO.setRe_ip(request.getRemoteAddr());
						
				// 댓글 수정
				noticeService.updateReply(noticeReplyVO);
				map.put("result", "success");
			}else {
				// 로그인 회원 번호와 작성자 회원 번호 불일치
				map.put("result", "wrongAccess");
			}
					
			return map;
		}
		
		// #############대댓글################
		
			//대댓글 등록(ajax)
			@RequestMapping("/notice/writeReReply.do")
			@ResponseBody
			public Map<String,String> writeReReply(NoticeReReplyVO noticeReReplyVO,
					                             HttpSession session,
					                             HttpServletRequest request){
				log.debug("<<대댓글 등록>> : " + noticeReReplyVO);
				
				Map<String,String> map = new HashMap<String,String>();
				
				Integer user_num = (Integer)session.getAttribute("user_num");
				if(user_num == null) {
					//로그인 안 됨
					map.put("result", "logout");
				}else {
					//로그인 됨
					//ip 등록
					noticeReReplyVO.setRe_ip(request.getRemoteAddr());
					//댓글 등록
					noticeService.insertReReply(noticeReReplyVO);
					map.put("result", "success");
				}
				
				return map;
			}



			// 대댓글 목록(ajax)
			@RequestMapping("/notice/rereplyList.do")
			@ResponseBody
			public Map<String,Object> getreList(
					      @RequestParam(value="pageNum",defaultValue="1") int currentPage,
					      @RequestParam int nre_num)	{
				
			 						  
			 				  
				log.debug("<<currentPage>> : " + currentPage);
				log.debug("<<nre_num>> : " + nre_num);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("nre_num", nre_num);
				//총 글의 갯수
				int count = noticeService.getReReplyCount(map);
				
				PagingUtil page = new PagingUtil(currentPage,count,rowCount,pageCount,
						                 null);
				map.put("start", page.getStartCount());
				map.put("end", page.getEndCount());
				
				List<NoticeReReplyVO> list = null;
				if(count > 0) {
					list = noticeService.getReReplyList(map);
					
					log.debug("<<대댓글 목록>> : " + list);
					
				}else {
					list = Collections.emptyList();
				}
				
				Map<String,Object> mapJson = new HashMap<String,Object>();
				mapJson.put("count", count);
				mapJson.put("rowCount", rowCount);
				mapJson.put("list", list);
				
				return mapJson;
			}
			

			//대댓글 삭제
			@RequestMapping("/notice/deleteReReply.do")
			@ResponseBody
			public Map<String,String> deleteReReply(@RequestParam int nrre_num,
					                              @RequestParam int mem_num,
					                              HttpSession session){
				
				log.debug("<<nrre_num>> : " + nrre_num);
				log.debug("<<mem_num>> : " + mem_num);
				
				Map<String,String> map = new HashMap<String,String>();
				
				Integer user_num = (Integer)session.getAttribute("user_num");
				if(user_num == null) {
					//로그인이 되어있지 않음
					map.put("result", "logout");
				}else if(user_num != null && user_num==mem_num) {
					//로그인이 되어 있고 로그인한 아이디와 작성자 아이디가 일치
					noticeService.deleteReReply(nrre_num);
					map.put("result", "success");
				}else {
					//로그인 아이디와 작성자 아이디 불일치
					map.put("result", "wrongAccess");
				}
				return map;
			}
			
			//대댓글 수정
			@RequestMapping("/notice/updateReReply.do")
			@ResponseBody
			public Map<String,String> modifyReReply(NoticeReReplyVO noticeReReplyVO, HttpSession session, HttpServletRequest request){
				
				log.debug("<<대댓글 수정>>:" + noticeReReplyVO);
				
				Map<String,String> map=new HashMap<String,String>();
				
				Integer user_num=(Integer)session.getAttribute("user_num");
				if(user_num==null) {
					//로그인이 안된 경우
					map.put("result", "logout");
				}else if(user_num!=null && user_num == noticeReReplyVO.getMem_num()) {
					//로그인 회원번호와 작성자 회원번호 일치
					//ip 등록
					noticeReReplyVO.setRe_ip(request.getRemoteAddr());
					
					//대댓글 수정
					noticeService.updateReReply(noticeReReplyVO);
					map.put("result", "success");
				}else {
					//로그인회원번호와 작성자 회원번호 불일치
					map.put("result", "wrongAccess");
					
				}
				
				return map;
			}
	
}








































