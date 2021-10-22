package kr.spring.review.controller;

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

import kr.spring.member.vo.MemberVO;
import kr.spring.review.service.ReviewService;
import kr.spring.review.vo.ReviewReplyVO;
import kr.spring.review.vo.ReviewVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	// 로그 처리(로그 대상 지정)
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	private int rowCount = 20;
	private int pageCount = 10;
	
	// 자바빈 초기화
	@ModelAttribute
	public ReviewVO initCommand() {
		return new ReviewVO();
	}
	
	//리뷰 등록(ajax)
		@RequestMapping("/review/writeReview.do")
		@ResponseBody
		public Map<String,String> writeReview(ReviewVO reviewVO,
				                             HttpSession session,
				                             HttpServletRequest request){
			logger.debug("<<댓글 등록>> : " + reviewVO);
			
			Map<String,String> map = new HashMap<String,String>();
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				//로그인 안 됨
				map.put("result", "logout");
			}else {
				//로그인 됨
				//ip 등록
				reviewVO.setIp(request.getRemoteAddr());
				//댓글 등록
				reviewService.insertReview(reviewVO);
				map.put("result", "success");
			}
			
			return map;
		}
	
	
	// 리뷰 목록(ajax)
	@RequestMapping("/review/reviewList.do")
	@ResponseBody
	public Map<String,Object> getList(
			      @RequestParam(value="pageNum",defaultValue="1") int currentPage,
			      @RequestParam  int camping_num)	{
		
	 						  
	 				  
		logger.debug("<<currentPage>> : " + currentPage);
		logger.debug("<<camping_num>> : " + camping_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("camping_num", camping_num);
		//총 글의 갯수
		int count = reviewService.getReviewCount(map);
		
		PagingUtil page = new PagingUtil(currentPage,count,rowCount,pageCount,
				                 null);
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<ReviewVO> list = null;
		if(count > 0) {
			list = reviewService.getReviewList(map);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = new HashMap<String,Object>();
		mapJson.put("count", count);
		mapJson.put("rowCount", rowCount);
		mapJson.put("list", list);
		
		return mapJson;
	}
	
	//리뷰사진 업데이트(ajax)
		@RequestMapping("/review/updatePhoto.do")
		@ResponseBody
		public Map<String,String> processProfile(ReviewVO reviewVO, HttpSession session){
			Map<String,String> map = new HashMap<String,String>();
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num==null) {//로그인이 되지 않은 상태
				map.put("result", "logout");
			}else {//로그인 된 상태
				reviewVO.setMem_num(user_num);
				reviewService.updateFile(reviewVO);
				
				//이미지를 업로드한 후 세션에 저장된 프로필 사진 정보 갱신
				session.setAttribute("user_photo", reviewVO.getUploadfile());
				
				map.put("result", "success");
			}
			
			return map;
		}
		
		//리뷰 사진 출력
		@RequestMapping("/review/photoView.do")
		public ModelAndView viewImage(int review_num, HttpSession session) {
			
			ReviewVO reviewVO = reviewService.getReview(review_num);
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("imageView");
			mav.addObject("imageFile", reviewVO.getUploadfile());
			mav.addObject("filename", reviewVO.getFilename());
			
			return mav;
		}
		
		//리뷰 삭제
		@RequestMapping("/review/deleteReview.do")
		@ResponseBody
		public Map<String,String> deleteReview(@RequestParam int review_num,
				                              @RequestParam int mem_num,
				                              HttpSession session){
			
			logger.debug("<<review_num>> : " + review_num);
			logger.debug("<<mem_num>> : " + mem_num);
			
			Map<String,String> map = new HashMap<String,String>();
			
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				//로그인이 되어있지 않음
				map.put("result", "logout");
			}else if(user_num != null && user_num==mem_num) {
				//로그인이 되어 있고 로그인한 아이디와 작성자 아이디가 일치
				reviewService.deleteReview(review_num);
				map.put("result", "success");
			}else {
				//로그인 아이디와 작성자 아이디 불일치
				map.put("result", "wrongAccess");
			}
			return map;
		}
		
		//리뷰 수정
		@RequestMapping("/review/updateReview.do")
		@ResponseBody
		public Map<String,String> modifyReview(ReviewVO reviewVO, HttpSession session, HttpServletRequest request){
			
			logger.debug("<<리뷰 수정>>:" + reviewVO);
			
			Map<String,String> map=new HashMap<String,String>();
			
			Integer user_num=(Integer)session.getAttribute("user_num");
			if(user_num==null) {
				//로그인이 안된 경우
				map.put("result", "logout");
			}else if(user_num!=null && user_num == reviewVO.getMem_num()) {
				//로그인 회원번호와 작성자 회원번호 일치
				//ip 등록
				reviewVO.setIp(request.getRemoteAddr());
				
				//댓글 수정
				reviewService.updateReview(reviewVO);
				map.put("result", "success");
			}else {
				//로그인회원번호와 작성자 회원번호 불일치
				map.put("result", "wrongAccess");
				
			}
			
			return map;
		}
		

		
		
		//---------------------대댓글---------------------------------
		
		
		
		
		//대댓글 등록(ajax)
				@RequestMapping("/review/writeReReview.do")
				@ResponseBody
				public Map<String,String> writeReReview(ReviewReplyVO reviewReplyVO,
						                             HttpSession session,
						                             HttpServletRequest request){
					logger.debug("<<대댓글 등록>> : " + reviewReplyVO);
					
					Map<String,String> map = new HashMap<String,String>();
					
					Integer user_num = (Integer)session.getAttribute("user_num");
					if(user_num == null) {
						//로그인 안 됨
						map.put("result", "logout");
					}else {
						//로그인 됨
						//ip 등록
						reviewReplyVO.setRe_ip(request.getRemoteAddr());
						//댓글 등록
						reviewService.insertReReview(reviewReplyVO);
						map.put("result", "success");
					}
					
					return map;
				}
		
		
		
		// 대댓글 목록(ajax)
				@RequestMapping("/review/rereviewList.do")
				@ResponseBody
				public Map<String,Object> getreList(
						      @RequestParam(value="pageNum",defaultValue="1") int currentPage,
						     /* @RequestParam(value="camping_num", required=false) int camping_num,*/ 
						      @RequestParam int review_num)	{
					
				 						  
				 				  
					logger.debug("<<currentPage>> : " + currentPage);
				/*	logger.debug("<<camping_num>> : " + camping_num); */
					logger.debug("<<review_num>> : " + review_num);
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("review_num", review_num);
					//총 글의 갯수
					int count = reviewService.getReReviewCount(map);
					
					PagingUtil page = new PagingUtil(currentPage,count,rowCount,pageCount,
							                 null);
					map.put("start", page.getStartCount());
					map.put("end", page.getEndCount());
					
					List<ReviewReplyVO> list = null;
					if(count > 0) {
						list = reviewService.getReReviewList(map);
						
						logger.debug("<<대댓글 목록>> : " + list);
						
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
				@RequestMapping("/review/deleteReReview.do")
				@ResponseBody
				public Map<String,String> deleteReReview(@RequestParam int rre_num,
						                              @RequestParam int mem_num,
						                              HttpSession session){
					
					logger.debug("<<rre_num>> : " + rre_num);
					logger.debug("<<mem_num>> : " + mem_num);
					
					Map<String,String> map = new HashMap<String,String>();
					
					Integer user_num = (Integer)session.getAttribute("user_num");
					if(user_num == null) {
						//로그인이 되어있지 않음
						map.put("result", "logout");
					}else if(user_num != null && user_num==mem_num) {
						//로그인이 되어 있고 로그인한 아이디와 작성자 아이디가 일치
						reviewService.deleteReReview(rre_num);
						map.put("result", "success");
					}else {
						//로그인 아이디와 작성자 아이디 불일치
						map.put("result", "wrongAccess");
					}
					return map;
				}
				
		//대댓글 수정
				@RequestMapping("/review/updateReReview.do")
				@ResponseBody
				public Map<String,String> modifyReReview(ReviewReplyVO reviewreplyVO, HttpSession session, HttpServletRequest request){
					
					logger.debug("<<대댓글 수정>>:" + reviewreplyVO);
					
					Map<String,String> map=new HashMap<String,String>();
					
					Integer user_num=(Integer)session.getAttribute("user_num");
					if(user_num==null) {
						//로그인이 안된 경우
						map.put("result", "logout");
					}else if(user_num!=null && user_num == reviewreplyVO.getMem_num()) {
						//로그인 회원번호와 작성자 회원번호 일치
						//ip 등록
						reviewreplyVO.setRe_ip(request.getRemoteAddr());
						
						//대댓글 수정
						reviewService.updateReReview(reviewreplyVO);
						map.put("result", "success");
					}else {
						//로그인회원번호와 작성자 회원번호 불일치
						map.put("result", "wrongAccess");
						
					}
					
					return map;
				}
				

				
		
		
}



